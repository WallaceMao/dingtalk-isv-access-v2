package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.auth.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.manager.suite.SuiteManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-09 19:21
 */
public class SuiteService {
    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private SuiteRequestHelper suiteRequestHelper;

    public void fetchAndSaveSuiteToken() {
        SuiteVO suite = suiteManager.getSuite();
        SuiteTicketVO suiteTicket = suiteManager.getSuiteTicket();
        SuiteTokenVO suiteToken = suiteRequestHelper.getSuiteToken(suite, suiteTicket);
        suiteManager.saveOrUpdateSuiteToken(suiteToken);
    }
}
