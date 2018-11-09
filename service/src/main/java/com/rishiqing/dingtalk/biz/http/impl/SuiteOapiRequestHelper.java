package com.rishiqing.dingtalk.biz.http.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.http.HttpRequestClient;
import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.biz.exception.HttpRequestException;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 获取永久授权码
     * @param suiteKey
     * @param tmpAuthCode
     * @param suiteAccessToken
     * @return
     */
    @Override
    public CorpSuiteAuthVO getPermanentCode(String suiteKey, String tmpAuthCode, String suiteAccessToken) {
        String url = getOapiDomain() + "/service/get_permanent_code?suite_access_token=" + suiteAccessToken;
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("tmp_auth_code", tmpAuthCode);
        String str = httpRequestClient.httpPostJson(url, JSON.toJSONString(parmMap));
        JSONObject jsonObject = JSON.parseObject(str);
        Long errCode = jsonObject.getLong("errcode");
        if(Long.valueOf(40078).equals(errCode)){
            //  由于钉钉的的原因，企业授权时会同时推送多个tmp_auth_code，而tmp_auth_code使用过一次就会失效
            //  因此在接受到多个tmp_auth_code做授权时，只能通过40078（不存在的临时授权码）的错误码判断
            //  收到40078后，不能返回错误，否则钉钉还会继续发送tmp_auth_code，
            //  而应该直接返回成功！

            //  不存在的临时授权码,不再继续重试
            return null;
        }
        if (!Long.valueOf(0).equals(errCode)) {
            throw new HttpRequestException(errCode, str);
        }
        JSONObject corpObject = jsonObject.getJSONObject("auth_corp_info");
        String chPcode = StringUtils.isEmpty(jsonObject.getString("ch_permanent_code"))?"":jsonObject.getString("ch_permanent_code");
        String pCode = StringUtils.isEmpty(jsonObject.getString("permanent_code"))?"":jsonObject.getString("permanent_code");
        String corpId = corpObject.getString("corpid");
        CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
        corpSuiteAuthVO.setSuiteKey(suiteKey);
        corpSuiteAuthVO.setChPermanentCode(chPcode);
        corpSuiteAuthVO.setPermanentCode(pCode);
        corpSuiteAuthVO.setCorpId(corpId);
        return corpSuiteAuthVO;
    }

    @Override
    public SuiteTokenVO getSuiteToken(SuiteVO suite, SuiteTicketVO suiteTicket) {
        return null;
    }

    @Override
    public CorpTokenVO getCorpToken(String corpId) {
        return null;
    }

    @Override
    public CorpJSAPITicketVO getCorpJSAPITicket(CorpTokenVO corpToken) {
        return null;
    }

//    /**
//     * 获取企业的授权信息
//     * @param suiteKey
//     * @param corpId
//     * @param suiteAccessToken
//     * @return
//     */
//    public CorpAuthInfoVO getAuthInfo(String suiteKey, String corpId, String suiteAccessToken) {
//        try {
//            String url = getOapiDomain() + "/service/get_auth_info?suite_access_token=" + suiteAccessToken;
//
//            Map<String, Object> parmMap = new HashMap<String, Object>();
//            parmMap.put("suite_key", suiteKey);
//            parmMap.put("auth_corpid", corpId);
//            String sr = httpRequestClient.httpPostJson(url, JSON.toJSONString(parmMap));
//
//            JSONObject jsonObject = JSON.parseObject(sr);
//            Long errCode = jsonObject.getLong("errcode");
//            if (Long.valueOf(0).equals(errCode)) {
//                CorpAuthInfoVO corpAuthInfoVO = JSON.parseObject(sr,CorpAuthInfoVO.class);
//                return ServiceResult.success(corpAuthInfoVO);
//            }
//            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
//        } catch (Exception e) {
//            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
//                    LogFormatter.KeyValue.getNew("suiteAccessToken", suiteAccessToken)
//            ), e);
//            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(), ServiceResultCode.SYS_ERROR.getErrMsg());
//        }
}
