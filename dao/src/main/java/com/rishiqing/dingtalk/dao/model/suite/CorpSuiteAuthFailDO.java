package com.rishiqing.dingtalk.dao.model.suite;

import com.rishiqing.dingtalk.biz.corp.enumtype.AuthFailType;
import com.rishiqing.dingtalk.biz.corp.enumtype.SuitePushType;

import java.util.Date;

/**
 * 企业开通套件失败的记录。当企业开通失败后，在这个表里记录；系统会定时查看这个表，对失败的记录进行重试。
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class CorpSuiteAuthFailDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  企业corpId
    private String corpId;
    //  套件key
    private String suiteKey;
    //  失败类型
    private AuthFailType authFailType;
    //  失败信息
    private String failInfo;
    //  套件的推送类型
    private SuitePushType suitePushType;

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

    public AuthFailType getAuthFailType() {
        return authFailType;
    }

    public void setAuthFailType(AuthFailType authFailType) {
        this.authFailType = authFailType;
    }

    public String getFailInfo() {
        return failInfo;
    }

    public void setFailInfo(String failInfo) {
        this.failInfo = failInfo;
    }

    public SuitePushType getSuitePushType() {
        return suitePushType;
    }

    public void setSuitePushType(SuitePushType suitePushType) {
        this.suitePushType = suitePushType;
    }

    @Override
    public String toString() {
        return "CorpSuiteAuthFailDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", authFailType=" + authFailType +
                ", failInfo='" + failInfo + '\'' +
                ", suitePushType=" + suitePushType +
                '}';
    }
}
