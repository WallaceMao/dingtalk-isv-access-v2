package com.rishiqing.dingtalk.api.service.config;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 17:38
 */
public interface ConfigService {

    Boolean isStaffCountAboveThreshold(Long staffCount);

    void closeStaffCountLimitFilter();
}
