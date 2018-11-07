package com.rishiqing.dingtalk.isv.api.model.corp;

import java.util.List;

/**
 * 获取企业基本信息的vo对象。注意，由于钉钉返回值中驼峰命名法和下划线命名法混用，这里统一都是用驼峰命名法
 * @author Wallace Mao
 * Date: 2018-11-06 15:25
 */
public class CorpAuthInfoVO {
    private String syncAction;
    private String permanentCode;
    private String chPermanentCode;
    private CorpAuthInfoVO.AuthCorpInfo authCorpInfo;
    private CorpAuthInfoVO.AuthUserInfo authUserInfo;
    private CorpAuthInfoVO.AuthInfo authInfo;
    private CorpAuthScopeInfoVO authScope;

    public static class AuthCorpInfo {
        private String authChannel;
        private String authChannelType;
        private Integer authLevel;
        private String corpLogoUrl;
        private String corpName;
        private String corpId;
        private String industry;
        private String inviteCode;
        private String inviteUrl;
        private Boolean isAuthenticated;
        private String licenseCode;

        public String getAuthChannel() {
            return authChannel;
        }

        public void setAuthChannel(String authChannel) {
            this.authChannel = authChannel;
        }

        public String getAuthChannelType() {
            return authChannelType;
        }

        public void setAuthChannelType(String authChannelType) {
            this.authChannelType = authChannelType;
        }

        public Integer getAuthLevel() {
            return authLevel;
        }

        public void setAuthLevel(Integer authLevel) {
            this.authLevel = authLevel;
        }

        public String getCorpLogoUrl() {
            return corpLogoUrl;
        }

        public void setCorpLogoUrl(String corpLogoUrl) {
            this.corpLogoUrl = corpLogoUrl;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public String getCorpId() {
            return corpId;
        }

        public void setCorpId(String corpId) {
            this.corpId = corpId;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getInviteUrl() {
            return inviteUrl;
        }

        public void setInviteUrl(String inviteUrl) {
            this.inviteUrl = inviteUrl;
        }

        public Boolean getAuthenticated() {
            return isAuthenticated;
        }

        public void setAuthenticated(Boolean authenticated) {
            isAuthenticated = authenticated;
        }

        public String getLicenseCode() {
            return licenseCode;
        }

        public void setLicenseCode(String licenseCode) {
            this.licenseCode = licenseCode;
        }
    }

    public static class AuthUserInfo {
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class AuthInfo {
        private List<CorpAuthInfoVO.Agent> agent;

        public List<Agent> getAgent() {
            return agent;
        }

        public void setAgent(List<Agent> agent) {
            this.agent = agent;
        }
    }

    public static class Agent {
        private List<String> adminList;
        private String agentName;
        private Long agentId;
        private Long appId;
        private String logoUrl;

        public List<String> getAdminList() {
            return adminList;
        }

        public void setAdminList(List<String> adminList) {
            this.adminList = adminList;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public Long getAgentId() {
            return agentId;
        }

        public void setAgentId(Long agentId) {
            this.agentId = agentId;
        }

        public Long getAppId() {
            return appId;
        }

        public void setAppId(Long appId) {
            this.appId = appId;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }
    }

    public String getSyncAction() {
        return syncAction;
    }

    public void setSyncAction(String syncAction) {
        this.syncAction = syncAction;
    }

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode;
    }

    public String getChPermanentCode() {
        return chPermanentCode;
    }

    public void setChPermanentCode(String chPermanentCode) {
        this.chPermanentCode = chPermanentCode;
    }

    public AuthCorpInfo getAuthCorpInfo() {
        return authCorpInfo;
    }

    public void setAuthCorpInfo(AuthCorpInfo authCorpInfo) {
        this.authCorpInfo = authCorpInfo;
    }

    public AuthUserInfo getAuthUserInfo() {
        return authUserInfo;
    }

    public void setAuthUserInfo(AuthUserInfo authUserInfo) {
        this.authUserInfo = authUserInfo;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public CorpAuthScopeInfoVO getAuthScope() {
        return authScope;
    }

    public void setAuthScope(CorpAuthScopeInfoVO authScope) {
        this.authScope = authScope;
    }
}
