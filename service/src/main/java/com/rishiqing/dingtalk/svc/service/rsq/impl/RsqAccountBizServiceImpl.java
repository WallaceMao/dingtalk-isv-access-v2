package com.rishiqing.dingtalk.svc.service.rsq.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.dingtalk.svc.constant.SystemConstant;
import com.rishiqing.dingtalk.svc.converter.order.OrderConverter;
import com.rishiqing.dingtalk.svc.service.util.QueueService;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.event.OrderChargeEvent;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderStatusVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpStaffManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.order.OrderManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.api.model.rsq.RsqCorp;
import com.rishiqing.dingtalk.api.model.rsq.RsqDepartment;
import com.rishiqing.dingtalk.api.model.rsq.RsqUser;
import com.rishiqing.dingtalk.api.service.rsq.RsqAccountBizService;
import com.rishiqing.dingtalk.req.rsq.auth.http.RsqRequestHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 用于企业授权后批量读取企业部门和成员信息
 * Created by Wallace on 2016/11/29.
 */
public class RsqAccountBizServiceImpl implements RsqAccountBizService {
    private static final Logger bizLogger = LoggerFactory.getLogger(RsqAccountBizServiceImpl.class);
    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpDepartmentManager corpDepartmentManager;
    @Autowired
    private CorpStaffManager corpStaffManager;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private RsqRequestHelper rsqRequestHelper;
    @Autowired(required = false)
    private QueueService queueService;

    @Autowired
    private AsyncEventBus asyncOrderChargeEventBus;

    /**
     * 同步所有的企业信息到日事清，包括：
     * 1  公司信息
     * 2  部门信息
     * 3  部门成员信息
     * 4  需要删除的部门的信息
     * 5  需要删除的人员的信息
     *
     * @return
     */
    @Override
    public void syncAllCreated(String corpId) {
        CorpVO corp = corpManager.getCorpByCorpId(corpId);
        boolean noRsqId = corp.getRsqId() == null;
        //  1  将组织架构信息全部同步
        this.syncCreated(corp);
        //  2  将需要删除的组织结构信息同步到日事清
        this.syncDeleted(corp);

        if (noRsqId) {
            //  2  当全部都同步成功后，发到corpAuthSuiteQueue队列中，由第三方异步处理
            //  发送生成全公司解决方案的消息
            queueService.sendToGenerateTeamSolution(corpId, null);
            //  公司中的人逐个发送
            List<CorpStaffVO> staffList = corpStaffManager.getCorpStaffListByCorpId(corpId);
            for (CorpStaffVO staffVO : staffList) {
                queueService.sendToGenerateStaffSolution(corpId, staffVO.getUserId());
            }
        }
    }

    @Override
    public void syncAllChanged(String corpId) {
        CorpVO corp = corpManager.getCorpByCorpId(corpId);
        //  1  将新增的组织架构信息全部同步
        this.syncCreated(corp);
        //  2  将需要删除的组织结构信息同步到日事清
        this.syncDeleted(corp);
    }

    /**
     * 设置corpId中的所有管理员
     *
     * @param corpId
     * @return
     */
    @Override
    public void updateAllCorpAdmin(String corpId, Long scopeVersion) {
        List<CorpStaffVO> list = corpStaffManager.getCorpStaffListByCorpIdAndIsAdminAndScopeVersion(
                corpId, true, scopeVersion);
        SuiteVO suiteVO = suiteManager.getSuite();
        rsqRequestHelper.setBatchTeamUserAdmin(suiteVO, corpId, list);
        // for (CorpStaffVO staffVO : list) {
        //     this.updateRsqTeamAdmin(staffVO);
        // }
    }

    private void syncCreated(CorpVO corp) {
        //  1  创建日事清企业
        String corpId = corp.getCorpId();
        Long scopeVersion = corp.getScopeVersion();
        this.createRsqTeam(corp);
        this.checkOrderCharge(corpId);
        //  2  创建企业部门。创建部门时存在三种情况：
        //  a. 包含deptId为1的部门，即全公司的部门已经获取到。这种情况
        //  b. 不包含deptId为1的部门，但是是全公司部门的一个子集
        //  c. 不包含任何部门。当用户开通时选择仅管理员可见会出现这种情况
        List<CorpDepartmentVO> topDeptList = corpDepartmentManager.listTopCorpDepartmentByScopeVersion(corpId, scopeVersion);
        if (topDeptList != null) {
            for (CorpDepartmentVO topDept : topDeptList) {
                this.createRecursiveSubDepartmentByScopeVersion(topDept, scopeVersion);
            }
        }

        //  3  新建企业部门成员
        this.createCorpStaffByScopeVersion(corpId, scopeVersion);

        // 4  fix bug:不能在这里更新企业部门的管理员状态。当用户修改可见范围时，旧的管理员如果也会被同步，而旧的管理员不在新的可见范围之内！
        // 这里应该等create和delete都同步完成了之后再更新管理员状态
        // this.updateAllCorpAdmin(corpId, scopeVersion);
    }

    private void syncDeleted(CorpVO corp) {
        String corpId = corp.getCorpId();
        Long scopeVersion = corp.getScopeVersion();

        //  6  检查是否有需要删除的成员
        this.deleteAllDeprecatedCorpStaff(corpId, scopeVersion);

        //  5  检查是否有需要删除的部门
        this.deleteAllDeprecatedCorpDepartment(corpId, scopeVersion);
    }

    @Override
    public void doRsqCharge(OrderStatusVO orderStatus) {
        if (orderStatus == null) {
            throw new BizRuntimeException("order status is null");
        }
        String corpId = orderStatus.getBuyCorpId();
        CorpVO corpVO = corpManager.getCorpByCorpId(corpId);
        if (corpVO.getRsqId() == null) {
            throw new BizRuntimeException("corp has no rsqId: " + corpVO);
        }

        // 保存OrderRsqPushEventVO
        OrderRsqPushEventVO rsqPushEvent = OrderConverter.orderStatusVO2OrderRsqPushEventVO(orderStatus);
        rsqPushEvent.setStatus(SystemConstant.ORDER_PUSH_STATUS_PENDING);
        rsqPushEvent.setRsqTeamId(Long.valueOf(corpVO.getRsqId()));
        orderManager.saveOrUpdateOrderRsqPushEvent(rsqPushEvent);

        //发送后台接口进行充值
        SuiteVO suiteVO = suiteManager.getSuite();
        OrderSpecItemVO specItemVO = orderManager.getOrderSpecItemByGoodsCodeAndItemCode(
                orderStatus.getGoodsCode(),
                orderStatus.getItemCode());
        rsqRequestHelper.doCharge(suiteVO, specItemVO, rsqPushEvent);

        // 更新OrderRsqPushEventDO的状态为success
        rsqPushEvent.setStatus(SystemConstant.ORDER_PUSH_STATUS_SUCCESS);
        orderManager.saveOrUpdateOrderRsqPushEvent(rsqPushEvent);
    }

    /**
     * 创建部门，分为以下几步：
     * 1  根据corpId和deptId查询是否有记录，是否department的rsqId存在，则直接返回department
     * 2  如果记录不存在或者rsqId不存在，则发送到日事清服务器请求创建
     * 3  保存返回结果
     *
     * @param departmentVO 部门的数据库对象
     * @return
     */
    @Override
    public void createRsqDepartment(CorpDepartmentVO departmentVO) {
        String corpId = departmentVO.getCorpId();
        // //  如果departmentVO的rsqId存在，则不重新创建部门
        // if (null != departmentVO.getRsqId()) {
        //     return;
        // }

        CorpVO corpVO = corpManager.getCorpByCorpId(corpId);

        //  如果corpVO的rsqId不存在，那么返回失败
        if (null == corpVO.getRsqId()) {
            throw new BizRuntimeException("department corp rsqId not exist: " + departmentVO);
        }

        CorpDepartmentVO parentDept = departmentVO.getParentId() == null ? null :
                corpDepartmentManager.getCorpDepartmentByCorpIdAndDeptId(corpId, departmentVO.getParentId());

        //  如果corpVO的rsqId不存在，那么就请求日事清服务器创建，创建成功后更新corpVO
        SuiteVO suiteVO = suiteManager.getSuite();

        RsqDepartment rsqDept = rsqRequestHelper.createDepartment(suiteVO, corpVO, departmentVO, parentDept);
        departmentVO.setRsqId(String.valueOf(rsqDept.getId()));
        corpDepartmentManager.updateRsqInfo(departmentVO);
    }

    @Override
    public void updateRsqDepartment(CorpDepartmentVO departmentVO) {
        if (departmentVO == null) {
            return;
        }
        // String corpId = departmentVO.getCorpId();
        // Long parentId = departmentVO.getParentId();

        //  suiteKey
        SuiteVO suiteVO = suiteManager.getSuite();
        // CorpDepartmentVO parentDepartmentVO = null;
        // if (null != parentId) {
        //     parentDepartmentVO = corpDepartmentManager.getCorpDepartmentByCorpIdAndDeptId(corpId, parentId);
        //     if (null == parentDepartmentVO.getRsqId()) {
        //         throw new BizRuntimeException("parent department corp rsqId not exist: " + departmentVO);
        //     }
        // }
        //  提交更新
        rsqRequestHelper.updateDepartment(suiteVO, departmentVO);
    }

    @Override
    public void deleteRsqDepartment(CorpDepartmentVO departmentVO) {
        if (null == departmentVO.getRsqId()) {
            return;
        }
        //  suiteKey
        SuiteVO suiteVO = suiteManager.getSuite();
        //  提交更新
        rsqRequestHelper.deleteDepartment(suiteVO, departmentVO);
    }

    /**
     * 创建公司员工
     *
     * @param staffVO
     * @return
     */
    @Override
    public void createRsqTeamStaff(CorpStaffVO staffVO) {
        String userId = staffVO.getUserId();
        String corpId = staffVO.getCorpId();
        //  生成用户信息

        CorpVO corpVO = corpManager.getCorpByCorpId(corpId);
        if (null == corpVO || null == corpVO.getRsqId()) {
            throw new BizRuntimeException("rsqId not found in corpVO: " + corpVO);
        }

        SuiteVO suiteVO = suiteManager.getSuite();

        //  保存用户信息到日事清系统
        String username = generateRsqUsername(suiteVO.getRsqAppName());  //自动生成用户名
        String password = generateRsqPassword();  //自动生成明文密码
        staffVO.setRsqUsername(username);
        staffVO.setRsqPassword(password);

        RsqUser user = rsqRequestHelper.createUser(suiteVO, staffVO, corpVO);

        staffVO.setRsqUserId(String.valueOf(user.getId()));
        // 为控制并发，保证username和password与日事清系统一致，使用返回值作为rsqUsername和rsqPassword
        staffVO.setRsqUsername(user.getUsername());
        staffVO.setRsqPassword(generateRsqPassword());

        corpStaffManager.updateRsqInfo(staffVO);
    }

    @Override
    public void updateRsqTeamStaff(CorpStaffVO corpStaffVO) {
        //  suiteKey
        SuiteVO suiteVO = suiteManager.getSuite();
        //  提交更新
        RsqUser rsqUser = rsqRequestHelper.updateUser(suiteVO, corpStaffVO);

        if (corpStaffVO.getRsqUserId() == null) {
            corpStaffVO.setRsqUserId(String.valueOf(rsqUser.getId()));
            corpStaffManager.updateRsqInfo(corpStaffVO);
        }
    }

    @Override
    public void removeRsqTeamStaff(CorpStaffVO corpStaffVO) {
        if (null == corpStaffVO) {
            return;
        }
        //  suiteKey
        SuiteVO suiteVO = suiteManager.getSuite();
        //  提交更新
        rsqRequestHelper.removeUser(suiteVO, corpStaffVO);
    }

    @Override
    public void updateRsqTeamStaffSetAdmin(CorpStaffVO corpStaffVO) {
        //  suiteKey
        SuiteVO suiteVO = suiteManager.getSuite();
        //  提交更新
        rsqRequestHelper.setUserAdmin(suiteVO, corpStaffVO);
    }

    /**
     * 创建公司，分为以下几步：
     * 1  根据corpId查询是否有记录，是否rsqId存在，如果rsqId存在，则直接返回rsqId
     * 2  如果记录不存在或者rsqId不存在，则发送到日事清服务器请求创建
     * 3  保存返回结果
     *
     * @param corpVO
     * @return
     */
    private CorpVO createRsqTeam(CorpVO corpVO) {
        //  如果corpVO的rsqId存在，那么直接返回
        if (null != corpVO.getRsqId()) {
            return corpVO;
        }

        //  如果corpVO的rsqId不存在，那么就请求日事清服务器创建，创建成功后更新corpVO
        SuiteVO suiteVO = suiteManager.getSuite();
        //  找到开通微应用的管理员，作为创建者传给接口
        CorpStaffVO creator = corpManager.findATeamCreator(corpVO.getCorpId());
        if (creator != null && creator.getRsqUserId() == null) {
            creator.setRsqUsername(generateRsqUsername(suiteVO.getRsqAppName()));
            creator.setRsqPassword(generateRsqPassword());
        }
//        CorpSuiteAuthVO corpSuiteAuth = corpSuiteAuthManageService.getCorpSuiteAuth(corpId);
//        CorpStaffVO creator = null;
//        if(corpSuiteAuth != null && corpSuiteAuth.getAuthUserId() != null){
//            //  读取出该用户作为创建者
//            creator = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, corpSuiteAuth.getAuthUserId());
//            if(creator != null && creator.getRsqUserId() == null){
//                creator.setRsqUsername(generateRsqUsername(suiteVO.getRsqAppName()));
//                creator.setRsqPassword(generateRsqPassword());
//            }
//        }
        RsqCorp rsqCorp = rsqRequestHelper.createCorp(suiteVO, corpVO, creator);

        corpVO.setRsqId(String.valueOf(rsqCorp.getId()));
        corpManager.updateRsqInfo(corpVO);

        return corpVO;
    }

    /**
     * 查看是否有需要充值的订单，如果有那么调用接口进行充值
     *
     * @param corpId
     */
    private void checkOrderCharge(String corpId) {
        OrderEventVO dbEvent = orderManager.getOrderEventByCorpIdAndLatest(corpId);
        if (dbEvent == null) {
            return;
        }
        OrderStatusVO dbOrderStatus = orderManager.getOrderStatusByOrderId(dbEvent.getOrderId());
        //  要么orderStatus不存在，要么orderStatus的状态为初始的状态，这两种情况都进行充值
        if (dbOrderStatus == null || SystemConstant.ORDER_STATUS_PAID.equals(dbOrderStatus.getStatus())) {
            //  使用eventBus异步调用
            OrderChargeEvent event = new OrderChargeEvent();
            event.setSuiteKey(dbEvent.getSuiteKey());
            event.setOrderId(dbEvent.getOrderId());
            event.setCorpId(dbEvent.getBuyCorpId());
            asyncOrderChargeEventBus.post(event);
        }
    }

    /**
     * 将departmentVO同步到日事清，并递归同步其子部门
     *
     * @param departmentVO
     * @param scopeVersion
     * @return
     */
    @Override
    public void createRecursiveSubDepartmentByScopeVersion(CorpDepartmentVO departmentVO, Long scopeVersion) {
        String corpId = departmentVO.getCorpId();
        Long deptId = departmentVO.getDeptId();

        this.createRsqDepartment(departmentVO);

        List<CorpDepartmentVO> deptList = corpDepartmentManager.getCorpDepartmentListByCorpIdAndParentIdAndScopeVersion(
                corpId, deptId, scopeVersion);
        if (0 == deptList.size()) {
            return;
        }
        for (CorpDepartmentVO dept : deptList) {
            createRecursiveSubDepartmentByScopeVersion(dept, scopeVersion);
        }
    }

    /**
     * 将一个公司的所有员工同步到日事清
     *
     * @param corpId
     * @return
     */
    @Override
    public void createCorpStaffByScopeVersion(String corpId, Long scopeVersion) {
        List<CorpStaffVO> list = corpStaffManager.getCorpStaffListByCorpIdAndScopeVersion(corpId, scopeVersion);
        for (CorpStaffVO staffVO : list) {
            if (null == staffVO.getRsqUserId()) {
                this.createRsqTeamStaff(staffVO);
            } else {
                this.updateRsqTeamStaff(staffVO);
            }
        }
    }

    /**
     * 设置staffVO是否是管理员
     *
     * @param staffVO
     * @return
     */
    private void updateRsqTeamAdmin(CorpStaffVO staffVO) {
        SuiteVO suiteVO = suiteManager.getSuite();
        rsqRequestHelper.setUserAdmin(suiteVO, staffVO);
    }

    private String generateRsqUsername(String appName) {
        StringBuffer sb = new StringBuffer();
        sb.append(RandomStringUtils.randomAlphabetic(5))
                .append("_")
                .append(new Date().getTime())
                .append("@")
                .append(appName)
                .append(".rishiqing.com");
        return sb.toString();
    }

    private String generateRsqPassword() {
        return "123456";
    }

    private JSONArray convertRsqDepartment(String corpId, String dingDepartment) {
        JSONArray orgArray = JSON.parseArray(dingDepartment);
        JSONArray rsqArray = new JSONArray();

        for (int i = 0; i < orgArray.size(); i++) {
            Long orgId = orgArray.getLong(i);
            CorpDepartmentVO departmentVO = corpDepartmentManager.getCorpDepartmentByCorpIdAndDeptId(corpId, orgId);
            String rsqId = departmentVO.getRsqId();
            if (null == rsqId) {
                return null;
            }
            rsqArray.add(rsqId);
        }
        return rsqArray;
    }

    private JSONArray convertRsqDepartment(String corpId, List<Long> departmentIdList) {
        JSONArray rsqArray = new JSONArray();
        for (Long deptId : departmentIdList) {
            CorpDepartmentVO departmentVO = corpDepartmentManager.getCorpDepartmentByCorpIdAndDeptId(corpId, deptId);
            if (departmentVO == null) {
                continue;
            }
            String rsqId = departmentVO.getRsqId();
            if (null == rsqId) {
                continue;
            }
            rsqArray.add(rsqId);
        }
        return rsqArray;
    }

    private void deleteAllDeprecatedCorpDepartment(String corpId, Long scopeVersion) {
        List<CorpDepartmentVO> list = corpDepartmentManager.getCorpDepartmentListByCorpIdAndScopeVersionLessThan(
                corpId, scopeVersion
        );
        for (CorpDepartmentVO dept : list) {
            this.deleteRsqDepartment(dept);
        }
    }

    private void deleteAllDeprecatedCorpStaff(String corpId, Long scopeVersion) {
        List<CorpStaffVO> list = corpStaffManager.getCorpStaffListByCorpIdAndScopeVersionLessThan(corpId, scopeVersion);
        for (CorpStaffVO staff : list) {
            //这地方要try catch出来，防止循环中有一个失败了，循环就不会继续进行了
            try {
                this.removeRsqTeamStaff(staff);
            } catch (Exception e) {
                bizLogger.error(
                        LogFormatter.format(
                                LogFormatter.LogEvent.EXCEPTION,
                                "delete deprecated corp staff error",
                                LogFormatter.getKV("corpId", corpId),
                                LogFormatter.getKV("staff", staff)
                        )
                );
            }
        }
    }

    /**
     * 将一个公司的所有部门同步到日事清
     *
     * @param corpId
     * @return
     */
    @Deprecated
    private void createAllCorpDepartment(String corpId) {
        List<CorpDepartmentVO> deptList = corpDepartmentManager.getCorpDepartmentListByCorpId(corpId);

        for (CorpDepartmentVO dept : deptList) {
            this.createRsqDepartment(dept);
        }
    }
}
