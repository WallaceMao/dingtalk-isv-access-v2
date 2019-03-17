package com.rishiqing.dingtalk.api.model.vo.corp;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 15:12
 */
public class CorpCountWithCreatorVO implements Serializable {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    //  钉钉平台企业id
    private String corpId;
    //  企业所属行业
    private String industry;
    //  企业名称
    private String corpName;
    //  企业logo
    private String corpLogoUrl;
    //  日事清id
    private String rsqId;
    // 团队成员数
    private Long corpCount;
    // 开通微应用的管理员相关的信息
    private Long creatorId;
    private String creatorUserId;
    private String creatorName;
    private Boolean creatorIsAdmin;
    private Boolean creatorIsBoss;
    private String creatorAvatar;
    private String creatorRsqUserId;
    private String activeLevel;

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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpLogoUrl() {
        return corpLogoUrl;
    }

    public void setCorpLogoUrl(String corpLogoUrl) {
        this.corpLogoUrl = corpLogoUrl;
    }

    public String getRsqId() {
        return rsqId;
    }

    public void setRsqId(String rsqId) {
        this.rsqId = rsqId;
    }

    public Long getCorpCount() {
        return corpCount;
    }

    public void setCorpCount(Long corpCount) {
        this.corpCount = corpCount;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Boolean getCreatorIsAdmin() {
        return creatorIsAdmin;
    }

    public void setCreatorIsAdmin(Boolean creatorIsAdmin) {
        this.creatorIsAdmin = creatorIsAdmin;
    }

    public Boolean getCreatorIsBoss() {
        return creatorIsBoss;
    }

    public void setCreatorIsBoss(Boolean creatorIsBoss) {
        this.creatorIsBoss = creatorIsBoss;
    }

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }

    public String getCreatorRsqUserId() {
        return creatorRsqUserId;
    }

    public void setCreatorRsqUserId(String creatorRsqUserId) {
        this.creatorRsqUserId = creatorRsqUserId;
    }

    public String getActiveLevel() {
        return activeLevel;
    }

    public void setActiveLevel(String activeLevel) {
        this.activeLevel = activeLevel;
    }

    @Override
    public String toString() {
        return "CorpCountWithCreatorVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", industry='" + industry + '\'' +
                ", corpName='" + corpName + '\'' +
                ", corpLogoUrl='" + corpLogoUrl + '\'' +
                ", rsqId='" + rsqId + '\'' +
                ", corpCount=" + corpCount +
                ", creatorId=" + creatorId +
                ", creatorUserId='" + creatorUserId + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", creatorIsAdmin=" + creatorIsAdmin +
                ", creatorIsBoss=" + creatorIsBoss +
                ", creatorAvatar='" + creatorAvatar + '\'' +
                ", creatorRsqUserId='" + creatorRsqUserId + '\'' +
                ", activeLevel='" + activeLevel + '\'' +
                '}';
    }
}
