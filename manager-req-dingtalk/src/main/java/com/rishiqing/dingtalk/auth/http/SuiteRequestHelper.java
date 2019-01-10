package com.rishiqing.dingtalk.auth.http;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 13:58
 */
public interface SuiteRequestHelper {
    CorpAuthInfoVO getCorpAuthInfo(SuiteVO suite, SuiteTicketVO suiteTicketVO, String corpId);

    CorpAuthScopeInfoVO getCorpAuthScopeInfo(SuiteVO suiteVO, CorpTokenVO corpToken);

    SuiteTokenVO getSuiteToken(SuiteVO suite, SuiteTicketVO suiteTicket);

    CorpTokenVO getCorpToken(String suiteKey, String suiteSecret, String suiteTicket, String corpId);

    CorpJSAPITicketVO getCorpJSAPITicket(CorpTokenVO corpToken);

    String callCorpUser(SuiteTokenVO suiteTokenVO, String authedCorpId, String authedStaffId, String staffId);

    void setCalleeList(SuiteTokenVO suiteTokenVO, String calleeList);

    void removeCalleeList(SuiteTokenVO suiteTokenVO, String calleeList);
}
