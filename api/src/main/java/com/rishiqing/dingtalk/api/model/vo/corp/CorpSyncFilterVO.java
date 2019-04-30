package com.rishiqing.dingtalk.api.model.vo.corp;

/**
 * @author: Da Shuai
 * @create: 2019-04-25 17:44
 */
public class CorpSyncFilterVO {
    private String corpId;
    private Long count;
    private String status;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CorpSyncFilterVO{" +
                "corpId='" + corpId + '\'' +
                ", count=" + count +
                ", status='" + status + '\'' +
                '}';
    }
}
