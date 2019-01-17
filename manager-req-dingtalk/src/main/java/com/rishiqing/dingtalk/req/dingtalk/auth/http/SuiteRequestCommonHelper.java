package com.rishiqing.dingtalk.req.dingtalk.auth.http;

import com.dingtalk.api.response.OapiServiceGetAuthInfoResponse;

/**
 * @author Wallace Mao
 * Date: 2019-01-17 19:18
 */
public interface SuiteRequestCommonHelper {
    OapiServiceGetAuthInfoResponse getCorpAuthInfo(String suiteKey, String suiteSecret, String suiteTicket, String corpId);
}
