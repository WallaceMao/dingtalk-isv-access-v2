package com.rishiqing.dingtalk.biz.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgSyncEvent;
import com.rishiqing.dingtalk.isv.api.event.EventListener;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:14
 */
public class RsqSuiteAuthPushEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger(RsqSuiteAuthPushEventListener.class);

    @Autowired
    private RsqAccountBizService rsqAccountBizService;
    @Autowired
    private FailBizService failBizService;

    @Subscribe
    @AllowConcurrentEvents //  event并行执行
    public void listenCorpOrgSyncEvent(CorpOrgSyncEvent corpOrgSyncEvent) {
        try{
            String corpId = corpOrgSyncEvent.getCorpId();
            rsqAccountBizService.pushCreateAll(corpId);
        }catch (Exception e){
            //  加入失败job,失败任务会重试
            failBizService.saveCorpOrgSyncFail(corpOrgSyncEvent);
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "RsqSuiteAuthPushEventListener",
                    new LogFormatter.KeyValue("corpOrgSyncEvent", corpOrgSyncEvent)
            ), e);
        }
    }
}
