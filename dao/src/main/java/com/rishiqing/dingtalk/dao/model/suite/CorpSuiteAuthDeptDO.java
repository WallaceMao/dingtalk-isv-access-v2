package com.rishiqing.dingtalk.dao.model.suite;

import java.util.Date;

/**
 * 用来保存企业开通微应用时的授权可见范围信息，具体的使用方法参照
 * @link https://open-doc.dingtalk.com/microapp/serverapi2/vt6v7m
 * @author Wallace Mao
 * Date: 2018-12-15 18:03
 */
public class CorpSuiteAuthDeptDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  企业corpId
    private String corpId;
    //  套件key
    private String suiteKey;
    // 授权范围用户的id
    private String deptId;

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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "CorpSuiteAuthDeptDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", deptId='" + deptId + '\'' +
                '}';
    }
}
