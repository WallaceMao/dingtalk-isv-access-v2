package com.rishiqing.dingtalk.api.model.domain.suite;

import java.util.Date;

/**
 * 换取套件访问开放平台的accessToken的Ticket
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class SuiteTicketDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  套件suiteKey
    private String suiteKey;
    //  套件ticket
    private String suiteTicket;

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

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getSuiteTicket() {
        return suiteTicket;
    }

    public void setSuiteTicket(String suiteTicket) {
        this.suiteTicket = suiteTicket;
    }

    @Override
    public String toString() {
        return "SuiteTicketDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteTicket='" + suiteTicket + '\'' +
                '}';
    }
}
