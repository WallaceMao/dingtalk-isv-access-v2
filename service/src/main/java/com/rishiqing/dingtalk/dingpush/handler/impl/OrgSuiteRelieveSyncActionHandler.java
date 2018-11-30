package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAppVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.model.suite.AppVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.AppManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgSuiteRelieveSyncActionHandler implements SyncActionHandler {
    @Autowired
    private AppManageService appManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private FailBizService failBizService;
    /**
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 开通套件微应用的企业corpid
     biz_id             ： 套件suiteid
     biz_data         ： 数据为如下Json格式，其中"syncAction" 字段取值含义如下：
     "org_suite_auth" ：表示企业授权套件
     "org_suite_change"：表示企业变更授权范围
     "org_suite_relieve"：表示企业解除授权
     需要删除的表中数据包括：
     isv_corp_app
     isv_corp_suite_auth
     isv_corp_suite_auth_fail
     isv_corp_suite_jsapi_ticket
     isv_corp_token
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        String corpId = data.getCorpId();
        //  删除团队后，暂时不会做任何处理
        AppVO app = appManageService.getDefaultAppVO();
        corpManageService.deleteCorpSuiteAuthByCorpId(corpId);
        corpManageService.deleteCorpAppByCorpId(corpId, app.getAppId());
        corpManageService.deleteCorpTokenByCorpId(corpId);
        corpManageService.deleteCorpJSAPITicketByCorpId(corpId);
        //  fail暂不处理
//        failBizService.deleteCorpSuiteAuthFailById();
    }
}
