package com.rishiqing.dingtalk.api.service.biz;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:27
 */
public interface SuiteDbCheckBizService {
    void checkDingPushEvent();

    void checkDingMediumPushEvent();

    Long getAuthedStaffCount(String corpId);
}
