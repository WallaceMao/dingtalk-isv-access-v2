package com.rishiqing.dingtalk.biz.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import com.rishiqing.dingtalk.isv.api.event.CorpSuiteAuthEvent;
import com.rishiqing.dingtalk.isv.api.event.EventListener;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpBizService;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
            CorpAuthInfoVO corpAuthInfo = new CorpAuthInfoVO();
            CorpAuthInfoVO.AuthCorpInfo corpInfo = new CorpAuthInfoVO.AuthCorpInfo();
            corpInfo.setCorpId(corpId);
            corpAuthInfo.setAuthCorpInfo(corpInfo);
            corpBizService.activateCorpApp(corpAuthInfo, 0L);
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
