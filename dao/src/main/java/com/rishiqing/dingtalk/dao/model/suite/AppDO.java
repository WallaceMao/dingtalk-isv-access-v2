package com.rishiqing.dingtalk.dao.model.suite;

import java.util.Date;

/**
 * 微应用
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */

public class AppDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  套件key
    private String suiteKey;
    //  应用id
    private Long appId;
    //  开通应用时推送的消息
    private String activeMessage;

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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getActiveMessage() {
        return activeMessage;
    }

    public void setActiveMessage(String activeMessage) {
        this.activeMessage = activeMessage;
    }

    @Override
    public String toString() {
        return "AppDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", appId=" + appId +
                ", activeMessage='" + activeMessage + '\'' +
                '}';
    }
}
