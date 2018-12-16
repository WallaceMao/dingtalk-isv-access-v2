package com.rishiqing.dingtalk.isv.api.model.suite;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 19:16
 */
public class CorpSuiteAuthVO implements Serializable {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  企业corpId
    private String corpId;
    //  套件key
    private String suiteKey;
    //  永久授权码value
    private String permanentCode;
    //  永久授权码类型 1:微应用;2:服务窗
    private Integer codeType;
    //  服务窗永久授权码
    private String chPermanentCode;
    //  开通应用的管理员的id
    private String authUserId;
    // 授权的部门的id列表
    private List<Long> scopeAuthedDeptIdList;
    // 授权的用户的id列表
    private List<String> scopeAuthedUserIdList;

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

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getChPermanentCode() {
        return chPermanentCode;
    }

    public void setChPermanentCode(String chPermanentCode) {
        this.chPermanentCode = chPermanentCode;
    }

    public String getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(String authUserId) {
        this.authUserId = authUserId;
    }

    public List<Long> getScopeAuthedDeptIdList() {
        return scopeAuthedDeptIdList;
    }

    public void setScopeAuthedDeptIdList(List<Long> scopeAuthedDeptIdList) {
        this.scopeAuthedDeptIdList = scopeAuthedDeptIdList;
    }

    public List<String> getScopeAuthedUserIdList() {
        return scopeAuthedUserIdList;
    }

    public void setScopeAuthedUserIdList(List<String> scopeAuthedUserIdList) {
        this.scopeAuthedUserIdList = scopeAuthedUserIdList;
    }

    @Override
    public String toString() {
        return "CorpSuiteAuthVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", permanentCode='" + permanentCode + '\'' +
                ", codeType=" + codeType +
                ", chPermanentCode='" + chPermanentCode + '\'' +
                ", authUserId='" + authUserId + '\'' +
                ", scopeAuthedDeptIdList=" + scopeAuthedDeptIdList +
                ", scopeAuthedUserIdList=" + scopeAuthedUserIdList +
                '}';
    }
}
