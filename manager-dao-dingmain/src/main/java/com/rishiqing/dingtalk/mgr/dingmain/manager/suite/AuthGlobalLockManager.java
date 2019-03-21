package com.rishiqing.dingtalk.mgr.dingmain.manager.suite;

import com.rishiqing.dingtalk.api.model.vo.suite.AuthGlobalLockVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:46
 */
public interface AuthGlobalLockManager {
    AuthGlobalLockVO getAuthGlobalLockByLockKey(String lockKey);

    void updateStatus(AuthGlobalLockVO lock);

    void saveOrUpdateAuthGlobalLock(AuthGlobalLockVO lock);
}
