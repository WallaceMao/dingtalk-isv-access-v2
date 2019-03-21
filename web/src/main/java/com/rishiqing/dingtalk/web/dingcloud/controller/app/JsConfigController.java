package com.rishiqing.dingtalk.web.dingcloud.controller.app;

import com.rishiqing.dingtalk.svc.http.HttpResult;
import com.rishiqing.dingtalk.svc.http.HttpResultCode;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAppVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.CorpAppManager;
import com.rishiqing.dingtalk.svc.util.DingTalkJsApiSingnature;
import com.rishiqing.dingtalk.svc.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:11
 */
@Controller
public class JsConfigController {
    private static final Logger bizLogger = LoggerFactory.getLogger(JsConfigController.class);

    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpAppManager corpAppManager;
    /**
     * jssdk api授权
     * @param url
     * @param corpId
     * @param appId
     * @return
     */
    @RequestMapping("/get_js_config")
    @ResponseBody
    public Map<String, Object> getJSConfig(@RequestParam(value = "url", required = false) String url,
                                           @RequestParam(value = "corpid", required = false) String corpId,
                                           @RequestParam(value = "appid", required = false) Long appId

    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/get_js_config",
                new LogFormatter.KeyValue("url", url),
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("appId", appId)
        ));
        try{
            //  检查url合法性
            url = check(url);

            CorpJSAPITicketVO jsapiTicketVO = corpManager.getCorpJSAPITicketByCorpId(corpId);
            CorpAppVO corpAppVO = corpAppManager.getCorpAppByCorpIdAndAppId(corpId, appId);
            String nonce = Utils.getRandomStr(8);
            Long timeStamp = System.currentTimeMillis();
            String sign = DingTalkJsApiSingnature.getJsApiSingnature(url, nonce, timeStamp, jsapiTicketVO.getCorpJSAPITicket());
            Map<String,Object> jsapiConfig = new HashMap<>();
            jsapiConfig.put("signature", sign);
            jsapiConfig.put("nonce", nonce);
            jsapiConfig.put("timeStamp", timeStamp);
            jsapiConfig.put("agentId", corpAppVO.getAgentId());
            jsapiConfig.put("corpId", corpId);
            jsapiConfig.put("appId", appId);
            return HttpResult.getSuccess(jsapiConfig);
        }catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/get_js_config",
                    new LogFormatter.KeyValue("url", url),
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("appId", appId)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    private String check(String url){
        try {
            url = URLDecoder.decode(url,"UTF-8");
            URL urler = new URL(url);
            StringBuilder urlBuffer = new StringBuilder();
            urlBuffer.append(urler.getProtocol());
            urlBuffer.append(":");
            if (urler.getAuthority() != null && urler.getAuthority().length() > 0) {
                urlBuffer.append("//");
                urlBuffer.append(urler.getAuthority());
            }
            if (urler.getPath() != null) {
                urlBuffer.append(urler.getPath());
            }
            if (urler.getQuery() != null) {
                urlBuffer.append('?');
                urlBuffer.append(URLDecoder.decode(urler.getQuery(), "utf-8"));
            }
            url = urlBuffer.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("url非法");
        }
        return url;
    }
}
