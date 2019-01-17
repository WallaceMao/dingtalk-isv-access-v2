package com.rishiqing.dingtalk.api.model.domain.corp;

import java.util.Date;

/**
 * 并发锁，防止多用户同时获取token，导致先返回token的用户的token失效
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class CorpLockDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  所的key值
    private String lockKey;
    //  锁的超时时间
    private Date expire;

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

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "CorpDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", lockKey='" + lockKey + '\'' +
                ", expire='" + expire + '\'' +
                '}';
    }
}
