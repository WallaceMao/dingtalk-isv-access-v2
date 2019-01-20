package com.rishiqing.dingtalk.req.dingtalk.auth.http.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.http.HttpRequestClient;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.common.http.exception.HttpRequestException;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;

import java.util.Calendar;
import java.util.Date;

/**
 * 跟suite相关的http请求，使用Oapi的方式来请求
 * @author Wallace Mao
 * Date: 2018-11-03 17:52
 */
public class SuiteOapiRequestHelper implements SuiteRequestHelper {
    private HttpRequestClient httpRequestClient;
    private String oapiDomain;

    public String getOapiDomain() {
        return oapiDomain;
    }
    public void setOapiDomain(String oapiDomain) {
        this.oapiDomain = oapiDomain;
    }
    public HttpRequestClient getHttpRequestClient() {
        return httpRequestClient;
    }
    public void setHttpRequestClient(HttpRequestClient httpRequestClient) {
        this.httpRequestClient = httpRequestClient;
    }

    /**
     * 获取企业的jsapi ticket
     * @param suiteKey
     * @param corpId
     * @param accessToken
     * @return
     */
    public CorpJSAPITicketVO getJSTicket(String suiteKey, String corpId, String accessToken) {
        String url = getOapiDomain() + "/get_jsapi_ticket?access_token=" + accessToken;
        String str = httpRequestClient.doHttpGet(url);
        JSONObject jsonObject = JSON.parseObject(str);
        Long errCode = jsonObject.getLong("errcode");
        if (!Long.valueOf(0).equals(errCode)) {
            throw new HttpRequestException(errCode, str);
        }
        String ticket = jsonObject.getString("ticket");
        Long expires_in = jsonObject.getLong("expires_in");
        CorpJSAPITicketVO corpJSTicketVO = new CorpJSAPITicketVO();
        corpJSTicketVO.setCorpId(corpId);
        corpJSTicketVO.setSuiteKey(suiteKey);
        corpJSTicketVO.setCorpJSAPITicket(ticket);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, expires_in.intValue());
        corpJSTicketVO.setExpiredTime(calendar.getTime());
        return corpJSTicketVO;
    }

    @Override
    public CorpAuthInfoVO getCorpAuthInfo(SuiteVO suite, SuiteTicketVO suiteTicketVO, String corpId) {
        return null;
    }

    @Override
    public CorpAuthScopeInfoVO getCorpAuthScopeInfo(SuiteVO suiteVO, CorpTokenVO corpToken) {
        return null;
    }


    @Override
    public SuiteTokenVO getSuiteToken(SuiteVO suite, SuiteTicketVO suiteTicket) {
        return null;
    }

    @Override
    public CorpTokenVO getCorpToken(String suiteKey, String suiteSecret, String suiteTicket, String corpId) {
        return null;
    }

    @Override
    public CorpJSAPITicketVO getCorpJSAPITicket(CorpTokenVO corpToken) {
        return null;
    }

    @Override
    public String callCorpUser(SuiteTokenVO suiteTokenVO, String authedCorpId, String authedStaffId, String staffId) {
        return "";
    }

    @Override
    public void setCalleeList(SuiteTokenVO suiteTokenVO, String calleeList) {

    }

    @Override
    public void removeCalleeList(SuiteTokenVO suiteTokenVO, String calleeList) {

    }
}
