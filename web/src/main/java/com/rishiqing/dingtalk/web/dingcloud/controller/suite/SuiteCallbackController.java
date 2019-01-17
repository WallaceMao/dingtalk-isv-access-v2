package com.rishiqing.dingtalk.web.dingcloud.controller.suite;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.enumtype.SuitePushType;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.api.service.biz.SuiteCallbackBizService;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteCallbackConverter;
import com.rishiqing.dingtalk.svc.util.DingTalkEncryptor;
import com.rishiqing.dingtalk.svc.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 钉钉回调协议
 * @author Wallace Mao
 * Date: 2018-10-31 23:50
 */
@Controller
public class SuiteCallbackController {
    private static final Logger bizLogger = LoggerFactory.getLogger(SuiteCallbackController.class);

    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private SuiteCallbackBizService suiteCallbackBizService;

    /**
     * 在钉钉微应用中配置的suite的回调地址，用来接收钉钉推送过来的跟suite相关的消息
     * @param passSuiteKey
     * @param signature
     * @param timestamp
     * @param nonce
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/suite/callback/{suiteKey}", method = {RequestMethod.POST})
    public Map<String, String> receiveCallBack(@PathVariable("suiteKey") String passSuiteKey,
                                               @RequestParam(value = "signature", required = false) String signature,
                                               @RequestParam(value = "timestamp", required = false) String timestamp,
                                               @RequestParam(value = "nonce", required = false) String nonce,
                                               @RequestBody(required = false) JSONObject json
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/suite/callback/{suiteKey}",
                new LogFormatter.KeyValue("suiteKey", passSuiteKey),
                new LogFormatter.KeyValue("signature", signature),
                new LogFormatter.KeyValue("timestamp", timestamp),
                new LogFormatter.KeyValue("nonce", nonce),
                new LogFormatter.KeyValue("json", json)
        ));
        String successString = "success";
        SuiteVO globalSuite = suiteManager.getSuite();
        String suiteKey = globalSuite.getSuiteKey();
        try {
            //  调用钉钉的sdk进行解密操作
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(
                    globalSuite.getToken(),
                    globalSuite.getEncodingAesKey(),
                    globalSuite.getSuiteKey());
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature,timestamp,nonce,encryptMsg);
            bizLogger.info(LogFormatter.format(
                    LogFormatter.LogEvent.COMMON,
                    "/suite/callback/{suiteKey}解密之后明文消息",
                    new LogFormatter.KeyValue("suiteKey", suiteKey),
                    new LogFormatter.KeyValue("signature", signature),
                    new LogFormatter.KeyValue("timestamp", timestamp),
                    new LogFormatter.KeyValue("nonce", nonce),
                    new LogFormatter.KeyValue("json", json),
                    new LogFormatter.KeyValue("plainText", plainText)
            ));

            //  处理解密后的信息
            isvCallbackEvent(plainText) ;
            Map<String, String> encryptedMap = dingTalkEncryptor.getEncryptedMap(successString, System.currentTimeMillis(), Utils.getRandomStr(8));
            bizLogger.info(LogFormatter.format(
                    LogFormatter.LogEvent.END,
                    "/suite/callback/{suiteKey}返回值",
                    new LogFormatter.KeyValue("suiteKey", suiteKey),
                    new LogFormatter.KeyValue("signature", signature),
                    new LogFormatter.KeyValue("timestamp", timestamp),
                    new LogFormatter.KeyValue("nonce", nonce),
                    new LogFormatter.KeyValue("json", json),
                    new LogFormatter.KeyValue("encryptedMap", encryptedMap)
            ));
            return encryptedMap;
        } catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/suite/callback/{suiteKey}error",
                    new LogFormatter.KeyValue("suiteKey", suiteKey),
                    new LogFormatter.KeyValue("signature", signature),
                    new LogFormatter.KeyValue("timestamp", timestamp),
                    new LogFormatter.KeyValue("nonce", nonce),
                    new LogFormatter.KeyValue("json", json)
            ), e);
            return null;
        }
    }

    /**
     * 处理各种回调时间的TAG。这个维度的回调是和套件相关的
     * @param callbackMsg
     * @return
     */
    private void isvCallbackEvent(String callbackMsg) {
        JSONObject jsonMessage = JSONObject.parseObject(callbackMsg);
        String eventType = SuiteCallbackConverter.json2EventTypeString(jsonMessage);
        if(SuitePushType.SUITE_TICKET.getKey().equals(eventType)){
            suiteCallbackBizService.receiveSuiteTicket(
                    SuiteCallbackConverter.json2SuiteTicket(jsonMessage)
            );
        }else if(SuitePushType.TMP_AUTH_CODE.getKey().equals(eventType)){
            //  接收到临时授权码之后，执行开通操作
            suiteCallbackBizService.receiveTmpAuthCode(
                    SuiteCallbackConverter.json2CorpSuiteAuth(jsonMessage)
            );
        }else if(SuitePushType.CHANGE_AUTH.getKey().equals(eventType)){
            suiteCallbackBizService.receiveChangeAuth(
                    SuiteCallbackConverter.json2AuthCorpIdString(jsonMessage)
            );
        }else if(SuitePushType.SUITE_RELIEVE.getKey().equals(eventType)){
            suiteCallbackBizService.receiveSuiteRelieve(
                    SuiteCallbackConverter.json2AuthCorpIdString(jsonMessage)
            );
        }else if(SuitePushType.CHECK_CREATE_SUITE_URL.getKey().equals(eventType)){
            //TODO
        }else if(SuitePushType.CHECK_UPDATE_SUITE_URL.getKey().equals(eventType)){
            //TODO
        }else if(SuitePushType.CHECK_SUITE_LICENSE_CODE.getKey().equals(eventType)){
            //TODO
            //留给业务自行判断
        }else if(SuitePushType.MARKET_BUY.getKey().equals(eventType)){
            suiteCallbackBizService.receiveMarketBuy(
                    SuiteCallbackConverter.json2OrderEvent(jsonMessage)
            );
        }else{
            //当开放平台更新了新的推送类型,为了避免不认识,需要报警出来
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.END,
                    new LogFormatter.KeyValue("suite callback, event type can not handled: ", callbackMsg)
            ));
        }
    }
}
