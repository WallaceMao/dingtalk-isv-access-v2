package com.rishiqing.dingtalk.dao.model.corp;

import java.util.Date;

/**
 * 企业员工对象
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class CorpStaffDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  公司id
    private String corpId;
    //  用户id
    private String userId;
    //  用户姓名
    private String name;
    //  分机号（ISV不可见）
    private String tel;
    //  办公地点（ISV不可见）
    private String workPlace;
    //  备注（ISV不可见）
    private String remark;
    //  手机号码（ISV不可见）
    private String mobile;
    //  员工的电子邮箱（ISV不可见）
    private String email;
    //  是否已经激活, true表示已激活, false表示未激活
    private Boolean active;
    //  在对应的部门中的排序, Map结构的json字符串, key是部门的Id, value是人员在这个部门的排序值
    private String orderInDepts;
    //  是否为企业的管理员, true表示是, false表示不是
    private Boolean isAdmin;
    //  是否为企业的老板, true表示是, false表示不是
    private Boolean isBoss;
    //  钉钉Id
    private String dingId;
    //  在对应的部门中是否为主管, Map结构的json字符串, key是部门的Id, value是人员在这个部门中是否为主管, true表示是, false表示不是
    private String isLeaderInDepts;
    //  是否号码隐藏, true表示隐藏, false表示不隐藏
    private Boolean isHide;
    //  成员所属部门id列表
    private String department;
    //  职位信息
    private String position;
    //  头像url
    private String avatar;
    //  员工工号
    private String jobnumber;
    //  扩展属性，可以设置多种属性(但手机上最多只能显示10个扩展属性，具体显示哪些属性，请到OA管理后台->设置->通讯录信息设置和OA管理后台->设置->手机端显示信息设置)性
    private String extattr;

    //  钉钉免登接口使用code换取用户信息时获取到的用户信息。是否是管理员，code免登时获取到
    private Boolean isSys;
    //  钉钉免登接口使用code换取用户信息时获取到的用户信息。级别，0：非管理员 1：超级管理员（主管理员） 2：普通管理员（子管理员） 100：老板
    private Integer sysLevel;

    //  钉钉unionId
    private String unionId;

    //  日事清中的用户id
    private String rsqUserId;
    //  日事清中的用户登录名
    private String rsqUsername;
    //  日事清中的用户登录密码
    private String rsqPassword;
    //  存储了日事清中用户的标识
    private String rsqLoginToken;
    //  用来存储可见范围版本的字段，每次用户修改可见范围都会更新该字段为修改时的时间戳
    private Long scopeVersion;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getBoss() {
        return isBoss;
    }

    public void setBoss(Boolean boss) {
        isBoss = boss;
    }

    public String getDingId() {
        return dingId;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    public Boolean getHide() {
        return isHide;
    }

    public void setHide(Boolean hide) {
        isHide = hide;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public Boolean getSys() {
        return isSys;
    }

    public void setSys(Boolean sys) {
        isSys = sys;
    }

    public Integer getSysLevel() {
        return sysLevel;
    }

    public void setSysLevel(Integer sysLevel) {
        this.sysLevel = sysLevel;
    }

    public String getOrderInDepts() {
        return orderInDepts;
    }

    public void setOrderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
    }

    public String getIsLeaderInDepts() {
        return isLeaderInDepts;
    }

    public void setIsLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExtattr() {
        return extattr;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRsqUserId() {
        return rsqUserId;
    }

    public void setRsqUserId(String rsqUserId) {
        this.rsqUserId = rsqUserId;
    }

    public String getRsqUsername() {
        return rsqUsername;
    }

    public void setRsqUsername(String rsqUsername) {
        this.rsqUsername = rsqUsername;
    }

    public String getRsqPassword() {
        return rsqPassword;
    }

    public void setRsqPassword(String rsqPassword) {
        this.rsqPassword = rsqPassword;
    }

    public String getRsqLoginToken() {
        return rsqLoginToken;
    }

    public void setRsqLoginToken(String rsqLoginToken) {
        this.rsqLoginToken = rsqLoginToken;
    }

    public Long getScopeVersion() {
        return scopeVersion;
    }

    public void setScopeVersion(Long scopeVersion) {
        this.scopeVersion = scopeVersion;
    }

    @Override
    public String toString() {
        return "CorpStaffDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", remark='" + remark + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", orderInDepts='" + orderInDepts + '\'' +
                ", isAdmin=" + isAdmin +
                ", isBoss=" + isBoss +
                ", dingId='" + dingId + '\'' +
                ", isLeaderInDepts='" + isLeaderInDepts + '\'' +
                ", isHide=" + isHide +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", avatar='" + avatar + '\'' +
                ", jobnumber='" + jobnumber + '\'' +
                ", extattr='" + extattr + '\'' +
                ", isSys=" + isSys +
                ", sysLevel=" + sysLevel +
                ", unionId='" + unionId + '\'' +
                ", rsqUserId='" + rsqUserId + '\'' +
                ", rsqUsername='" + rsqUsername + '\'' +
                ", rsqPassword='" + rsqPassword + '\'' +
                ", rsqLoginToken='" + rsqLoginToken + '\'' +
                ", scopeVersion=" + scopeVersion +
                '}';
    }
}
