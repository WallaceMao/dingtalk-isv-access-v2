package com.rishiqing.dingtalk.biz.service.util.impl;

import com.rishiqing.dingtalk.biz.enmutype.CorpLockType;
import com.rishiqing.dingtalk.biz.service.util.CorpLockService;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpLockDao;
import com.rishiqing.dingtalk.dao.model.corp.CorpLockDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 10:58
 */
public class CorpLockServiceImpl implements CorpLockService {
    private static final Logger bizLogger = LoggerFactory.getLogger("SYS_CORP_LOCK_LOGGER");

    @Autowired
    CorpLockDao corpLockDao;
    /**
     * 请求锁，事务控制，基本逻辑如下：
     * 1  根据corpId和lockType读取lock
     * 2  如果lock不存在，则说明是首次获取，可直接获取到该锁，并将锁的下次超时时间更新为当前时间加超时阀值
     * 3  查看lock是否超时。如果超时，则表示可以获取到该锁，并将锁的下次超时时间更新为当前时间加超时阀值
     * 4  如果lock未超时，说明该锁已被占用，则获取锁失败。
     * 5  释放锁时，更新lock的超时时间为当前时间，锁可有其他请求获取。
     * @return
     */
    @Override
    @Transactional
    public CorpLockDO requireLock(String corpId, CorpLockType lockType) {
        CorpLockDO corpLock;
        String lockKey = corpId + "_" + lockType.getKey();
        //  设置超时时间默认为1分钟
        int offset = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());


        calendar.add(Calendar.MINUTE, offset);
        Date date = calendar.getTime();

        corpLock = corpLockDao.getCorpLockByLockKey(lockKey);
        //  如果不存在，则直接保存
        if(null == corpLock){
            corpLock = new CorpLockDO();
            corpLock.setLockKey(lockKey);
            corpLock.setExpire(date);
        } else {
            //  如果lock的期限已到，则重新设置期限
            if(corpLock.getExpire().compareTo(new Date()) <= 0){
                corpLock.setExpire(date);
            }else{
                bizLogger.warn("请求锁被占用", corpId, lockType);
                //  如果lock的期限未到，说明锁已被占用，则返回null，表示请求锁失败
                return null;
            }
        }
        corpLockDao.saveOrUpdateCorpLock(corpLock);
        return corpLock;
    }

    /**
     * 释放锁时，更新lock的超时时间为当前时间，锁可有其他请求获取。
     * @param corpId
     * @param lockType
     * @return
     */
    @Override
    @Transactional
    public CorpLockDO releaseLock(String corpId, CorpLockType lockType) {
        CorpLockDO corpLock;
        String lockKey = corpId + "_" + lockType.getKey();

        corpLock = corpLockDao.getCorpLockByLockKey(lockKey);
        //  如果不存在，则直接保存
        if(null == corpLock){
            bizLogger.error("释放锁异常：锁不存在", corpId, lockType);
        } else {
            corpLock.setExpire(new Date());
        }
        corpLockDao.saveOrUpdateCorpLock(corpLock);
        return corpLock;
    }
}
