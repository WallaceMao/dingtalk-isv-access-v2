package com.rishiqing.dingtalk.api.model.domain.config;

import java.util.Date;

/**
 * @author: Da Shuai
 * @create: 2019-04-24 17:11
 */
public class FilterConfigDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String filterKey;
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

    public String getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
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
        return "FilterConfigDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", filterKey='" + filterKey + '\'' +
                ", autoFilter=" + autoFilter +
                ", autoFilterThreshold=" + autoFilterThreshold +
                '}';
    }
}
