package com.rishiqing.dingtalk.svc.service.util.impl;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.vo.suite.AuthGlobalLockVO;
import com.rishiqing.dingtalk.api.service.util.AuthGlobalLockService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.AuthGlobalLockManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:43
 */
public class AuthGlobalLockServiceImpl implements AuthGlobalLockService {
    private static final Logger bizLogger = LoggerFactory.getLogger(AuthGlobalLockServiceImpl.class);

    private static final String LOCK_STATUS_OPEN= "open";
    private static final String LOCK_STATUS_LOCKED= "locked";
    @Autowired
    private AuthGlobalLockManager authGlobalLockManager;

    /**
     * 请求锁，步骤如下：
     * 1  开启事务
     * 2  使用select ... for update，获取指定lockKey的记录
     * 3  如果记录不存在，那么插入初始化记录，并设置默认的status为“auth”，设置lock为新插入的lock
     * 4  如果记录存在，那么设置lock为查询到的lock
     * 至此，成功设置lock为lock记录，下面开始判断lock记录的状态
     * 5  如果lock的status不为"auth"，那么说明已经被其他线程锁住直接返回null，
     * 6  如果lock的status为"auth"，那么首先更新lock，将status设置为“locked”，返回lock
     * 结束事务
     * @param lockKey
     * @return
     */
    @Override
    @Transactional(value = "transactionManager")
    public AuthGlobalLockVO requireAuthGlobalLock(String lockKey){
        AuthGlobalLockVO lock = authGlobalLockManager.getAuthGlobalLockByLockKey(lockKey);
        if(lock == null){
            lock = saveDefaultLock(lockKey);
        }

        if(!LOCK_STATUS_OPEN.equals(lock.getStatus())){
            bizLogger.warn(LogFormatter.format(
                    LogFormatter.LogEvent.END,
                    "auth global lock锁被占用",
                    new LogFormatter.KeyValue("lockKey", lockKey)
            ));
            return null;
        }
        lock.setStatus(LOCK_STATUS_LOCKED);
        authGlobalLockManager.updateStatus(lock);
        return lock;
    }

    @Override
    @Transactional(value = "transactionManager")
    public void releaseAuthGlobalLock(String lockKey){
        AuthGlobalLockVO lock = authGlobalLockManager.getAuthGlobalLockByLockKey(lockKey);
        if(lock == null){
            return;
        }
        lock.setStatus(LOCK_STATUS_OPEN);
        authGlobalLockManager.updateStatus(lock);
    }

    private AuthGlobalLockVO saveDefaultLock(String lockKey){
        AuthGlobalLockVO lock = new AuthGlobalLockVO();
        lock.setLockKey(lockKey);
        lock.setStatus(LOCK_STATUS_OPEN);
        authGlobalLockManager.saveOrUpdateAuthGlobalLock(lock);
        return lock;
    }
}
