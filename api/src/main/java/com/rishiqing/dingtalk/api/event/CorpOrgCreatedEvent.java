package com.rishiqing.dingtalk.api.event;

import java.io.Serializable;

/**
 * 当用户组织结构创建成功时，抛出的事件
 * @author Wallace Mao
 * Date: 2018-11-03 20:33
 */
public class CorpOrgCreatedEvent implements Serializable {
    private String corpId;
    private String suiteKey;
    private Long scopeVersion;
    private String info;

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

    public Long getScopeVersion() {
        return scopeVersion;
    }

    public void setScopeVersion(Long scopeVersion) {
        this.scopeVersion = scopeVersion;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "CorpOrgCreatedEvent{" +
                "corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", scopeVersion=" + scopeVersion +
                ", info='" + info + '\'' +
                '}';
    }
}
