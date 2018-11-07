package com.rishiqing.dingtalk.biz.http.impl;

import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;

/**
 * 使用淘宝开放平台（TOP）接口来做http实现http请求
 * @author Wallace Mao
 * Date: 2018-11-06 13:59
 */
public class SuiteTopRequestHelper implements SuiteRequestHelper {
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
    public CorpJSAPITicketVO getJSTicket(String suiteKey, String corpId, String accessToken) {
        return null;
    }
}
