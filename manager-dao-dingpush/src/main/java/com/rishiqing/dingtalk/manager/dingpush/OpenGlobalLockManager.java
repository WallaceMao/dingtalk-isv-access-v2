package com.rishiqing.dingtalk.manager.dingpush;

import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenGlobalLockVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:46
 */
public interface OpenGlobalLockManager {
    OpenGlobalLockVO getOpenGlobalLockByLockKey(String lockKey);

    void updateStatus(OpenGlobalLockVO lock);

    void saveOrUpdateOpenGlobalLock(OpenGlobalLockVO lock);
}
