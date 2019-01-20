package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.CorpSuiteAuthManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.api.service.biz.PhoneCallBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-12-13 18:00
 */
public class PhoneCallBizServiceImpl implements PhoneCallBizService {
    @Autowired
    private SuiteRequestHelper suiteRequestHelper;
    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CorpSuiteAuthManager corpSuiteAuthManager;

    @Override
    public String callActivateAdmin(String corpId, String loginUserId) {
        // String loginUserId = getLoginUserId();
        SuiteTokenVO suiteTokenVO = suiteManager.getSuiteToken();
        CorpSuiteAuthVO corpSuiteAuthVO  = corpSuiteAuthManager.getCorpSuiteAuth(corpId);
        return suiteRequestHelper.callCorpUser(suiteTokenVO, corpId, corpSuiteAuthVO.getAuthUserId(), loginUserId);
    }

    @Override
    public void setCalleeList(String calleeList) {
        SuiteTokenVO suiteTokenVO = suiteManager.getSuiteToken();
        suiteRequestHelper.setCalleeList(suiteTokenVO, calleeList);
    }

    @Override
    public void removeCalleeList(String calleeList) {
        SuiteTokenVO suiteTokenVO = suiteManager.getSuiteToken();
        suiteRequestHelper.removeCalleeList(suiteTokenVO, calleeList);
    }
}
