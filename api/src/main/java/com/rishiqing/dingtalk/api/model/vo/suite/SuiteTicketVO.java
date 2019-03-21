package com.rishiqing.dingtalk.api.model.vo.suite;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 15:19
 */
public class SuiteTicketVO implements Serializable {
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
        return "SuiteTicketVO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteTicket='" + suiteTicket + '\'' +
                '}';
    }
}
