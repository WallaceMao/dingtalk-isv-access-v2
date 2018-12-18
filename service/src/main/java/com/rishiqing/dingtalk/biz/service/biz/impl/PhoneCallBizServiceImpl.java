package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.PhoneCallBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-12-13 18:00
 */
public class PhoneCallBizServiceImpl implements PhoneCallBizService {
    @Autowired
    private SuiteRequestHelper suiteRequestHelper;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private CorpSuiteAuthManageService corpSuiteAuthManageService;

    @Override
    public String callActivateAdmin(String corpId, String loginUserId) {
        // String loginUserId = getLoginUserId();
        SuiteTokenVO suiteTokenVO = suiteManageService.getSuiteToken();
        CorpSuiteAuthVO corpSuiteAuthVO  = corpSuiteAuthManageService.getCorpSuiteAuth(corpId);
        return suiteRequestHelper.callCorpUser(suiteTokenVO, corpSuiteAuthVO.getAuthUserId(), loginUserId, corpId);
    }

    @Override
    public void setCalleeList(String calleeList) {
        SuiteTokenVO suiteTokenVO = suiteManageService.getSuiteToken();
        suiteRequestHelper.setCalleeList(suiteTokenVO, calleeList);
    }

    @Override
    public void removeCalleeList(String calleeList) {
        SuiteTokenVO suiteTokenVO = suiteManageService.getSuiteToken();
        suiteRequestHelper.removeCalleeList(suiteTokenVO, calleeList);
    }
}
