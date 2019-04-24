package com.rishiqing.dingtalk.api.model.vo.config;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 17:39
 */
public class ConfigVO {
    private Boolean autoFilter;
    private Long autoFilterThreshold;

    public Boolean getAutoFilter() {
        return autoFilter;
    }

    public void setAutoFilter(Boolean autoFilter) {
        this.autoFilter = autoFilter;
    }

    public Long getAutoFilterThreshold() {
        return autoFilterThreshold;
    }

    public void setAutoFilterThreshold(Long autoFilterThreshold) {
        this.autoFilterThreshold = autoFilterThreshold;
    }

    @Override
    public String toString() {
        return "ConfigVO{" +
                "autoFilter=" + autoFilter +
                ", autoFilterThreshold=" + autoFilterThreshold +
                '}';
    }
}
