package com.rishiqing.dingtalk.api.service.util;

import com.rishiqing.dingtalk.api.model.vo.suite.AuthGlobalLockVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:42
 */
public interface AuthGlobalLockService {
    AuthGlobalLockVO requireAuthGlobalLock(String lockKey);
    void releaseAuthGlobalLock(String lockKey);
}
