package com.rishiqing.dingtalk.web.dingcallback.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CallbackRequestHelper;
import com.rishiqing.dingtalk.svc.converter.suite.CorpSuiteAuthConverter;
import com.rishiqing.dingtalk.svc.model.CorpCallbackMessage;
import com.rishiqing.dingtalk.api.event.CorpSuiteAuthEvent;
import com.rishiqing.dingtalk.api.event.EventListener;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.api.service.biz.CorpBizService;
import com.rishiqing.dingtalk.api.service.biz.FailBizService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.req.dingtalk.model.EventCallbackUrlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:37
 */
public class CorpSuiteAuthEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpSuiteAuthEventListener.class);

    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CallbackRequestHelper callbackRequestHelper;
    @Autowired
    private CorpBizService corpBizService;
    @Autowired
    private FailBizService failBizService;
    @Autowired
    @Qualifier("globalConfig")
    private Map<String, String> globalConfig;

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
            saveOrUpdateCorpCallback(corpId);
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

    private void saveOrUpdateCorpCallback(String corpId) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        SuiteVO suiteVO = suiteManager.getSuite();
        String accessToken = corpTokenVO.getCorpToken();
        EventCallbackUrlVO eventCallbackUrlVO = callbackRequestHelper.queryCallbackUrl(accessToken);
        String url = globalConfig.get("dingtalkCorpCallback") + suiteVO.getSuiteKey();
        if(eventCallbackUrlVO == null){
            callbackRequestHelper.registerCallbackUrl(
                    accessToken, CorpCallbackMessage.Tag.getAllTag(),
                    suiteVO.getToken(), suiteVO.getEncodingAesKey(),
                    url);
        }else{
            callbackRequestHelper.updateCallbackUrl(
                    accessToken, CorpCallbackMessage.Tag.getAllTag(),
                    suiteVO.getToken(), suiteVO.getEncodingAesKey(),
                    url);
        }
    }
}
