package com.rishiqing.dingtalk.api.model.vo.suite;

/**
 * @author Wallace Mao
 * Date: 2018-10-29 0:05
 */
public class AuthGlobalLockVO {
    private Long id;
    private String lockKey;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AuthGlobalLock{" +
                "id=" + id +
                ", lockKey='" + lockKey + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
