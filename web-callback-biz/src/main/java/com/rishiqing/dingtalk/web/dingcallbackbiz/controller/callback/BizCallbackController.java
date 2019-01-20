package com.rishiqing.dingtalk.web.dingcallbackbiz.controller.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.svc.converter.corp.CorpCallbackConverter;
import com.rishiqing.dingtalk.svc.util.DingTalkEncryptor;
import com.rishiqing.dingtalk.svc.util.Utils;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpCallbackQueueManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2019-01-12 15:44
 */
@Controller
@RequestMapping("/bizCallback")
public class BizCallbackController {
    private final Logger logger = LoggerFactory.getLogger(BizCallbackController.class);

    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CorpCallbackQueueManager corpCallbackQueueManager;

    /**
     * 企业接受接受回调事件。这个维度的回调都是和授权企业相关的
     *
     * @param passSuiteKey
     * @param signature
     * @param timestamp
     * @param nonce
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{suiteKey}", method = {RequestMethod.POST})
    public Map<String, String> corpSuiteCallBack(@PathVariable("suiteKey") String passSuiteKey,
                                                 @RequestParam(value = "signature", required = false) String signature,
                                                 @RequestParam(value = "timestamp", required = false) String timestamp,
                                                 @RequestParam(value = "nonce", required = false) String nonce,
                                                 @RequestBody(required = false) JSONObject json
    ) {
        SuiteVO globalSuite = suiteManager.getSuite();
        String suiteKey = globalSuite.getSuiteKey();
        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(
                    globalSuite.getToken(), globalSuite.getEncodingAesKey(), globalSuite.getSuiteKey());
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
            logger.info(LogFormatter.format(LogFormatter.LogEvent.END,
                    LogFormatter.getKV("suiteKey", suiteKey),
                    LogFormatter.getKV("signature", signature),
                    LogFormatter.getKV("timestamp", timestamp),
                    LogFormatter.getKV("nonce", nonce),
                    LogFormatter.getKV("json", json),
                    LogFormatter.getKV("plainText", plainText)
            ));
            JSONObject jsonObject = JSON.parseObject(plainText);
            String eventType = jsonObject.getString("EventType");
            if ("check_url".equals(eventType)) {
                Map<String, String> encryptedMap = dingTalkEncryptor.getEncryptedMap("success", System.currentTimeMillis(), Utils.getRandomStr(8));
                return encryptedMap;
            }
            //将消息先记录到MySQL数据库
            corpCallbackQueueManager.saveCorpCallbackQueue(
                    CorpCallbackConverter.json2CorpCallbackQueueDO(suiteKey, jsonObject));
            //实现Queue之后才能使用jmsTemplate，否则会发生线程堵塞
//                jmsTemplate.send(suiteCallBackQueue, new SuiteCallBackMessage(jsonObject, tag));

            Map<String, String> encryptedMap = dingTalkEncryptor.getEncryptedMap("success", System.currentTimeMillis(), Utils.getRandomStr(8));
            return encryptedMap;
        } catch (Exception e) {
            logger.error(LogFormatter.format(LogFormatter.LogEvent.END,
                    LogFormatter.getKV("suiteKey", suiteKey),
                    LogFormatter.getKV("signature", signature),
                    LogFormatter.getKV("timestamp", timestamp),
                    LogFormatter.getKV("nonce", nonce),
                    LogFormatter.getKV("json", json)
            ), e);
            return null;
        }
    }
}
