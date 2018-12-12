package com.rishiqing.dingtalk.dao.model.corp;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-12-11 18:44
 */
public class CorpStatisticDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  公司id
    private String corpId;
    //  缓存团队人数
    private Long staffCount;

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

    public Long getStaffCount() {
        return staffCount;
    }

    public void setStaffCount(Long staffCount) {
        this.staffCount = staffCount;
    }

    @Override
    public String toString() {
        return "CorpStatisticDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", staffCount=" + staffCount +
                '}';
    }
}
