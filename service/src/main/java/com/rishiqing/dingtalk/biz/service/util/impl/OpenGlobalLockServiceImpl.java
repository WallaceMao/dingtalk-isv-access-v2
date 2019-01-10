package com.rishiqing.dingtalk.biz.service.util.impl;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenGlobalLockVO;
import com.rishiqing.dingtalk.isv.api.service.util.OpenGlobalLockService;
import com.rishiqing.dingtalk.manager.dingpush.OpenGlobalLockManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:43
 */
public class OpenGlobalLockServiceImpl implements OpenGlobalLockService {
    private static final Logger bizLogger = LoggerFactory.getLogger(OpenGlobalLockServiceImpl.class);

    private static final String LOCK_STATUS_OPEN= "open";
    private static final String LOCK_STATUS_LOCKED= "locked";
    @Autowired
    private OpenGlobalLockManager openGlobalLockManager;

    /**
     * 请求锁，步骤如下：
     * 1  开启事务
     * 2  使用select ... for update，获取指定lockKey的记录
     * 3  如果记录不存在，那么插入初始化记录，并设置默认的status为“open”，设置lock为新插入的lock
     * 4  如果记录存在，那么设置lock为查询到的lock
     * 至此，成功设置lock为lock记录，下面开始判断lock记录的状态
     * 5  如果lock的status不为"open"，那么说明已经被其他线程锁住直接返回null，
     * 6  如果lock的status为"open"，那么首先更新lock，将status设置为“locked”，返回lock
     * 结束事务
     * @param lockKey
     * @return
     */
    @Override
    @Transactional(value = "transactionManagerDingpush")
    public OpenGlobalLockVO requireOpenGlobalLock(String lockKey){
        OpenGlobalLockVO lock = openGlobalLockManager.getOpenGlobalLockByLockKey(lockKey);
        if(lock == null){
            lock = saveDefaultLock(lockKey);
        }

        if(!LOCK_STATUS_OPEN.equals(lock.getStatus())){
            bizLogger.warn(LogFormatter.format(
                    LogFormatter.LogEvent.END,
                    "global lock锁被占用",
                    new LogFormatter.KeyValue("lockKey", lockKey)
            ));
            return null;
        }
        lock.setStatus(LOCK_STATUS_LOCKED);
        openGlobalLockManager.updateStatus(lock);
        return lock;
    }

    @Override
    @Transactional(value = "transactionManagerDingpush")
    public void releaseOpenGlobalLock(String lockKey){
        OpenGlobalLockVO lock = openGlobalLockManager.getOpenGlobalLockByLockKey(lockKey);
        if(lock == null){
            return;
        }
        lock.setStatus(LOCK_STATUS_OPEN);
        openGlobalLockManager.updateStatus(lock);
    }

    private OpenGlobalLockVO saveDefaultLock(String lockKey){
        OpenGlobalLockVO lock = new OpenGlobalLockVO();
        lock.setLockKey(lockKey);
        lock.setStatus(LOCK_STATUS_OPEN);
        openGlobalLockManager.saveOrUpdateOpenGlobalLock(lock);
        return lock;
    }
}
