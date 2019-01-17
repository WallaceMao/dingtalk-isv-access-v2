package com.rishiqing.dingtalk.req.dingtalk.model;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-12 14:57
 */
public class EventCallbackUrlVO extends CommonResultVO {
    private List<String> callbackTag;
    private String token;
    private String aesKey;
    private String url;

    public List<String> getCallbackTag() {
        return callbackTag;
    }

    public void setCallbackTag(List<String> callbackTag) {
        this.callbackTag = callbackTag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "EventCallbackUrlVO{" +
                "errcode=" + getErrcode() +
                ", errmsg='" + getErrmsg() + '\'' +
                ", callbackTag=" + callbackTag +
                ", token='" + token + '\'' +
                ", aesKey='" + aesKey + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
