package com.rishiqing.dingtalk.biz.http.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.biz.util.DateUtil;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public CorpAuthInfoVO getCorpAuthInfo(SuiteVO suite, SuiteTicketVO suiteTicketVO, String corpId) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_auth_info");
        OapiServiceGetAuthInfoRequest req = new OapiServiceGetAuthInfoRequest();
        req.setAuthCorpid(corpId);
        try {
            OapiServiceGetAuthInfoResponse resp = client.execute(
                    req,suite.getSuiteKey(),
                    suite.getSuiteSecret(),
                    suiteTicketVO.getSuiteTicket());
            if(resp.getErrcode() != 0L){
                throw new BizRuntimeException("getSuiteToken error: " + resp.getErrcode() + ", " + resp.getErrmsg());
            }
            return this.convertToCorpSuiteAuth(resp);

        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    @Override
    public CorpAuthScopeInfoVO getCorpAuthScopeInfo(SuiteVO suiteVO, SuiteTokenVO suiteTokenVO) {
        String accessToken = suiteTokenVO.getSuiteToken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/auth/scopes");
        OapiAuthScopesRequest request = new OapiAuthScopesRequest();
        request.setHttpMethod("GET");
        try {
            OapiAuthScopesResponse resp = client.execute(request, accessToken);
            if(resp.getErrcode() != 0L){
                throw new BizRuntimeException("getSuiteToken error: " + resp.getErrcode() + ", " + resp.getErrmsg());
            }
            return this.convertToCorpAuthScopeInfo(resp);

        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    private CorpAuthInfoVO convertToCorpSuiteAuth(OapiServiceGetAuthInfoResponse resp) {
        CorpAuthInfoVO corpAuthInfo = new CorpAuthInfoVO();
        OapiServiceGetAuthInfoResponse.AuthCorpInfo respAuthCorpInfo = resp.getAuthCorpInfo();
        CorpAuthInfoVO.AuthCorpInfo corpInfo = new CorpAuthInfoVO.AuthCorpInfo();
        corpInfo.setAuthChannel(respAuthCorpInfo.getAuthChannel());
        corpInfo.setAuthChannelType(respAuthCorpInfo.getAuthChannelType());
        corpInfo.setAuthLevel(respAuthCorpInfo.getAuthLevel());
        corpInfo.setCorpLogoUrl(respAuthCorpInfo.getCorpLogoUrl());
        corpInfo.setCorpName(respAuthCorpInfo.getCorpName());
        corpInfo.setCorpId(respAuthCorpInfo.getCorpid());
        corpInfo.setIndustry(respAuthCorpInfo.getIndustry());
        corpInfo.setInviteCode(respAuthCorpInfo.getInviteCode());
        corpInfo.setInviteUrl(respAuthCorpInfo.getInviteUrl());
        corpInfo.setAuthenticated(respAuthCorpInfo.getIsAuthenticated());
        corpInfo.setLicenseCode(respAuthCorpInfo.getLicenseCode());
        corpAuthInfo.setAuthCorpInfo(corpInfo);

        CorpAuthInfoVO.AuthInfo authInfo = new CorpAuthInfoVO.AuthInfo();
        OapiServiceGetAuthInfoResponse.AuthInfo respAuthInfo = resp.getAuthInfo();
        List<OapiServiceGetAuthInfoResponse.Agent> respAgentList = respAuthInfo.getAgent();
        List<CorpAuthInfoVO.Agent> agentList = new ArrayList<>(respAgentList.size());
        for(OapiServiceGetAuthInfoResponse.Agent respAgent : respAgentList){
            CorpAuthInfoVO.Agent agent = new CorpAuthInfoVO.Agent();
            agent.setAppId(respAgent.getAppid());
            agent.setAgentId(respAgent.getAgentid());
            agent.setLogoUrl(respAgent.getLogoUrl());
            agent.setAgentName(respAgent.getAgentName());
            agent.setAdminList(respAgent.getAdminList());
            agentList.add(agent);
        }
        authInfo.setAgent(agentList);
        corpAuthInfo.setAuthInfo(authInfo);

        OapiServiceGetAuthInfoResponse.AuthUserInfo respAuthUserInfo = resp.getAuthUserInfo();
        CorpAuthInfoVO.AuthUserInfo authUserInfo = new CorpAuthInfoVO.AuthUserInfo();
        authUserInfo.setUserId(respAuthUserInfo.getUserId());
        corpAuthInfo.setAuthUserInfo(authUserInfo);

        return corpAuthInfo;
    }

    private CorpAuthScopeInfoVO convertToCorpAuthScopeInfo(OapiAuthScopesResponse resp) {
        //  auth_scope
        CorpAuthScopeInfoVO scope = new CorpAuthScopeInfoVO();
        scope.setErrcode(resp.getErrcode());
        scope.setErrmsg(resp.getErrmsg());
        scope.setConditionField(resp.getConditionField());
        scope.setAuthUserField(resp.getAuthUserField());
        //  auth_org_scopes
        OapiAuthScopesResponse.AuthOrgScopes respScopes = resp.getAuthOrgScopes();
        CorpAuthScopeInfoVO.AuthOrgScopes orgScopes = new CorpAuthScopeInfoVO.AuthOrgScopes();
        orgScopes.setAuthedUser(respScopes.getAuthedUser());
        orgScopes.setAuthedDept(respScopes.getAuthedDept());
        scope.setAuthOrgScopes(orgScopes);
        return scope;
    }

    @Override
    public SuiteTokenVO getSuiteToken(SuiteVO suite, SuiteTicketVO suiteTicket) {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_suite_token");
        OapiServiceGetSuiteTokenRequest req = new OapiServiceGetSuiteTokenRequest();
        req.setSuiteKey(suite.getSuiteKey());
        req.setSuiteSecret(suite.getSuiteSecret());
        req.setSuiteTicket(suiteTicket.getSuiteTicket());
        try {
            OapiServiceGetSuiteTokenResponse resp = client.execute(req);
            if(resp.getErrcode() != 0L){
                throw new BizRuntimeException("getSuiteToken error: " + resp.getErrcode() + ", " + resp.getErrmsg());
            }
            SuiteTokenVO suiteTokenVO = new SuiteTokenVO();
            suiteTokenVO.setSuiteKey(suite.getSuiteKey());
            suiteTokenVO.setSuiteToken(resp.getSuiteAccessToken());
            suiteTokenVO.setExpiredTime(DateUtil.addSeconds(resp.getExpiresIn()));
            return suiteTokenVO;
        } catch (ApiException e){
            throw new BizRuntimeException(e);
        }
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
            if(resp.getErrcode() != 0L){
                throw new BizRuntimeException("getCorpToken error: " + resp.getErrcode() + ", " + resp.getErrmsg());
            }
            CorpTokenVO corpTokenVO = new CorpTokenVO();
            corpTokenVO.setSuiteKey(suite.getSuiteKey());
            corpTokenVO.setCorpId(corpId);
            corpTokenVO.setCorpToken(resp.getAccessToken());
            corpTokenVO.setExpiredTime(DateUtil.addSeconds(resp.getExpiresIn()));
            return corpTokenVO;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    @Override
    public CorpJSAPITicketVO getCorpJSAPITicket(CorpTokenVO corpToken) {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
        OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
        req.setHttpMethod("GET");
        try {
            SuiteVO suite = suiteManageService.getSuite();
            SuiteTicketVO suiteTicketVO = suiteManageService.getSuiteTicket();
            OapiGetJsapiTicketResponse resp = client.execute(
                    req,
                    corpToken.getCorpToken());
            if(resp.getErrcode() != 0L){
                throw new BizRuntimeException("getCorpJSAPITicket error: " + resp.getErrcode() + ", " + resp.getErrmsg());
            }
            CorpJSAPITicketVO corpJSAPITicketVO = new CorpJSAPITicketVO();
            corpJSAPITicketVO.setSuiteKey(suite.getSuiteKey());
            corpJSAPITicketVO.setCorpId(corpToken.getCorpId());
            corpJSAPITicketVO.setCorpJSAPITicket(resp.getTicket());
            corpJSAPITicketVO.setExpiredTime(DateUtil.addSeconds(resp.getExpiresIn()));
            return corpJSAPITicketVO;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }
}
