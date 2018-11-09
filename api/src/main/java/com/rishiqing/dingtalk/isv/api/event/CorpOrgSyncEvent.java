package com.rishiqing.dingtalk.isv.api.event;

import java.io.Serializable;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:33
 */
public class CorpOrgSyncEvent implements Serializable {
    private String corpId;
    private String suiteKey;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "CorpOrgSyncEvent{" +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                '}';
    }
}
