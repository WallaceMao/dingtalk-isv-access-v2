package com.rishiqing.dingtalk.api.model.vo.message;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 16:56
 */
public class MessageResultVO {
    private Long taskId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "MessageResultVO{" +
                "taskId=" + taskId +
                '}';
    }
}
