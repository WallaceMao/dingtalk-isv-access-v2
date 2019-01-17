package com.rishiqing.dingtalk.api.model.vo.front;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 13:39
 */
public class StaffPopupConfigVO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    private String suiteKey;
    //  弹窗类型
    private String popupType;
    //  弹窗显示的营销二维码地址
    private String saleQrCodeUrl;
    //  弹窗显示的销售联系方式
    private String salePhoneNumber;
    //  是否被禁用
    private Boolean isDisabled;

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

    public String getPopupType() {
        return popupType;
    }

    public void setPopupType(String popupType) {
        this.popupType = popupType;
    }

    public String getSaleQrCodeUrl() {
        return saleQrCodeUrl;
    }

    public void setSaleQrCodeUrl(String saleQrCodeUrl) {
        this.saleQrCodeUrl = saleQrCodeUrl;
    }

    public String getSalePhoneNumber() {
        return salePhoneNumber;
    }

    public void setSalePhoneNumber(String salePhoneNumber) {
        this.salePhoneNumber = salePhoneNumber;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    @Override
    public String toString() {
        return "StaffPopupConfigVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", popupType='" + popupType + '\'' +
                ", saleQrCodeUrl='" + saleQrCodeUrl + '\'' +
                ", salePhoneNumber='" + salePhoneNumber + '\'' +
                ", isDisabled=" + isDisabled +
                '}';
    }
}
