package com.rishiqing.dingtalk.req.dingtalk.auth.http.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestCommonHelper;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.taobao.api.ApiException;

/**
 * @author Wallace Mao
 * Date: 2019-01-16 10:48
 */
public class CorpTopRequestCommonHelper implements CorpRequestCommonHelper {
    @Override
    public OapiDepartmentGetResponse getCorpDepartment(String token, Long deptId) {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest req = new OapiDepartmentGetRequest();
        req.setId(String.valueOf(deptId));
        req.setHttpMethod("GET");
        try {
            return client.execute(req, token);
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }
}
