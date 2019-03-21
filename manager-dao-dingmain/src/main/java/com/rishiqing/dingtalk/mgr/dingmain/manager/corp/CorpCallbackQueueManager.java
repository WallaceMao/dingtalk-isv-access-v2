package com.rishiqing.dingtalk.mgr.dingmain.manager.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-12 17:05
 */
public interface CorpCallbackQueueManager {
    void saveCorpCallbackQueue(CorpCallbackQueueDO corpCallbackQueueDO);

    void updateCorpCallbackQueue(CorpCallbackQueueDO corpCallbackQueueDO);

    List<CorpCallbackQueueDO> listCorpCallbackQueueByStatusOrderByTimestampWithLimit(
            Long status, Long limit);
}
