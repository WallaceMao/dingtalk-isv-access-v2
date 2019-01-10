package com.rishiqing.dingtalk.manager.dingpush.impl;

import com.rishiqing.dingtalk.converter.dingpush.OpenGlobalLockConverter;
import com.rishiqing.dingtalk.dao.mapper.dingpush.OpenGlobalLockDao;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenGlobalLockVO;
import com.rishiqing.dingtalk.manager.dingpush.OpenGlobalLockManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 23:46
 */
public class OpenGlobalLockManagerImpl implements OpenGlobalLockManager {
    @Autowired
    private OpenGlobalLockDao openGlobalLockDao;
    @Override
    public OpenGlobalLockVO getOpenGlobalLockByLockKey(String lockKey) {
        return OpenGlobalLockConverter.openGlobalLockDO2openGlobalLockVO(
                openGlobalLockDao.getOpenGlobalLockByLockKey(lockKey)
        );
    }

    @Override
    public void updateStatus(OpenGlobalLockVO lock) {
        openGlobalLockDao.updateStatus(
                OpenGlobalLockConverter.openGlobalLockVO2openGlobalLockDO(lock)
        );
    }

    @Override
    public void saveOrUpdateOpenGlobalLock(OpenGlobalLockVO lock) {
        openGlobalLockDao.saveOrUpdateOpenGlobalLock(
                OpenGlobalLockConverter.openGlobalLockVO2openGlobalLockDO(lock)
        );
    }
}
