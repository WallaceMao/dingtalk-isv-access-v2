package com.rishiqing.dingtalk.api.model.domain.corp;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2019-03-14 11:06
 */
public class CorpMessagePublishLogDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    private String message;
    private String corpId;
    private Long agentId;
    private String userIdList;
    private String deptIdList;
    private Boolean toAllUser = false;
    private Long taskId;

    private CorpMessagePublishLogDO(){}

    public static CorpMessagePublishLogDO build(
            String corpId,
            Long agentId,
            String message) {
        CorpMessagePublishLogDO log = new CorpMessagePublishLogDO();
        log.setCorpId(corpId);
        log.setAgentId(agentId);
        log.setMessage(message);
        return log;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(String userIdList) {
        this.userIdList = userIdList;
    }

    public String getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(String deptIdList) {
        this.deptIdList = deptIdList;
    }

    public Boolean getToAllUser() {
        return toAllUser;
    }

    public void setToAllUser(Boolean toAllUser) {
        this.toAllUser = toAllUser;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "CorpMessagePublishLogDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", message='" + message + '\'' +
                ", corpId='" + corpId + '\'' +
                ", agentId=" + agentId +
                ", userIdList='" + userIdList + '\'' +
                ", deptIdList='" + deptIdList + '\'' +
                ", toAllUser=" + toAllUser +
                ", taskId=" + taskId +
                '}';
    }
}
