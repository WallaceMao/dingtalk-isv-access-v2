package com.rishiqing.dingtalk.dao.model.corp;

import java.util.Date;

/**
 * 用户修改组织结构发生失败的记录表。当失败时，会在该表中插入记录，系统定时读取该表中的失败记录进行重试
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class CorpCallbackFailDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  套件key
    private String suiteKey;
    //  企业corpId
    private String corpId;
    //  钉钉推送过来的事件
    private String eventJSON;
    //  事件的标签
    private String tag;
    //  失败信息
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

    public String getEventJSON() {
        return eventJSON;
    }

    public void setEventJSON(String eventJSON) {
        this.eventJSON = eventJSON;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFailInfo() {
        return failInfo;
    }

    public void setFailInfo(String failInfo) {
        this.failInfo = failInfo;
    }

    @Override
    public String toString() {
        return "CorpCallbackFailDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", corpId='" + corpId + '\'' +
                ", eventJSON='" + eventJSON + '\'' +
                ", tag='" + tag + '\'' +
                ", failInfo='" + failInfo + '\'' +
                '}';
    }
}
