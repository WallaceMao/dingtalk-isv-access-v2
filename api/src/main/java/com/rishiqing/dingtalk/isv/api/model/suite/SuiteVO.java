package com.rishiqing.dingtalk.isv.api.model.suite;

import java.util.Date;

/**
 * 套件VO
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class SuiteVO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  套件名字
    private String suiteName;
    //  suiteKey
    private String suiteKey;
    //  suite的唯一secret，与key对应
    private String suiteSecret;
    //  回调信息加解密参数
    private String encodingAesKey;
    //  已填写用于生成签名和校验毁掉请求的合法性
    private String token;
    //  回调地址
    private String eventReceiveUrl;

    //  日事清调用的名称
    private String rsqAppName;
    //  日事清调用的token
    private String rsqAppToken;
    //  新版本钉钉微应用中需要用到的suiteId
    private String suiteId;

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

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getSuiteSecret() {
        return suiteSecret;
    }

    public void setSuiteSecret(String suiteSecret) {
        this.suiteSecret = suiteSecret;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEventReceiveUrl() {
        return eventReceiveUrl;
    }

    public void setEventReceiveUrl(String eventReceiveUrl) {
        this.eventReceiveUrl = eventReceiveUrl;
    }

    public String getRsqAppName() {
        return rsqAppName;
    }

    public void setRsqAppName(String rsqAppName) {
        this.rsqAppName = rsqAppName;
    }

    public String getRsqAppToken() {
        return rsqAppToken;
    }

    public void setRsqAppToken(String rsqAppToken) {
        this.rsqAppToken = rsqAppToken;
    }

    public String getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
    }

    @Override
    public String toString() {
        return "SuiteVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteName='" + suiteName + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteSecret='" + suiteSecret + '\'' +
                ", encodingAesKey='" + encodingAesKey + '\'' +
                ", token='" + token + '\'' +
                ", eventReceiveUrl='" + eventReceiveUrl + '\'' +
                ", rsqAppName='" + rsqAppName + '\'' +
                ", rsqAppToken='" + rsqAppToken + '\'' +
                ", suiteId='" + suiteId + '\'' +
                '}';
    }
}
