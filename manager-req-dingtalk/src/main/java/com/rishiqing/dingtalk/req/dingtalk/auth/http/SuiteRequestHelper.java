package com.rishiqing.dingtalk.req.dingtalk.auth.http;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;

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
