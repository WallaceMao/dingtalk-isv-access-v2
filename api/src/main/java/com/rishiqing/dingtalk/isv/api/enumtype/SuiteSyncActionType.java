package com.rishiqing.dingtalk.isv.api.enumtype;

/**
 * 钉钉云直接推送到数据库中的syncAction.
 * @link https://open-doc.dingtalk.com/microapp/ln6dmh/troq7i
 * @author Wallace Mao
 * Date: 2018-11-07 0:10
 */
public enum SuiteSyncActionType {
    //  套件票据的最新状态
    SUITE_TICKET("suite_ticket"),
    ORG_SUITE_AUTH("org_suite_auth"),
    ORG_SUITE_CHANGE("org_suite_change"),
    ORG_SUITE_RELIEVE("org_suite_relieve"),
    ORG_MICRO_APP_RESTORE("org_micro_app_restore"),
    ORG_MICRO_APP_STOP("org_micro_app_stop"),
    ORG_MICRO_APP_REMOVE("org_micro_app_remove"),
    USER_ADD_ORG("user_add_org"),
    USER_MODIFY_ORG("user_modify_org"),
    USER_DEPT_CHANGE("user_dept_change"),
    USER_ROLE_CHANGE("user_role_change"),
    USER_LEAVE_ORG("user_leave_org"),
    ORG_DEPT_CREATE("org_dept_create"),
    ORG_DEPT_MODIFY("org_dept_modify"),
    ORG_ROLE_ADD("org_role_add"),
    ORG_ROLE_MODIFY("org_role_modify"),
    ORG_UPDATE("org_update"),
    ORG_REMOVE("org_remove"),
    MARKET_ORDER("market_order"),
    MARKET_ORDER_CANCEL("market_order_cancel"),
    CONTACT_ADD_ORG("contact_add_org"),
    CONTACT_MODIFY_ORG("contact_modify_org"),
    CONTACT_LEAVE_ORG("contact_leave_org"),
    ISV_BPMS("isv_bpms"),
    ISV_BPMS_CANCEL("isv_bpms_cancel");

    private final String key;

    SuiteSyncActionType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static SuiteSyncActionType getSuiteSyncActionType(String key){
        SuiteSyncActionType[] suiteDbPushTypeArr = SuiteSyncActionType.values();
        for (SuiteSyncActionType o : suiteDbPushTypeArr) {
            if (o.getKey().equalsIgnoreCase(key)) {
                return o;
            }
        }
        return null;
    }
}
