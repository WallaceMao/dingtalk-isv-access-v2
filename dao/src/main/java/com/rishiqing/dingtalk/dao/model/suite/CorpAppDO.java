package com.rishiqing.dingtalk.dao.model.suite;

import java.util.Date;

/**
 * 企业使用微应用信息
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class CorpAppDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  钉钉平台企业id
    private String corpId;
    //  微应用原始id，这个appId与企业无关
    private Long appId;
    //  企业使用微应用的id，同一个微应用在不同的企业中agentId不同
    private Long agentId;
    //  微应用名称
    private String agentName;
    //  微应用logo
    private String logoUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


    @Override
    public String toString() {
        return "CorpAppDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", appId=" + appId +
                ", agentId=" + agentId +
                ", agentName='" + agentName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}
