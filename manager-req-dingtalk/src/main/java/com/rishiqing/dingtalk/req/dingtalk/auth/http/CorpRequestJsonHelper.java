package com.rishiqing.dingtalk.req.dingtalk.auth.http;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Wallace Mao
 * 直接返回json格式的requestHelper，在某些情况下上层需要获取到返回结果
 * Date: 2019-01-16 10:35
 */
public interface CorpRequestJsonHelper {
    JSONObject getCorpDepartment(String token, String corpId, Long deptId);
}
