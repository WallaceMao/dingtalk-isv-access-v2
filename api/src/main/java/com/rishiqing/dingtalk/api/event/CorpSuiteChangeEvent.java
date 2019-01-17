package com.rishiqing.dingtalk.api.event;

import java.io.Serializable;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:33
 */
public class CorpSuiteChangeEvent implements Serializable {
    private String suiteToken;
    private String corpId;
    private String suiteKey;
    private String permanentCode;
    private String chPermanentCode;
    private String info;

    public String getSuiteToken() {
        return suiteToken;
    }

    public void setSuiteToken(String suiteToken) {
        this.suiteToken = suiteToken;
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

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode;
    }

    public String getChPermanentCode() {
        return chPermanentCode;
    }

    public void setChPermanentCode(String chPermanentCode) {
        this.chPermanentCode = chPermanentCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "CorpSuiteChangeEvent{" +
                "suiteToken='" + suiteToken + '\'' +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", permanentCode='" + permanentCode + '\'' +
                ", chPermanentCode='" + chPermanentCode + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
