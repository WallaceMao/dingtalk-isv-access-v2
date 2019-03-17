package com.rishiqing.dingtalk.req.dingtalk.auth.http.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiServiceGetAuthInfoRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiServiceGetAuthInfoResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestCommonHelper;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestJsonHelper;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestCommonHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2019-01-16 10:36
 */
public class CorpTopRequestJsonHelper implements CorpRequestJsonHelper {
    @Autowired
    private CorpRequestCommonHelper corpRequestCommonHelper;
    @Autowired
    private SuiteRequestCommonHelper suiteRequestCommonHelper;

    @Override
    public JSONObject getCorpDepartment(String token, Long deptId) {
        OapiDepartmentGetResponse resp = corpRequestCommonHelper.getCorpDepartment(token, deptId);
        return JSONObject.parseObject(resp.getBody());
    }

    @Override
    public JSONObject getCorpStaff(String corpToken, String userId) {
        OapiUserGetResponse resp = corpRequestCommonHelper.getCorpStaff(corpToken, userId);
        return JSONObject.parseObject(resp.getBody());
    }

    @Override
    public JSONObject getCorpInfo(String suiteKey, String suiteSecret, String suiteTicket, String corpId) {
        OapiServiceGetAuthInfoResponse resp = suiteRequestCommonHelper.getCorpAuthInfo(
                suiteKey, suiteSecret, suiteTicket, corpId);
        JSONObject json = JSONObject.parseObject(resp.getBody());
        JSONObject jsonCorp = json.getJSONObject("auth_corp_info");
        jsonCorp.put("errcode", json.getLong("errcode"));
        jsonCorp.put("errmsg", json.getString("errmsg"));
        return jsonCorp;
    }
}
