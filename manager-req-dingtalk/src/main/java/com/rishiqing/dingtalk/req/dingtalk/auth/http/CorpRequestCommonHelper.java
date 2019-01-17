package com.rishiqing.dingtalk.req.dingtalk.auth.http;

import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;

/**
 * @author Wallace Mao
 * Date: 2019-01-16 11:03
 */
public interface CorpRequestCommonHelper {
    OapiDepartmentGetResponse getCorpDepartment(String token, Long deptId);

    OapiUserGetResponse getCorpStaff(String token, String userId);
}
