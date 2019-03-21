package com.rishiqing.dingtalk.api.model.vo.fail;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-09 19:46
 */
public class CorpOrgSyncFailVO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  suiteKey
    private String suiteKey;
    //  企业corpId
    private String corpId;
    //  失败的类型
    private String corpFailType;
    //  失败的信息
    private String failInfo;

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

    public String getCorpFailType() {
        return corpFailType;
    }

    public void setCorpFailType(String corpFailType) {
        this.corpFailType = corpFailType;
    }

    public String getFailInfo() {
        return failInfo;
    }

    public void setFailInfo(String failInfo) {
        this.failInfo = failInfo;
    }

    @Override
    public String toString() {
        return "CorpOrgSyncFailVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                ", corpFailType='" + corpFailType + '\'' +
                ", failInfo='" + failInfo + '\'' +
                '}';
    }
}
