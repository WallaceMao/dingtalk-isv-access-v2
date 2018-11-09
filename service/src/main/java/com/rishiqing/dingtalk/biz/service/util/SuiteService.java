package com.rishiqing.dingtalk.biz.service.util;

import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-09 19:21
 */
public class SuiteService {
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private SuiteRequestHelper suiteRequestHelper;

    public void fetchAndSaveSuiteToken() {
        SuiteVO suite = suiteManageService.getSuite();
        SuiteTicketVO suiteTicket = suiteManageService.getSuiteTicket();
        SuiteTokenVO suiteToken = suiteRequestHelper.getSuiteToken(suite, suiteTicket);
        suiteManageService.saveOrUpdateSuiteToken(suiteToken);
    }
}
