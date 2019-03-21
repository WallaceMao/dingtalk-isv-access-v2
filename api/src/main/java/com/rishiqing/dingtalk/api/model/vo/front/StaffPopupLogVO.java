package com.rishiqing.dingtalk.api.model.vo.front;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 13:39
 */
public class StaffPopupLogVO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  suiteKey
    private String suiteKey;
    //  公司id
    private String corpId;
    //  用户id
    private String userId;
    //  弹窗类型
    private String popupType;
    //  弹窗静默期期限，在期限内，将不会弹窗
    private Long popupMuteExpire;

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

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPopupType() {
        return popupType;
    }

    public void setPopupType(String popupType) {
        this.popupType = popupType;
    }

    public Long getPopupMuteExpire() {
        return popupMuteExpire;
    }

    public void setPopupMuteExpire(Long popupMuteExpire) {
        this.popupMuteExpire = popupMuteExpire;
    }

    @Override
    public String toString() {
        return "StaffPopupLogVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                ", userId='" + userId + '\'' +
                ", popupType='" + popupType + '\'' +
                ", popupMuteExpire=" + popupMuteExpire +
                '}';
    }
}
