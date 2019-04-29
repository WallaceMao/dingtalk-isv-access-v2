package com.rishiqing.dingtalk.svc.dingpush.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.filter.FilterManager;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.svc.dingpush.manager.SuiteSyncActionManager;
import com.rishiqing.dingtalk.svc.service.biz.impl.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Da Shuai
 * @create: 2019-04-29 10:53
 */
public class FilterSuiteSyncActionManagerImpl implements SuiteSyncActionManager {
    //企业授权同步行为：1."org_suite_auth" ：表示企业授权套件  2."org_suite_change"：表示企业变更授权范围
    private static final String[] ORG_SUITE_ARR = new String[]{"org_suite_auth", "org_suite_change"};
    @Autowired
    private FilterManager filterManager;
    @Autowired
    @Qualifier("baseSuiteSyncActionManager")
    private SuiteSyncActionManager baseSuiteSyncActionManager;
    @Autowired
    private StaffService staffService;

    @Override
    public void handleSyncData(OpenSyncBizDataVO data) {
        String corpId = data.getCorpId();
        //获得同步行为
        JSONObject dataJsonObject = JSONObject.parseObject(data.getBizData());
        String syncAction = SuiteDbCheckConverter.json2SyncActionString(dataJsonObject);
        //如果同步行为需要特殊处理
        if (Arrays.binarySearch(ORG_SUITE_ARR, syncAction) >= 0) {
            //获得企业授权信息
            CorpAuthInfoVO corpAuthInfoVO = SuiteDbCheckConverter.json2CorpSuiteAuthInfo(dataJsonObject);
            CorpAuthScopeInfoVO.AuthOrgScopes authOrgScopes = corpAuthInfoVO.getAuthScope().getAuthOrgScopes();
            //企业授权的部门id列表
            List<Long> authedDept = authOrgScopes.getAuthedDept();
            //企业授权的员工userId列表
            List<String> authedUser = authOrgScopes.getAuthedUser();
            //判断企业员工是否超过阈值，超员直接抛出异常
            staffService.isCorpStaffCountAboveThreshold(authedUser, authedDept, corpId);
            baseSuiteSyncActionManager.handleSyncData(data);
        } else {
            baseSuiteSyncActionManager.handleSyncData(data);
        }
    }
}
