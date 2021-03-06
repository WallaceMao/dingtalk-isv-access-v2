package com.rishiqing.dingtalk.isv.api.service.util;

import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenGlobalLockVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:42
 */
public interface OpenGlobalLockService {
    OpenGlobalLockVO requireOpenGlobalLock(String lockKey);
    void releaseOpenGlobalLock(String lockKey);
}
