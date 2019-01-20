package com.rishiqing.dingtalk.req.dingtalk.auth.http.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiServiceGetAuthInfoRequest;
import com.dingtalk.api.response.OapiServiceGetAuthInfoResponse;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestCommonHelper;
import com.taobao.api.ApiException;

/**
 * @author Wallace Mao
 * Date: 2019-01-17 19:18
 */
public class SuiteTopRequestCommonHelper implements SuiteRequestCommonHelper {
    @Override
    public OapiServiceGetAuthInfoResponse getCorpAuthInfo(String suiteKey, String suiteSecret, String suiteTicket, String corpId) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_auth_info");
        OapiServiceGetAuthInfoRequest req = new OapiServiceGetAuthInfoRequest();
        req.setAuthCorpid(corpId);
        try {
            return client.execute(req, suiteKey, suiteSecret, suiteTicket);
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }
}
