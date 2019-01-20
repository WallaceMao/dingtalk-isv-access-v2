package com.rishiqing.dingtalk.api.model.domain.suite;

import java.util.Date;

/**
 * 套件访问开放平台的accessToken
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class SuiteTokenDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  套件key
    private String suiteKey;
    //  套件token
    private String suiteToken;
    //  套件token
    private Date expiredTime;

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

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getSuiteToken() {
        return suiteToken;
    }

    public void setSuiteToken(String suiteToken) {
        this.suiteToken = suiteToken;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "SuiteTokenDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteToken='" + suiteToken + '\'' +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
