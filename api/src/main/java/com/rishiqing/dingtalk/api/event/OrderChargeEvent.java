package com.rishiqing.dingtalk.api.event;

import java.io.Serializable;

/**
 * 企业获取组织结构信息事件
 * Created by Wallace on 2017/1/3.
 */
public class OrderChargeEvent implements Serializable {
    private String suiteKey;
    private Long orderId;
    private String corpId;

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    @Override
    public String toString() {
        return "OrderChargeEvent{" +
                "suiteKey='" + suiteKey + '\'' +
                ", orderId='" + orderId + '\'' +
                ", corpId='" + corpId + '\'' +
                '}';
    }
}
