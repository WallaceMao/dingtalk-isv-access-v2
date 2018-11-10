package com.rishiqing.dingtalk.biz.converter.suite;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.isv.api.enumtype.SyncActionType;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthScopeInfoVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 0:08
 */
public class SuiteDbCheckConverter {
    @Deprecated
    public static SyncActionType json2SuiteSyncActionType(JSONObject json){
        return SyncActionType.getSuiteSyncActionType(json.getString("syncAction"));
    }
    public static String json2SyncActionString(JSONObject json){
        return json.getString("syncAction");
    }

    public static SuiteTicketVO json2SuiteTicket(JSONObject json){
        String receiveSuiteTicket = json.getString("suiteTicket");
        SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
        suiteTicketVO.setSuiteTicket(receiveSuiteTicket);
        return suiteTicketVO;
    }

    /**
     * {
     "syncAction": "org_suite_auth",
     "auth_corp_info": {
     "auth_channel": "",
     "auth_channel_type": "",
     "auth_level": 1,
     "corp_logo_url": "http://url.com",
     "corp_name": "浩倡测试企业申请认证",
     "corpid": "dingxxxxxxxxxxxxxx",
     "industry": "信息技术咨询",
     "invite_code": "000000",
     "invite_url": "http://url.com",
     "is_authenticated": true,
     "license_code": "xxx"
     },
     "auth_info": {
     "agent": [{
     "admin_list": ["aaaa", "bbbb"],
     "agent_name": "Demo",
     "agentid": 16000,
     "appid": 1234,
     "logo_url": "http://url.png"
     }]
     },
     "auth_user_info": {
     "userId": "xxxx"
     },
     "auth_scope": {
     "errcode": 0,
     "condition_field": [],
     "auth_user_field": ["dingId", "position", "jobnumber", "avatar", "deviceId"],
     "auth_org_scopes": {
     "authed_user": [],
     "authed_dept": [1]
     },
     "errmsg": "ok"
     },
     "permanent_code": "xxxxxxxxxxxxx",
     "ch_permanent_code": "xxxxxxxxxxxxxx"
     }
     * @param json
     * @return
     */
    public static CorpAuthInfoVO json2CorpSuiteAuthInfo(JSONObject json){
        CorpAuthInfoVO corpAuthInfo = new CorpAuthInfoVO();
        //  auth_corp_info
        if(json.containsKey("auth_corp_info")){
            JSONObject jsonCorp = json.getJSONObject("auth_corp_info");
            CorpAuthInfoVO.AuthCorpInfo corpInfo = new CorpAuthInfoVO.AuthCorpInfo();
            corpInfo.setAuthChannel(jsonCorp.getString("auth_channel"));
            corpInfo.setAuthChannelType(jsonCorp.getString("auth_channel_type"));
            corpInfo.setAuthLevel(jsonCorp.getInteger("auth_level"));
            corpInfo.setCorpLogoUrl(jsonCorp.getString("corp_logo_url"));
            corpInfo.setCorpName(jsonCorp.getString("corp_name"));
            corpInfo.setCorpId(jsonCorp.getString("corpid"));
            corpInfo.setIndustry(jsonCorp.getString("industry"));
            corpInfo.setInviteCode(jsonCorp.getString("invite_code"));
            corpInfo.setInviteUrl(jsonCorp.getString("invite_url"));
            corpInfo.setAuthenticated(jsonCorp.getBoolean("is_authenticated"));
            corpInfo.setLicenseCode(jsonCorp.getString("license_code"));
            corpAuthInfo.setAuthCorpInfo(corpInfo);
        }
        //  auth_info
        if(json.containsKey("auth_info")){
            JSONObject jsonAuth = json.getJSONObject("auth_info");
            CorpAuthInfoVO.AuthInfo authInfo = new CorpAuthInfoVO.AuthInfo();
            JSONArray jsonAgentArray = jsonAuth.getJSONArray("agent");
            List<CorpAuthInfoVO.Agent> agentList = new ArrayList<>(jsonAgentArray.size());
            for(int i = 0; i < jsonAgentArray.size(); i++){
                JSONObject jsonAgent = jsonAgentArray.getJSONObject(i);
                CorpAuthInfoVO.Agent agent = new CorpAuthInfoVO.Agent();
                // admin_list
                JSONArray jsonAdminArray = jsonAgent.getJSONArray("admin_list");
                List<String> adminList = new ArrayList<>(jsonAdminArray.size());
                for(int j = 0; j < jsonAdminArray.size(); j++){
                    adminList.add(jsonAdminArray.getString(j));
                }
                agent.setAdminList(adminList);

                agent.setAgentName(jsonAgent.getString("agent_name"));
                agent.setAgentId(jsonAgent.getLong("agentid"));
                agent.setAppId(jsonAgent.getLong("appid"));
                agent.setLogoUrl(jsonAgent.getString("logo_url"));
                agentList.add(agent);
            }
            authInfo.setAgent(agentList);
            corpAuthInfo.setAuthInfo(authInfo);
        }
        //  auth_user_info
        if(json.containsKey("auth_user_info")){
            JSONObject jsonUser = json.getJSONObject("auth_user_info");
            CorpAuthInfoVO.AuthUserInfo userInfo = new CorpAuthInfoVO.AuthUserInfo();
            userInfo.setUserId(jsonUser.getString("userId"));
            corpAuthInfo.setAuthUserInfo(userInfo);
        }
        //  auth_scope
        if(json.containsKey("auth_scope")){
            JSONObject jsonScope = json.getJSONObject("auth_scope");
            CorpAuthScopeInfoVO scope = new CorpAuthScopeInfoVO();
            scope.setErrcode(jsonScope.getLong("errcode"));
            scope.setErrmsg(jsonScope.getString("errmsg"));
            //  condition_field
            JSONArray jsonConditionFieldArray = jsonScope.getJSONArray("condition_field");
            List<String> conditionFieldList = new ArrayList<>(jsonConditionFieldArray.size());
            for(int i = 0; i < jsonConditionFieldArray.size(); i++){
                conditionFieldList.add(jsonConditionFieldArray.getString(i));
            }
            scope.setConditionField(conditionFieldList);
            //  auth_user_field
            JSONArray jsonAuthUserFieldArray = jsonScope.getJSONArray("auth_user_field");
            List<String> authUserFieldList = new ArrayList<>(jsonAuthUserFieldArray.size());
            for(int i = 0; i < jsonAuthUserFieldArray.size(); i++){
                authUserFieldList.add(jsonAuthUserFieldArray.getString(i));
            }
            scope.setAuthUserField(authUserFieldList);
            //  auth_org_scopes
            JSONObject jsonOrgScopes = jsonScope.getJSONObject("auth_org_scopes");
            CorpAuthScopeInfoVO.AuthOrgScopes orgScopes = new CorpAuthScopeInfoVO.AuthOrgScopes();
            JSONArray authedUserArray = jsonOrgScopes.getJSONArray("authed_user");
            List<String> authedUserList = new ArrayList<>(authedUserArray.size());
            for(int i = 0; i < authedUserArray.size(); i++){
                authedUserList.add(authedUserArray.getString(i));
            }
            orgScopes.setAuthedUser(authedUserList);
            JSONArray authedDeptArray = jsonOrgScopes.getJSONArray("authed_dept");
            List<Long> authedDeptList = new ArrayList<>(authedDeptArray.size());
            for(int i = 0; i< authedDeptArray.size(); i++){
                authedDeptList.add(authedDeptArray.getLong(i));
            }
            orgScopes.setAuthedDept(authedDeptList);
            scope.setAuthOrgScopes(orgScopes);

            corpAuthInfo.setAuthScope(scope);
        }

        //  permanent_code
        if(json.containsKey("permanent_code")){
            corpAuthInfo.setPermanentCode(json.getString("permanent_code"));
        }
        if(json.containsKey("ch_permanent_code")){
            corpAuthInfo.setChPermanentCode("ch_permanent_code");
        }

        return corpAuthInfo;
    }

    public static void main(String[] args) {
        String jsonString = "{\"syncAction\":\"org_suite_auth\",\"auth_corp_info\":{\"auth_channel\":\"\",\"auth_channel_type\":\"\",\"auth_level\":1,\"corp_logo_url\":\"http://url.com\",\"corp_name\":\"浩倡测试企业申请认证\",\"corpid\":\"dingxxxxxxxxxxxxxx\",\"industry\":\"信息技术咨询\",\"invite_code\":\"000000\",\"invite_url\":\"http://url.com\",\"is_authenticated\":true,\"license_code\":\"xxx\"},\"auth_info\":{\"agent\":[{\"admin_list\":[\"aaaa\",\"bbbb\"],\"agent_name\":\"Demo\",\"agentid\":16000,\"appid\":1234,\"logo_url\":\"http://url.png\"}]},\"auth_user_info\":{\"userId\":\"xxxx\"},\"auth_scope\":{\"errcode\":0,\"condition_field\":[],\"auth_user_field\":[\"dingId\",\"position\",\"jobnumber\",\"avatar\",\"deviceId\"],\"auth_org_scopes\":{\"authed_user\":[],\"authed_dept\":[1]},\"errmsg\":\"ok\"},\"permanent_code\":\"xxxxxxxxxxxxx\",\"ch_permanent_code\":\"xxxxxxxxxxxxxx\"}";
        JSONObject json = JSONObject.parseObject(jsonString);
        CorpAuthInfoVO corpAuthInfo = SuiteDbCheckConverter.json2CorpSuiteAuthInfo(json);
        System.out.println("--------------------");
    }
}
