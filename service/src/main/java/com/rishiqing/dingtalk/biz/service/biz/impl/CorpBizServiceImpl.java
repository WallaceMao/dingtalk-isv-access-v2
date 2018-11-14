package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.rishiqing.dingtalk.biz.converter.corp.CorpConverter;
import com.rishiqing.dingtalk.biz.converter.suite.CorpAppConverter;
import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.biz.service.util.DeptService;
import com.rishiqing.dingtalk.biz.service.util.StaffService;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgSyncEvent;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAppVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpAppManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 跟公司相关的上层业务服务，用来处理开通应用等业务
 * @author Wallace Mao
 * Date: 2018-11-03 17:43
 */
public class CorpBizServiceImpl implements CorpBizService {
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpAppManageService corpAppManageService;
    @Autowired
    private CorpSuiteAuthManageService corpSuiteAuthManageService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private EventBus asyncCorpOrgSyncEventBus;

    /**
     * 激活应用。
     * 激活应用兼容两种方式：
     * （1）传统callback的方式。只有corpId，那么在本方法中会获取授权公司信息、授权用户信息、授权信息、授权范围等。
     * （2）钉钉云推送的方式。会获取到包含授权公司信息、授权用户信息、授权信息的json字段
     * 本方法只处理将企业部门以及人员信息从钉钉抓取到auth server。
     * 分为如下几步：
     * 1. 获取企业基本信息（实际上是授权信息），如果没有通讯录可见范围的话要获取通讯录可见范围
     * 2. 保存企业基本信息
     * 3. 根据通讯录可见范围保存部门
     * 4. 获取用户，保存用户
     * 5. eventBus抛出事件，进行后续处理（推送到日事清等）
     * @param corpAuthInfo
     */
    @Override
    public void activateCorpApp(CorpAuthInfoVO corpAuthInfo) {
        SuiteVO suiteVO = suiteManageService.getSuite();
        String suiteKey = suiteVO.getSuiteKey();

        String corpId = corpAuthInfo.getAuthCorpInfo().getCorpId();
        if(corpId == null){
            throw new BizRuntimeException("corpId is null, corpAuthInfo is: " + corpAuthInfo);
        }

        //  1. 如果corpAuthInfo中authInfo的授权信息不存在，那么重新获取授权信息
        if(corpAuthInfo.getAuthInfo() == null){
            //TODO  根据corpId获取authInfo，走钉钉的获取授权信息的接口
        }

        //  2. 如果corpAuthInfo中的authScope不存在，那么重新获取可见范围信息
        if(corpAuthInfo.getAuthScope() == null){
            //TODO  获取企业的可见范围
        }

        //  在这里corpAuthInfo是完整的包含可见范围和授权的企业信息的对象

        //  3.  保存公司信息
        corpManageService.saveOrUpdateCorp(
                CorpConverter.corpAuthInfoVO2CorpVO(corpAuthInfo)
        );

        //  4.  保存corpApp的关联信息
        List<CorpAppVO> corpAppVOList = CorpAppConverter.corpAuthInfoVO2AppVOList(corpAuthInfo);
        for(CorpAppVO corpAppVO : corpAppVOList){
            corpAppManageService.saveOrUpdateCorpApp(corpAppVO);
        }

        //  5.  根据可见范围获取部门
        CorpAuthScopeInfoVO scopeInfo = corpAuthInfo.getAuthScope();
        CorpAuthScopeInfoVO.AuthOrgScopes scopes = scopeInfo.getAuthOrgScopes();
        deptService.fetchAndSaveCorpDepartmentList(corpId, scopes.getAuthedDept());

        //  6.  根据可见范围获取成员信息。获取的成员信息有两部分：
        // （1）第一部分是可见范围内的部门所包含的成员信息
        // （2）第二部分是可见范围内的成员列表所包含的成员信息
        staffService.fetchAndSaveCorpDepartmentStaff(corpId);
        staffService.fetchAndSaveCorpStaffList(corpId, scopes.getAuthedUser());


        //  7.  处理授权方管理员，谁开通的日事清，就以谁作为创建者。
        CorpAuthInfoVO.AuthUserInfo authUserInfo = corpAuthInfo.getAuthUserInfo();
        CorpSuiteAuthVO corpSuite = corpSuiteAuthManageService.getCorpSuiteAuth(corpId);
        if(corpSuite == null){
            corpSuite = new CorpSuiteAuthVO();
            corpSuite.setSuiteKey(suiteKey);
            corpSuite.setCorpId(corpId);
        }
        corpSuite.setAuthUserId(authUserInfo.getUserId());
        corpSuiteAuthManageService.saveOrUpdateCorpSuiteAuth(corpSuite);

        //  8.  异步，更新钉钉的组织机构以及用户信息到本地，然后与ISV更新组织机构和人员信息
        CorpOrgSyncEvent corpOrgSyncEvent = new CorpOrgSyncEvent();
        corpOrgSyncEvent.setSuiteKey(suiteKey);
        corpOrgSyncEvent.setCorpId(corpId);
        asyncCorpOrgSyncEventBus.post(corpOrgSyncEvent);
    }

    /**
     * 变更授权
     * @param corpId
     */
    @Override
    public void changeCorpApp(String corpId){}

    /**
     * 解除授权
     * @param corpId
     */
    @Override
    public void relieveCorpApp(String corpId){}

    /**
     * 收到充值事件，做充值前的准备工作
     * @param orderEvent
     */
    @Override
    public void prepareChargeCorpApp(OrderEventVO orderEvent){}

    /**
     * 进行充值
     * @param orderId
     */
    @Override
    public void chargeCorpApp(Long orderId){}
}
