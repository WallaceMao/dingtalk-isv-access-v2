package com.rishiqing.dingtalk.web.dingcallback.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.svc.converter.suite.CorpSuiteAuthConverter;
import com.rishiqing.dingtalk.api.event.CorpSuiteAuthEvent;
import com.rishiqing.dingtalk.api.event.EventListener;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.service.biz.CorpBizService;
import com.rishiqing.dingtalk.api.service.biz.FailBizService;
import com.rishiqing.dingtalk.web.dingcallback.service.CorpCallbackBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:37
 */
public class CorpSuiteAuthEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpSuiteAuthEventListener.class);

    @Autowired
    private CorpBizService corpBizService;
    @Autowired
    private FailBizService failBizService;
    @Autowired
    private CorpCallbackBizService corpCallbackBizService;

    /**
     * 企业授权套件临时授权码异步逻辑
     * @param corpSuiteAuthEvent
     */
    @Subscribe
    @AllowConcurrentEvents //  event并行执行
    public void listenCorpSuiteAuthEvent(CorpSuiteAuthEvent corpSuiteAuthEvent) {
        try{
            bizLogger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "CorpSuiteAuthEventListener",
                    new LogFormatter.KeyValue("corpSuiteAuthEvent", corpSuiteAuthEvent)
            ));
            String corpId = corpSuiteAuthEvent.getCorpId();
            CorpAuthInfoVO corpAuthInfo = CorpSuiteAuthConverter.corpId2CorpAuthInfoVO(corpId);
            //  注册或者更新回调，在通讯录或者群会话发生变更时会调用此接口
            corpCallbackBizService.saveOrUpdateCorpCallback(corpId);
            corpBizService.activateCorpApp(corpAuthInfo, new Date().getTime());
        }catch (Exception e){
            //  加入失败job,失败任务会重试
            failBizService.saveCorpSuiteAuthFail(corpSuiteAuthEvent);
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "CorpSuiteAuthEventListener",
                    new LogFormatter.KeyValue("corpSuiteAuthEvent", corpSuiteAuthEvent)
            ), e);
        }
    }
}
