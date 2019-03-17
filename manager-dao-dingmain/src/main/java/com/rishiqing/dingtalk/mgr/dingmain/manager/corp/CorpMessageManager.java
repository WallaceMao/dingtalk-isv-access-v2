package com.rishiqing.dingtalk.mgr.dingmain.manager.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpMessagePublishLogDO;

/**
 * @author Wallace Mao
 * Date: 2019-03-14 14:45
 */
public interface CorpMessageManager {
    void saveOrUpdateCorpMessagePublishLog(CorpMessagePublishLogDO suiteMessagePublishLogDO);

    CorpMessagePublishLogDO getLatestCorpMessagePublishLog(String corpId);
}
