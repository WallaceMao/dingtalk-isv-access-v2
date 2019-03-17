package com.rishiqing.dingtalk.mgr.dingmain.manager.corp.impl;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpMessagePublishLogDO;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp.CorpMessagePublishLogDao;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpMessageManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2019-03-14 14:46
 */
public class CorpMessageManagerImpl implements CorpMessageManager {
    @Autowired
    private CorpMessagePublishLogDao corpMessagePublishLogDao;

    @Override
    public void saveOrUpdateCorpMessagePublishLog(CorpMessagePublishLogDO corpMessagePublishLogDO) {
        corpMessagePublishLogDao.saveOrUpdateCorpMessagePublishLog(corpMessagePublishLogDO);
    }

    @Override
    public CorpMessagePublishLogDO getLatestCorpMessagePublishLog(String corpId) {
        return corpMessagePublishLogDao.getCorpMessagePublishLogByCorpIdOrderByIdDescWithLimit(corpId, 1L);
    }
}
