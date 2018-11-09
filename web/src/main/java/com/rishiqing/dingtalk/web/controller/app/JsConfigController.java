package com.rishiqing.dingtalk.web.controller.app;

import com.rishiqing.dingtalk.biz.http.HttpResult;
import com.rishiqing.dingtalk.biz.http.HttpResultCode;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAppVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.AppVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpAppManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.web.util.crypto.DingTalkJsApiSingnature;
import com.rishiqing.dingtalk.web.util.crypto.Utils;
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
    private static final Logger bizLogger = LoggerFactory.getLogger("CON_JS_CONFIG_LOGGER");

    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpAppManageService corpAppManageService;
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
        bizLogger.info("url: " + url, "corpId: "+ corpId, "appId: " + appId);
        try{
            //  检查url合法性
            url = check(url);

            CorpJSAPITicketVO jsapiTicketVO = corpManageService.getCorpJSAPITicketByCorpId(corpId);
            CorpAppVO corpAppVO = corpAppManageService.getCorpAppByCorpIdAndAppId(corpId, appId);
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
            bizLogger.error("getJSConfig系统错误 " + "url: " + url + "corpId: " + corpId + "appId: " + appId, e);
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
