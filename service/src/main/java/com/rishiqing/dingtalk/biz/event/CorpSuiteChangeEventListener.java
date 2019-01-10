package com.rishiqing.dingtalk.biz.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.biz.converter.suite.CorpSuiteAuthConverter;
import com.rishiqing.dingtalk.isv.api.event.CorpSuiteChangeEvent;
import com.rishiqing.dingtalk.isv.api.event.EventListener;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpBizService;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:37
 */
public class CorpSuiteChangeEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpSuiteChangeEventListener.class);

    @Autowired
    private CorpBizService corpBizService;
    @Autowired
    private FailBizService failBizService;

    /**
     * 企业变更授权套件异步逻辑
     * @param corpSuiteChangeEvent
     */
    @Subscribe
    @AllowConcurrentEvents //  event并行执行
    public void listenCorpSuiteChangeEvent(CorpSuiteChangeEvent corpSuiteChangeEvent) {
        try{
            bizLogger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "CorpSuiteChangeEventListener",
                    new LogFormatter.KeyValue("corpSuiteChangeEvent", corpSuiteChangeEvent)
            ));
            String corpId = corpSuiteChangeEvent.getCorpId();
            CorpAuthInfoVO corpAuthInfo = CorpSuiteAuthConverter.corpId2CorpAuthInfoVO(corpId);
            corpBizService.changeCorpApp(corpAuthInfo, new Date().getTime());
        }catch (Exception e){
            //  加入失败job,失败任务会重试
            failBizService.saveCorpSuiteChangeFail(corpSuiteChangeEvent);
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "CorpSuiteChangeEventListener",
                    new LogFormatter.KeyValue("corpSuiteChangeEvent", corpSuiteChangeEvent)
            ), e);
        }
    }
}
