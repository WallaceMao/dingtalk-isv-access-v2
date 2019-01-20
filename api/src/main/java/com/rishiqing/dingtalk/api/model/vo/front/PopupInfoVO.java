package com.rishiqing.dingtalk.api.model.vo.front;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 13:38
 */
public class PopupInfoVO {
    private String corpId;
    private Long serviceExpire;
    private Long buyNumber;
    private Long totalNumber;
    private Boolean isAdmin;
    private String specKey;
    private Map<String, StaffPopupConfigVO> popupConfigMap;
    private Map<String, StaffPopupLogVO> muteInfoMap;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Long getServiceExpire() {
        return serviceExpire;
    }

    public void setServiceExpire(Long serviceExpire) {
        this.serviceExpire = serviceExpire;
    }

    public Long getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(Long buyNumber) {
        this.buyNumber = buyNumber;
    }

    public Long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getSpecKey() {
        return specKey;
    }

    public void setSpecKey(String specKey) {
        this.specKey = specKey;
    }

    public Map<String, StaffPopupConfigVO> getPopupConfigMap() {
        return popupConfigMap;
    }

    public void setPopupConfigMap(Map<String, StaffPopupConfigVO> popupConfigMap) {
        this.popupConfigMap = popupConfigMap;
    }

    public Map<String, StaffPopupLogVO> getMuteInfoMap() {
        return muteInfoMap;
    }

    public void setMuteInfoMap(Map<String, StaffPopupLogVO> muteInfoMap) {
        this.muteInfoMap = muteInfoMap;
    }

    @Override
    public String toString() {
        return "PopupInfoVO{" +
                "corpId='" + corpId + '\'' +
                ", serviceExpire=" + serviceExpire +
                ", buyNumber=" + buyNumber +
                ", totalNumber=" + totalNumber +
                ", isAdmin=" + isAdmin +
                ", specKey='" + specKey + '\'' +
                ", popupConfigMap=" + popupConfigMap +
                ", muteInfoMap=" + muteInfoMap +
                '}';
    }
}
