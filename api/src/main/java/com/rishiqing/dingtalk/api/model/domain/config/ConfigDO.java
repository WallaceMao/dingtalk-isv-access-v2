package com.rishiqing.dingtalk.api.model.domain.config;

import java.util.Date;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 17:11
 */
public class ConfigDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String configKey;
    private Boolean autoFilter;
    private Long autoFilterThreshold;

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

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

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
        return "ConfigDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", configKey='" + configKey + '\'' +
                ", autoFilter=" + autoFilter +
                ", autoFilterThreshold=" + autoFilterThreshold +
                '}';
    }
}
