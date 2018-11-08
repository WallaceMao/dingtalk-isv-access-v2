package com.rishiqing.dingtalk.biz.service.util;

import com.rishiqing.dingtalk.biz.enmutype.CorpLockType;
import com.rishiqing.dingtalk.dao.model.corp.CorpLockDO;

/**
 * 公司相关的并发锁控制的service
 * @author Wallace Mao
 * Date: 2018-11-06 21:43
 */
public interface CorpLockService {

    /**
     * 请求锁
     * @param corpId
     * @param type
     * @return
     */
    public CorpLockDO requireLock(String corpId, CorpLockType type);

    /**
     * 释放锁
     * @param corpId
     * @param type
     * @return
     */
    public CorpLockDO releaseLock(String corpId, CorpLockType type);
}
