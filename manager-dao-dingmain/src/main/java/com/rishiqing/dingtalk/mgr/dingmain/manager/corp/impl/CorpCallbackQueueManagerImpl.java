package com.rishiqing.dingtalk.mgr.dingmain.manager.corp.impl;

import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp.CorpCallbackQueueDao;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpCallbackQueueManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-12 17:05
 */
public class CorpCallbackQueueManagerImpl implements CorpCallbackQueueManager {
    @Autowired
    private CorpCallbackQueueDao corpCallbackQueueDao;

    @Override
    public void saveCorpCallbackQueue(CorpCallbackQueueDO corpCallbackQueueDO) {
        corpCallbackQueueDao.saveCorpCallbackQueueDO(corpCallbackQueueDO);
    }

    @Override
    public void updateCorpCallbackQueue(CorpCallbackQueueDO corpCallbackQueueDO) {
        corpCallbackQueueDao.updateCorpCallbackQueueDO(corpCallbackQueueDO);
    }

    @Override
    public List<CorpCallbackQueueDO> listCorpCallbackQueueByStatusOrderByTimestampWithLimit(Long status, Long limit) {
        return corpCallbackQueueDao.listCorpCallbackQueueByStatusOrderByTimestampWithLimit(
                status, limit);
    }
}
