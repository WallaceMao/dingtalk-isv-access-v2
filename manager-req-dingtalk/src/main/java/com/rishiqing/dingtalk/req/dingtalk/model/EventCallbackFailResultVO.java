package com.rishiqing.dingtalk.req.dingtalk.model;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-12 15:00
 */
public class EventCallbackFailResultVO extends CommonResultVO {
    private Boolean hasMore;
    private List<EventCallbackVO> failedList;

    public static class EventCallbackVO extends CommonResultVO {
        private String callbackTag;
        private Long eventTime;
        private String data;
        private String corpId;
        private List<Long> deptId;
        private List<String> userId;

        public Long getEventTime() {
            return eventTime;
        }

        public void setEventTime(Long eventTime) {
            this.eventTime = eventTime;
        }

        public String getCallbackTag() {
            return callbackTag;
        }

        public void setCallbackTag(String callbackTag) {
            this.callbackTag = callbackTag;
        }

        public String getCorpId() {
            return corpId;
        }

        public void setCorpId(String corpId) {
            this.corpId = corpId;
        }

        public List<Long> getDeptId() {
            return deptId;
        }

        public void setDeptId(List<Long> deptId) {
            this.deptId = deptId;
        }

        public List<String> getUserId() {
            return userId;
        }

        public void setUserId(List<String> userId) {
            this.userId = userId;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "EventCallbackVO{" +
                    "callbackTag='" + callbackTag + '\'' +
                    ", eventTime=" + eventTime +
                    ", data='" + data + '\'' +
                    ", corpId='" + corpId + '\'' +
                    ", deptId=" + deptId +
                    ", userId=" + userId +
                    '}';
        }
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<EventCallbackVO> getFailedList() {
        return failedList;
    }

    public void setFailedList(List<EventCallbackVO> failedList) {
        this.failedList = failedList;
    }

    @Override
    public String toString() {
        return "EventCallbackFailResultVO{" +
                "hasMore=" + hasMore +
                ", failedList=" + failedList +
                '}';
    }
}
