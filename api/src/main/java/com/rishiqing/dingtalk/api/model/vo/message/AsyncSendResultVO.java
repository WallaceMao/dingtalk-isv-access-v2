package com.rishiqing.dingtalk.api.model.vo.message;

import java.util.Date;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-03-14 11:47
 */
public class AsyncSendResultVO {
    private List<String> invalidUserIdList;
    private List<String> forbiddenUserIdList;
    private List<String> failedUserIdList;
    private List<String> readUserIdList;
    private List<String> unreadUserIdList;
    private List<Long> invalidDeptIdList;
    private Date sendTime;

    public List<String> getInvalidUserIdList() {
        return invalidUserIdList;
    }

    public void setInvalidUserIdList(List<String> invalidUserIdList) {
        this.invalidUserIdList = invalidUserIdList;
    }

    public List<String> getForbiddenUserIdList() {
        return forbiddenUserIdList;
    }

    public void setForbiddenUserIdList(List<String> forbiddenUserIdList) {
        this.forbiddenUserIdList = forbiddenUserIdList;
    }

    public List<String> getFailedUserIdList() {
        return failedUserIdList;
    }

    public void setFailedUserIdList(List<String> failedUserIdList) {
        this.failedUserIdList = failedUserIdList;
    }

    public List<String> getReadUserIdList() {
        return readUserIdList;
    }

    public void setReadUserIdList(List<String> readUserIdList) {
        this.readUserIdList = readUserIdList;
    }

    public List<String> getUnreadUserIdList() {
        return unreadUserIdList;
    }

    public void setUnreadUserIdList(List<String> unreadUserIdList) {
        this.unreadUserIdList = unreadUserIdList;
    }

    public List<Long> getInvalidDeptIdList() {
        return invalidDeptIdList;
    }

    public void setInvalidDeptIdList(List<Long> invalidDeptIdList) {
        this.invalidDeptIdList = invalidDeptIdList;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "AsyncSendResultVO{" +
                "invalidUserIdList=" + invalidUserIdList +
                ", forbiddenUserIdList=" + forbiddenUserIdList +
                ", failedUserIdList=" + failedUserIdList +
                ", readUserIdList=" + readUserIdList +
                ", unreadUserIdList=" + unreadUserIdList +
                ", invalidDeptIdList=" + invalidDeptIdList +
                ", sendTime=" + sendTime +
                '}';
    }
}
