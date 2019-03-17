package com.rishiqing.dingtalk.api.service.util;

import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenGlobalLockVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:42
 */
public interface OpenGlobalLockService {
    OpenGlobalLockVO requireOpenGlobalLock(String lockKey);
    void releaseOpenGlobalLock(String lockKey);
}
