package com.rishiqing.dingtalk.biz.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgSyncEvent;
import com.rishiqing.dingtalk.isv.api.event.EventListener;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:14
 */
public class CorpOrgSyncEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger("EVENT_LOGGER");

    @Autowired
    private FailBizService failBizService;

    @Subscribe
    @AllowConcurrentEvents //  event并行执行
    public void listenCorpOrgSyncEvent(CorpOrgSyncEvent corpOrgSyncEvent) {
        try{
            String suiteKey = corpOrgSyncEvent.getSuiteKey();
            String corpId = corpOrgSyncEvent.getCorpId();
            //TODO  执行同步到日事清后台

        }catch (Exception e){
            //  加入失败job,失败任务会重试
            failBizService.saveCorpOrgSyncFail(corpOrgSyncEvent);
            bizLogger.error("corpOrgSyncEvent: " + corpOrgSyncEvent, e);
        }
    }
}
