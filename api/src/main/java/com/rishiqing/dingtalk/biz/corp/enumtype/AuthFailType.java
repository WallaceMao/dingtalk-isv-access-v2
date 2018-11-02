package com.rishiqing.dingtalk.biz.corp.enumtype;

/**
 * 企业授权套件失败场景枚举
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public enum AuthFailType {

    //  获取套件suiteToken失败
    GET_SUITE_TOKEN_FAILE("get_suite_token_fail"),
    //  获取永久授权码失败
    GET_PERMANENT_CODE_FAILE("get_permanent_code_fail"),
    //  激活企业为应用失败
    ACTIVE_CORP_APP_FAILE("active_corp_app_fail"),
    //  获取企业信息失败
    GET_CORP_INFO_FAILE("get_corp_info_fail"),
    //  注册企业回调地址失败
    SAVE_CORP_SUITE_CALLBACK_FAILE("save_corp_suite_callback__fail");

    private final String key;

    private AuthFailType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static AuthFailType getAuthFailType(String key){
        AuthFailType[] authFailTypeArr = AuthFailType.values();
        for (AuthFailType o : authFailTypeArr) {
            if (o.getKey().equalsIgnoreCase(key)) {
                return o;
            }
        }
        return null;
    }
}