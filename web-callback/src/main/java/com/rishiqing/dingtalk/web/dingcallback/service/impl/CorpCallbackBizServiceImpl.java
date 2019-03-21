package com.rishiqing.dingtalk.web.dingcallback.service.impl;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CallbackRequestHelper;
import com.rishiqing.dingtalk.req.dingtalk.model.EventCallbackUrlVO;
import com.rishiqing.dingtalk.svc.model.CorpCallbackMessage;
import com.rishiqing.dingtalk.web.dingcallback.service.CorpCallbackBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2019-01-21 11:42
 */
public class CorpCallbackBizServiceImpl implements CorpCallbackBizService {
    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CallbackRequestHelper callbackRequestHelper;
    @Autowired
    @Qualifier("globalConfig")
    private Map<String, String> globalConfig;

    @Override
    public void saveOrUpdateCorpCallback(String corpId) {
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
