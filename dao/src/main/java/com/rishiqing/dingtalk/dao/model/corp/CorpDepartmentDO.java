package com.rishiqing.dingtalk.dao.model.corp;

import java.util.Date;

/**
 * 企业部门对象
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public class CorpDepartmentDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  公司Id
    private String corpId;
    //  部门id
    private Long deptId;
    //  部门名称
    private String name;
    //  在父部门中的次序值
    private Long order;
    //  父部门的id，这里的记录的是父部门的deptId
    private Long parentId;
    //  是否同步创建一个关联此部门的企业群, true表示是, false表示不是
    private Boolean createDeptGroup;
    //  当群已经创建后，是否有新人加入部门会自动加入该群, true表示是, false表示不是
    private Boolean autoAddUser;
    //  是否隐藏部门, true表示隐藏, false表示显示
    private Boolean deptHiding;
    //  可以查看指定隐藏部门的其他部门列表，如果部门隐藏，则此值生效，取值为其他的部门id组成的的字符串，使用|符号进行分割
    private String deptPerimits;
    //  可以查看指定隐藏部门的其他人员列表，如果部门隐藏，则此值生效，取值为其他的人员userid组成的的字符串，使用|符号进行分割
    private String userPerimits;
    //  是否本部门的员工仅可见员工自己, 为true时，本部门员工默认只能看到员工自己
    private Boolean outerDept;
    //  本部门的员工仅可见员工自己为true时，可以配置额外可见部门，值为部门id组成的的字符串，使用|符号进行分割
    private String outerPermitDepts;
    //  本部门的员工仅可见员工自己为true时，可以配置额外可见人员，值为userid组成的的字符串，使用| 符号进行分割
    private String outerPermitUsers;
    //  企业群群主
    private String orgDeptOwner;
    //  部门的主管列表,取值为由主管的userid组成的字符串，不同的userid使用|符号进行分割
    private String deptManagerUseridList;
    //  钉钉与日事清绑定的id
    private String rsqId;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setCreateDeptGroup(Boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }

    public Boolean getAutoAddUser() {
        return autoAddUser;
    }

    public void setAutoAddUser(Boolean autoAddUser) {
        this.autoAddUser = autoAddUser;
    }

    public Boolean getDeptHiding() {
        return deptHiding;
    }

    public void setDeptHiding(Boolean deptHiding) {
        this.deptHiding = deptHiding;
    }

    public String getDeptPerimits() {
        return deptPerimits;
    }

    public void setDeptPerimits(String deptPerimits) {
        this.deptPerimits = deptPerimits;
    }

    public String getUserPerimits() {
        return userPerimits;
    }

    public void setUserPerimits(String userPerimits) {
        this.userPerimits = userPerimits;
    }

    public Boolean getOuterDept() {
        return outerDept;
    }

    public void setOuterDept(Boolean outerDept) {
        this.outerDept = outerDept;
    }

    public String getOuterPermitDepts() {
        return outerPermitDepts;
    }

    public void setOuterPermitDepts(String outerPermitDepts) {
        this.outerPermitDepts = outerPermitDepts;
    }

    public String getOuterPermitUsers() {
        return outerPermitUsers;
    }

    public void setOuterPermitUsers(String outerPermitUsers) {
        this.outerPermitUsers = outerPermitUsers;
    }

    public String getOrgDeptOwner() {
        return orgDeptOwner;
    }

    public void setOrgDeptOwner(String orgDeptOwner) {
        this.orgDeptOwner = orgDeptOwner;
    }

    public String getDeptManagerUseridList() {
        return deptManagerUseridList;
    }

    public void setDeptManagerUseridList(String deptManagerUseridList) {
        this.deptManagerUseridList = deptManagerUseridList;
    }

    public String getRsqId() {
        return rsqId;
    }

    public void setRsqId(String rsqId) {
        this.rsqId = rsqId;
    }

    @Override
    public String toString() {
        return "CorpDepartmentDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", corpId='" + corpId + '\'' +
                ", deptId=" + deptId +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentId=" + parentId +
                ", createDeptGroup=" + createDeptGroup +
                ", autoAddUser=" + autoAddUser +
                ", deptHiding=" + deptHiding +
                ", deptPerimits='" + deptPerimits + '\'' +
                ", userPerimits='" + userPerimits + '\'' +
                ", outerDept=" + outerDept +
                ", outerPermitDepts='" + outerPermitDepts + '\'' +
                ", outerPermitUsers='" + outerPermitUsers + '\'' +
                ", orgDeptOwner='" + orgDeptOwner + '\'' +
                ", deptManagerUseridList='" + deptManagerUseridList + '\'' +
                ", rsqId='" + rsqId + '\'' +
                '}';
    }
}
