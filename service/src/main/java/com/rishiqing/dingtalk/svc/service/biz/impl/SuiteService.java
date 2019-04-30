package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
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

    /**
     * 更新套件token
     */
    public void fetchAndSaveSuiteToken() {
        SuiteVO suite = suiteManager.getSuite();
        SuiteTicketVO suiteTicket = suiteManager.getSuiteTicket();
        SuiteTokenVO suiteToken = suiteRequestHelper.getSuiteToken(suite, suiteTicket);
        suiteManager.saveOrUpdateSuiteToken(suiteToken);
    }
}
