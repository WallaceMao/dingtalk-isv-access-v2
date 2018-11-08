package com.rishiqing.dingtalk.biz.http;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 13:58
 */
public interface SuiteRequestHelper {
    @Deprecated
    CorpSuiteAuthVO getPermanentCode(String suiteKey, String tmpAuthCode, String suiteAccessToken);

    CorpTokenVO getCorpToken(String corpId);

    CorpJSAPITicketVO getJSTicket(String suiteKey, String corpId, String accessToken);
}
