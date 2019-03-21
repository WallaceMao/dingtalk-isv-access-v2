package com.rishiqing.dingtalk.mgr.dingpush.manager;

import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenGlobalLockVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:46
 */
public interface OpenGlobalLockManager {
    OpenGlobalLockVO getOpenGlobalLockByLockKey(String lockKey);

    void updateStatus(OpenGlobalLockVO lock);

    void saveOrUpdateOpenGlobalLock(OpenGlobalLockVO lock);
}
