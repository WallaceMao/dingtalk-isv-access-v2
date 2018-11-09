package com.rishiqing.self.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.dingtalk.biz.constant.SystemConstant;
import com.rishiqing.dingtalk.isv.api.event.OrderChargeEvent;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderStatusVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.dingtalk.isv.api.service.base.order.OrderManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.self.api.model.RsqCorp;
import com.rishiqing.self.api.model.RsqDepartment;
import com.rishiqing.self.api.model.RsqUser;
import com.rishiqing.self.api.service.RsqAccountBizService;
import com.rishiqing.self.biz.http.RsqRequestHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

import java.util.*;

/**
 * 用于企业授权后批量读取企业部门和成员信息
 * Created by Wallace on 2016/11/29.
 */
public class RsqAccountBizServiceImpl implements RsqAccountBizService {
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private CorpSuiteAuthManageService corpSuiteAuthManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private RsqRequestHelper rsqRequestHelper;

    @Autowired
    private AsyncEventBus asyncOrderChargeEventBus;

    /**
     * 同步所有的企业信息到日事清，包括：
     * 1  公司信息
     * 2  部门信息
     * 3  部门成员信息
     * @return
     */
    @Override
    public void pushCreateAll(String corpId){
        //  1  创建日事清企业
        this.createRsqTeam(corpId);
        this.checkOrderCharge(corpId);
        //  2  创建企业部门。创建部门时存在三种情况：
        //  a. 包含deptId为1的部门，即全公司的部门已经获取到。这种情况
        //  b. 不包含deptId为1的部门，但是是全公司部门的一个子集
        //  c. 不包含任何部门。当用户开通时选择仅管理员可见会出现这种情况
        CorpDepartmentVO topDept = corpDepartmentManageService.getTopCorpDepartment(corpId);
        if(topDept != null){
            this.createRecursiveSubDepartment(topDept);
        }

        //  3  新建企业部门成员
        this.createAllCorpStaff(corpId);

        //  4  更新企业部门的管理员状态
        this.updateAllCorpAdmin(corpId);

        //  5  当全部都同步成功后，发到corpAuthSuiteQueue队列中，由第三方异步处理
//        jmsTemplate.send(rsqSyncCallBackQueue,new RsqSyncMessage(corpId));
    }

    /**
     * 创建公司，分为以下几步：
     * 1  根据corpId查询是否有记录，是否rsqId存在，如果rsqId存在，则直接返回rsqId
     * 2  如果记录不存在或者rsqId不存在，则发送到日事清服务器请求创建
     * 3  保存返回结果
     * @param corpId
     * @return
     */
    private CorpVO createRsqTeam(String corpId){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);

        //  如果corpVO的rsqId存在，那么直接返回
        if(null != corpVO.getRsqId()){
            return corpVO;
        }

        //  如果corpVO的rsqId不存在，那么就请求日事清服务器创建，创建成功后更新corpVO
        SuiteVO suiteVO = suiteManageService.getSuite();
        //  找到开通微应用的管理员，作为创建者传给接口
        CorpSuiteAuthVO corpSuiteAuth = corpSuiteAuthManageService.getCorpSuiteAuth(corpId);
        CorpStaffVO creator = null;
        if(corpSuiteAuth != null && corpSuiteAuth.getAuthUserId() != null){
            //  读取出该用户作为创建者
            creator = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, corpSuiteAuth.getAuthUserId());
            if(creator != null && creator.getRsqUserId() == null){
                creator.setRsqUsername(generateRsqUsername(suiteVO.getRsqAppName()));
                creator.setRsqPassword(generateRsqPassword());
            }
        }
        RsqCorp rsqCorp = rsqRequestHelper.createCorp(suiteVO, corpVO, creator);

        corpVO.setRsqId(String.valueOf(rsqCorp.getId()));
        corpManageService.updateRsqInfo(corpVO);

        return corpVO;
    }

    /**
     * 查看是否有需要充值的订单，如果有那么调用接口进行充值
     * @param corpId
     */
    private void checkOrderCharge(String corpId){
        OrderEventVO dbEvent = orderManageService.getOrderEventByCorpIdAndLatest(corpId);
        if(dbEvent == null){
            return;
        }
        OrderStatusVO dbOrderStatus = orderManageService.getOrderStatusByOrderId(dbEvent.getOrderId());
        //  要么orderStatus不存在，要么orderStatus的状态为初始的状态，这两种情况都进行充值
        if(dbOrderStatus == null || SystemConstant.ORDER_STATUS_PAID.equals(dbOrderStatus.getStatus())){
            //  使用eventBus异步调用
            OrderChargeEvent event = new OrderChargeEvent();
            event.setSuiteKey(dbEvent.getSuiteKey());
            event.setOrderEventId(dbEvent.getId());
            asyncOrderChargeEventBus.post(event);
        }
    }

    /**
     * 将departmentVO同步到日事清，并递归同步其子部门
     * @param departmentVO
     * @return
     */
    private void createRecursiveSubDepartment(CorpDepartmentVO departmentVO){
        String corpId = departmentVO.getCorpId();
        Long deptId = departmentVO.getDeptId();

        this.createRsqDepartment(departmentVO);

        List<CorpDepartmentVO> deptList =  corpDepartmentManageService.getCorpDepartmentListByCorpIdAndParentId(corpId, deptId);
        if(0 == deptList.size()){
            return;
        }
        for(CorpDepartmentVO dept : deptList){
            createRecursiveSubDepartment(dept);
        }
    }

    /**
     * 将一个公司的所有员工同步到日事清
     * @param corpId
     * @return
     */
    private void createAllCorpStaff(String corpId){
        List<CorpStaffVO> list = corpStaffManageService.getCorpStaffListByCorpId(corpId);
        for(CorpStaffVO staffVO : list){
            this.createRsqTeamStaff(staffVO);
        }
    }

    /**
     * 设置corpId中的所有管理员
     * @param corpId
     * @return
     */
    private void updateAllCorpAdmin(String corpId){
        List<CorpStaffVO> list = corpStaffManageService.getCorpStaffListByCorpIdAndIsAdmin(corpId, true);
        for(CorpStaffVO staffVO : list){
            this.updateRsqTeamAdmin(staffVO);
        }
    }

    /**
     * 创建部门，分为以下几步：
     * 1  根据corpId和deptId查询是否有记录，是否department的rsqId存在，则直接返回department
     * 2  如果记录不存在或者rsqId不存在，则发送到日事清服务器请求创建
     * 3  保存返回结果
     * @param departmentVO  部门的数据库对象
     * @return
     */
    private void createRsqDepartment(CorpDepartmentVO departmentVO){
        String corpId = departmentVO.getCorpId();
        //  如果departmentVO的rsqId存在，则不重新创建部门
        if(null != departmentVO.getRsqId()){
            return;
        }

        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);

        //  如果corpVO的rsqId不存在，那么返回失败
        if(null == corpVO.getRsqId()){
            throw new BizRuntimeException("department corp rsqId not exist: " + departmentVO);
        }

        CorpDepartmentVO parentDept = departmentVO.getParentId() == null ? null :
                corpDepartmentManageService.getCorpDepartmentByCorpIdAndDeptId(corpId, departmentVO.getParentId());

        //  如果corpVO的rsqId不存在，那么就请求日事清服务器创建，创建成功后更新corpVO
        SuiteVO suiteVO = suiteManageService.getSuite();

        RsqDepartment rsqDept = rsqRequestHelper.createDepartment(suiteVO, corpVO, departmentVO, parentDept);
        departmentVO.setRsqId(String.valueOf(rsqDept.getId()));
        corpDepartmentManageService.updateRsqInfo(departmentVO);
    }

    /**
     * 创建公司员工
     * @param staffVO
     * @return
     */
    private void createRsqTeamStaff(CorpStaffVO staffVO) {
        //  如果staffVO的rsqUserId存在，则不重新发送请求创建
        if(null != staffVO.getRsqUserId()){
            return;
        }

        String userId = staffVO.getUserId();
        String corpId = staffVO.getCorpId();
        //  生成用户信息

        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        if(null == corpVO || null == corpVO.getRsqId()){
            throw new BizRuntimeException("rsqId not found in corpVO: " + corpVO);
        }

        SuiteVO suiteVO = suiteManageService.getSuite();

        //  保存用户信息到日事清系统
        String username = generateRsqUsername(suiteVO.getRsqAppName());  //自动生成用户名
        String password = generateRsqPassword();  //自动生成明文密码
        staffVO.setRsqUsername(username);
        staffVO.setRsqPassword(password);

        JSONArray rsqIdArray = convertRsqDepartment(corpId, staffVO.getDepartment());
        if(null == rsqIdArray){
            throw new BizRuntimeException("系统异常:one of the staff department don't have rsqId: " + corpId);
        }
        Map params = new HashMap<String, Object>();
        params.put("rsqDepartment", rsqIdArray);

        RsqUser user = rsqRequestHelper.createUser(suiteVO, staffVO, corpVO, params);

        staffVO.setRsqUserId(String.valueOf(user.getId()));
        // 为控制并发，保证username和password与日事清系统一致，使用返回值作为rsqUsername和rsqPassword
        staffVO.setRsqUsername(user.getUsername());
        staffVO.setRsqPassword(generateRsqPassword());

        corpStaffManageService.updateRsqInfo(staffVO);
    }

    /**
     * 设置staffVO是否是管理员
     * @param staffVO
     * @return
     */
    private void updateRsqTeamAdmin(CorpStaffVO staffVO) {
        SuiteVO suiteVO = suiteManageService.getSuite();
        rsqRequestHelper.setUserAdmin(suiteVO, staffVO);
    }

    private String generateRsqUsername(String appName){
        StringBuffer sb = new StringBuffer();
        sb.append(RandomStringUtils.randomAlphabetic(5))
                .append("_")
                .append(new Date().getTime())
                .append("@")
                .append(appName)
                .append(".rishiqing.com");
        return  sb.toString();
    }

    private String generateRsqPassword(){
        return "123456";
    }

    private JSONArray convertRsqDepartment(String corpId, String dingDepartment){
        JSONArray orgArray = JSON.parseArray(dingDepartment);
        JSONArray rsqArray = new JSONArray();
        Iterator it = orgArray.iterator();

        while (it.hasNext()){
            Long orgId = new Long((Integer)it.next());
            CorpDepartmentVO departmentVO = corpDepartmentManageService.getCorpDepartmentByCorpIdAndDeptId(corpId, orgId);
            String rsqId = departmentVO.getRsqId();
            if(null == rsqId){
                return null;
            }
            rsqArray.add(rsqId);
        }
        return rsqArray;
    }

    private JSONArray convertRsqDepartment(String corpId, List departmentList){
        JSONArray rsqArray = new JSONArray();
        Iterator it = departmentList.iterator();

        while (it.hasNext()){
            Long orgId;
            Object num = it.next();
            if(num instanceof Integer){
                orgId = new Long((Integer)num);
            }else{
                orgId = (Long)num;
            }
            CorpDepartmentVO departmentVO = corpDepartmentManageService.getCorpDepartmentByCorpIdAndDeptId(corpId, orgId);
            String rsqId = departmentVO.getRsqId();
            if(null == rsqId){
                return null;
            }
            rsqArray.add(rsqId);
        }
        return rsqArray;
    }

    /**
     * 将一个公司的所有部门同步到日事清
     * @param corpId
     * @return
     */
    @Deprecated
    private void createAllCorpDepartment(String corpId){
        List<CorpDepartmentVO> deptList = corpDepartmentManageService.getCorpDepartmentListByCorpId(corpId);

        for(CorpDepartmentVO dept : deptList){
            this.createRsqDepartment(dept);
        }
    }
}
