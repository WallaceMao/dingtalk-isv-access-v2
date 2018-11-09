package com.rishiqing.dingtalk.isv.api.model.fail;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-09 19:45
 */
public class CorpSuiteAuthFailVO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  企业corpId
    private String corpId;
    //  套件key
    private String suiteKey;
    //  失败类型
    private String authFailType;
    //  失败信息
    private String failInfo;
    //  套件的推送类型
    private String suitePushType;

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

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getAuthFailType() {
        return authFailType;
    }

    public void setAuthFailType(String authFailType) {
        this.authFailType = authFailType;
    }

    public String getFailInfo() {
        return failInfo;
    }

    public void setFailInfo(String failInfo) {
        this.failInfo = failInfo;
    }

    public String getSuitePushType() {
        return suitePushType;
    }

    public void setSuitePushType(String suitePushType) {
        this.suitePushType = suitePushType;
    }

    @Override
    public String toString() {
        return "CorpSuiteAuthFailVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", authFailType='" + authFailType + '\'' +
                ", failInfo='" + failInfo + '\'' +
                ", suitePushType='" + suitePushType + '\'' +
                '}';
    }
}
