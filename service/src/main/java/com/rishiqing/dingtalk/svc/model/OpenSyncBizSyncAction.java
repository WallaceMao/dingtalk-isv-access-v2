package com.rishiqing.dingtalk.svc.model;

/**
 * 钉钉云推送的事件
 * 注意，这里只枚举需要处理的tag，其他的tag不要在这里加上
 * @link https://open-doc.dingtalk.com/microapp/ln6dmh/troq7i
 */
public class OpenSyncBizSyncAction {

    public enum Tag {
        //  推送ticket
        SUITE_TICKET("suite_ticket"),
        //  表示企业授权套件
        ORG_SUITE_AUTH("org_suite_auth"),
        //  表示企业变更授权范围
        ORG_SUITE_CHANGE("org_suite_change"),
        //  表示企业解除授权
        ORG_SUITE_RELIEVE("org_suite_relieve"),
        //  微应用启用
        ORG_MICRO_APP_RESTORE("org_micro_app_restore"),
        //  微应用停用
        ORG_MICRO_APP_STOP("org_micro_app_stop"),
        //  微应用删除，保留企业对套件的授权
        ORG_MICRO_APP_REMOVE("org_micro_app_remove"),
        //  通讯录用户增加
        USER_ADD_ORG("user_add_org"),
        //  通讯录用户更改
        USER_MODIFY_ORG("user_modify_org"),
        //  通讯录用户离职
        USER_LEAVE_ORG("user_leave_org"),
        //  表示企业修改员工所在部门事件之后的员工信息
        USER_DEPT_CHANGE("user_dept_change"),
        //  表示企业修改员工所在角色(包括管理员变更)事件之后的员工信息
        USER_ROLE_CHANGE("user_role_change"),
        //  通讯录企业部门创建
        ORG_DEPT_CREATE("org_dept_create"),
        //  通讯录企业部门修改
        ORG_DEPT_MODIFY("org_dept_modify"),
        //  通讯录企业部门删除
        ORG_DEPT_REMOVE("org_dept_remove"),
        //  表示企业增加角色事件之后的角色信息
        ORG_ROLE_ADD("org_role_add"),
        //  表示企业修改角色事件之后的角色信息
        ORG_ROLE_MODIFY("org_role_modify"),
        //  企业删除
        ORG_ROLE_REMOVE("org_role_remove"),
        //  企业信息发生变更
        ORG_UPDATE("org_update"),
        //  企业被解散
        ORG_REMOVE("org_remove"),
        //  订单信息
        MARKET_ORDER("market_order");

        private final String key;

        Tag(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
