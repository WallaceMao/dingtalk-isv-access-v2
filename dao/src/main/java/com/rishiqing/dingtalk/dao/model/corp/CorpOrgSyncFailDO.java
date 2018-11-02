package com.rishiqing.dingtalk.dao.model.corp;


import com.rishiqing.dingtalk.biz.corp.enumtype.CorpFailType;

import java.util.Date;

/**
 * 从钉钉中获取组织结构（部门和人员）信息失败的对象。如果发生失败会在该表中记录，系统定时查询该表进行重试
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class CorpOrgSyncFailDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  suiteKey
    private String suiteKey;
    //  企业corpId
    private String corpId;
    //  失败的类型
    private CorpFailType corpFailType;
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

    public CorpFailType getCorpFailType() {
        return corpFailType;
    }

    public void setCorpFailType(CorpFailType corpFailType) {
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
        return "CorpOrgSyncFailDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                ", corpFailType=" + corpFailType +
                ", failInfo='" + failInfo + '\'' +
                '}';
    }
}
