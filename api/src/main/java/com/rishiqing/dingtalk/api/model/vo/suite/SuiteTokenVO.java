package com.rishiqing.dingtalk.api.model.vo.suite;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 15:19
 */
public class SuiteTokenVO implements Serializable {
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
        return "SuiteTokenVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteToken='" + suiteToken + '\'' +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
