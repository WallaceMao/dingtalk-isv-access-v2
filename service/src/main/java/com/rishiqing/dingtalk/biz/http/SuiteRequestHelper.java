package com.rishiqing.dingtalk.biz.http;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 13:58
 */
public interface SuiteRequestHelper {
    @Deprecated
    CorpSuiteAuthVO getPermanentCode(String suiteKey, String tmpAuthCode, String suiteAccessToken);

    SuiteTokenVO getSuiteToken(SuiteVO suite, SuiteTicketVO suiteTicket);

    CorpTokenVO getCorpToken(String corpId);

    CorpJSAPITicketVO getCorpJSAPITicket(CorpTokenVO corpToken);
}
