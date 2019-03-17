package com.rishiqing.dingtalk.req.dingtalk.auth.http.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.rishiqing.common.base.DateUtil;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestCommonHelper;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpJSAPITicketVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTokenVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用淘宝开放平台（TOP）接口来做http实现http请求
 * @author Wallace Mao
 * Date: 2018-11-06 13:59
 */
public class SuiteTopRequestHelper implements SuiteRequestHelper {
    @Autowired
    private SuiteRequestCommonHelper suiteRequestCommonHelper;

    @Override
    public CorpAuthInfoVO getCorpAuthInfo(SuiteVO suite, SuiteTicketVO suiteTicketVO, String corpId) {
        OapiServiceGetAuthInfoResponse resp = suiteRequestCommonHelper.getCorpAuthInfo(
                suite.getSuiteKey(),
                suite.getSuiteSecret(),
                suiteTicketVO.getSuiteTicket(),
                corpId);
        if(resp.getErrcode() != 0L){
            throw new BizRuntimeException("getSuiteToken error: " + resp.getErrcode() + ", " + resp.getErrmsg());
        }
        return this.convertToCorpSuiteAuth(resp);
    }

    @Override
    public CorpAuthScopeInfoVO getCorpAuthScopeInfo(SuiteVO suiteVO, CorpTokenVO corpToken) {
        String accessToken = corpToken.getCorpToken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/auth/scopes");
        OapiAuthScopesRequest request = new OapiAuthScopesRequest();
        request.setHttpMethod("GET");
        try {
            OapiAuthScopesResponse resp = client.execute(request, accessToken);
            if(resp.getErrcode() != 0L){
                throw new BizRuntimeException("getCorpAuthScopeInfo error: " + resp.getErrcode() + ", " + resp.getErrmsg());
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
    public CorpTokenVO getCorpToken(String suiteKey, String suiteSecret, String suiteTicket, String corpId){
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_corp_token");
        OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
        req.setAuthCorpid(corpId);
        try {
            OapiServiceGetCorpTokenResponse resp = client.execute(
                    req, suiteKey, suiteSecret, suiteTicket);
            if(resp.getErrcode() != 0L){
                throw new BizRuntimeException("getCorpToken error: " + resp.getErrcode() + ", " + resp.getErrmsg());
            }
            CorpTokenVO corpTokenVO = new CorpTokenVO();
            corpTokenVO.setSuiteKey(suiteKey);
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
            OapiGetJsapiTicketResponse resp = client.execute(
                    req,
                    corpToken.getCorpToken());
            if(resp.getErrcode() != 0L){
                throw new BizRuntimeException("getCorpJSAPITicket error: " + resp.getErrcode() + ", " + resp.getErrmsg());
            }
            CorpJSAPITicketVO corpJSAPITicketVO = new CorpJSAPITicketVO();
            corpJSAPITicketVO.setSuiteKey(corpToken.getSuiteKey());
            corpJSAPITicketVO.setCorpId(corpToken.getCorpId());
            corpJSAPITicketVO.setCorpJSAPITicket(resp.getTicket());
            corpJSAPITicketVO.setExpiredTime(DateUtil.addSeconds(resp.getExpiresIn()));
            return corpJSAPITicketVO;
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    @Override
    public String callCorpUser(SuiteTokenVO suiteTokenVO, String authedCorpId, String authedStaffId, String staffId) {
        String suiteAccessToken = suiteTokenVO.getSuiteToken();
        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        IsvCallCalluserRequest req = new IsvCallCalluserRequest();
        req.setStaffId(staffId);
        req.setAuthedCorpId(authedCorpId);
        req.setAuthedStaffId(authedStaffId);
        IsvCallCalluserResponse rsp = null;
        try {
            rsp = client.execute(req, suiteAccessToken);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    @Override
    public void setCalleeList(SuiteTokenVO suiteTokenVO, String calleeList) {
        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        IsvCallSetuserlistRequest req = new IsvCallSetuserlistRequest();
        req.setStaffIdList(calleeList);
        IsvCallSetuserlistResponse rsp = null;
        try {
            rsp = client.execute(req, suiteTokenVO.getSuiteToken());
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }

    @Override
    public void removeCalleeList(SuiteTokenVO suiteTokenVO, String calleeList) {
        DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
        IsvCallRemoveuserlistRequest req = new IsvCallRemoveuserlistRequest();
        req.setStaffIdList("xxx,xxx");
        IsvCallRemoveuserlistResponse rsp = null;
        try {
            rsp = client.execute(req, suiteTokenVO.getSuiteToken());
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            throw new BizRuntimeException(e);
        }
    }
}
