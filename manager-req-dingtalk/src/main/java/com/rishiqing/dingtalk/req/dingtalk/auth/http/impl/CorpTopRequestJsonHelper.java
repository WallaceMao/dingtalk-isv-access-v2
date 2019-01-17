package com.rishiqing.dingtalk.req.dingtalk.auth.http.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestCommonHelper;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2019-01-16 10:36
 */
public class CorpTopRequestJsonHelper implements CorpRequestJsonHelper {
    private CorpRequestCommonHelper corpRequestCommonHelper;

    @Autowired
    public CorpTopRequestJsonHelper(CorpRequestCommonHelper corpRequestCommonHelper) {
        this.corpRequestCommonHelper = corpRequestCommonHelper;
    }

    @Override
    public JSONObject getCorpDepartment(String token, String corpId, Long deptId) {
        OapiDepartmentGetResponse resp = corpRequestCommonHelper.getCorpDepartment(token, deptId);
        return JSONObject.parseObject(resp.getBody());
    }
}
