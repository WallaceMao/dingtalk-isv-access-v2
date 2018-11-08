package com.rishiqing.dingtalk.biz.http.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiServiceGetCorpTokenRequest;
import com.dingtalk.api.response.OapiServiceGetCorpTokenResponse;
import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 使用淘宝开放平台（TOP）接口来做http实现http请求
 * @author Wallace Mao
 * Date: 2018-11-06 13:59
 */
public class SuiteTopRequestHelper implements SuiteRequestHelper {
    @Autowired
    private SuiteManageService suiteManageService;
    /**
     * 新版的钉钉授权逻辑中，不再需要获取永久授权码，因此这个方法直接返回空
     * @param suiteKey
     * @param tmpAuthCode
     * @param suiteAccessToken
     * @return
     */
    @Override
    public CorpSuiteAuthVO getPermanentCode(String suiteKey, String tmpAuthCode, String suiteAccessToken) {
        return null;
    }

    public CorpAuthInfoVO getCorpAuthInfo(){
        return null;
    }

    @Override
    public CorpTokenVO getCorpToken(String corpId){
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_corp_token");
        OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
        req.setAuthCorpid(corpId);
        try {
            SuiteVO suite = suiteManageService.getSuite();
            SuiteTicketVO suiteTicketVO = suiteManageService.getSuiteTicket();
            OapiServiceGetCorpTokenResponse resp = client.execute(
                    req,
                    suite.getSuiteKey(),
                    suite.getSuiteSecret(),
                    suiteTicketVO.getSuiteTicket());
            CorpTokenVO corpTokenVO = new CorpTokenVO();
            corpTokenVO.setSuiteKey(suite.getSuiteKey());
            corpTokenVO.setCorpId(corpId);
            corpTokenVO.setCorpToken(resp.getAccessToken());
            corpTokenVO.setExpiredTime(new Date(resp.getExpiresIn()));
            return corpTokenVO;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }

    }
    @Override
    public CorpJSAPITicketVO getJSTicket(String suiteKey, String corpId, String accessToken) {
        return null;
    }
}
