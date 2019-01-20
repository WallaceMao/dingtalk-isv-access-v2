package com.rishiqing.dingtalk.mgr.dingmain.manager.suite.impl;

import com.rishiqing.dingtalk.mgr.dingmain.converter.suite.AuthGlobalLockConverter;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.suite.AuthGlobalLockDao;
import com.rishiqing.dingtalk.api.model.vo.suite.AuthGlobalLockVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.AuthGlobalLockManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 23:46
 */
public class AuthGlobalLockManagerImpl implements AuthGlobalLockManager {
    @Autowired
    private AuthGlobalLockDao authGlobalLockDao;
    @Override
    public AuthGlobalLockVO getAuthGlobalLockByLockKey(String lockKey) {
        return AuthGlobalLockConverter.authGlobalLockDO2authGlobalLockVO(
                authGlobalLockDao.getAuthGlobalLockByLockKey(lockKey)
        );
    }

    @Override
    public void updateStatus(AuthGlobalLockVO lock) {
        authGlobalLockDao.updateStatus(
                AuthGlobalLockConverter.authGlobalLockVO2authGlobalLockDO(lock)
        );
    }

    @Override
    public void saveOrUpdateAuthGlobalLock(AuthGlobalLockVO lock) {
        authGlobalLockDao.saveOrUpdateAuthGlobalLock(
                AuthGlobalLockConverter.authGlobalLockVO2authGlobalLockDO(lock)
        );
    }
}
