package com.rishiqing.dingtalk.api.model.vo.message;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2019-03-14 11:54
 */
public class AsyncSendProgressVO {
    private Long progressInPercent;
    private Long status;
    private Date sendTime;

    public Long getProgressInPercent() {
        return progressInPercent;
    }

    public void setProgressInPercent(Long progressInPercent) {
        this.progressInPercent = progressInPercent;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "AsyncSendProgressVO{" +
                "progressInPercent=" + progressInPercent +
                ", status=" + status +
                ", sendTime=" + sendTime +
                '}';
    }
}
