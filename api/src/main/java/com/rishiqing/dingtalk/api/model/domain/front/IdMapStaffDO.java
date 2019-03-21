package com.rishiqing.dingtalk.api.model.domain.front;

/**
 * 用来给微应用页面做id转换的
 * @author Wallace Mao
 * Date: 2018-11-08 11:39
 */
public class IdMapStaffDO {
    private String userId;

    private String rsqUserId;  //日事清中的用户id

    private String emplId;
    private String avatar;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRsqUserId() {
        return rsqUserId;
    }

    public void setRsqUserId(String rsqUserId) {
        this.rsqUserId = rsqUserId;
    }

    public String getEmplId() {
        return emplId;
    }

    public void setEmplId(String emplId) {
        this.emplId = emplId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IdMapStaffDO{" +
                "userId='" + userId + '\'' +
                ", rsqUserId='" + rsqUserId + '\'' +
                ", emplId='" + emplId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
