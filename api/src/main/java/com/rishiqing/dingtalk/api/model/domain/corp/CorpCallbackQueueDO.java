package com.rishiqing.dingtalk.api.model.domain.corp;


import java.util.Date;

/**
 * 企业通讯录事件回调
 * Created by Wallace on 17-1-4.
 */
public class CorpCallbackQueueDO {

    // 主键
    private Long id;
    // 创建时间
    private Date gmtCreate;
    // 修改时间
    private Date gmtModified;
    // 企业corpid
    private String corpId;
    // 套件key
    private String suiteKey;
    // 事件json
    private String eventJSON;
    // 事件tag
    private String tag;
    // 失败信息
    private String failInfo;
    // 事件状态
    private Boolean isSuccess;
    // 时间戳
    private Long timestamp;
    // status
    private Long status;

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

    public String getFailInfo() {
        return failInfo;
    }

    public void setFailInfo(String failInfo) {
        this.failInfo = failInfo;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CorpCallbackQueueDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", suiteKey='" + suiteKey + '\'' +
                ", eventJSON='" + eventJSON + '\'' +
                ", tag='" + tag + '\'' +
                ", failInfo='" + failInfo + '\'' +
                ", isSuccess=" + isSuccess +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}
