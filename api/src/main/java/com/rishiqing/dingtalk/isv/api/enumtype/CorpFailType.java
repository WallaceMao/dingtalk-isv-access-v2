package com.rishiqing.dingtalk.isv.api.enumtype;

/**
 * 企业除授权key之外，其他失败类型
 * @author Wallace Mao
 * Date: 2018-10-31 23:59
 */
public enum CorpFailType {

    //  获取钉钉的企业信息失败，包括获取公司失败，获取部门失败，获取部门成员失败等
    GET_CORP_INFO("get_corp_info"),
    //  推送到第三方服务器失败
    PUT_ISV_CORP("put_isv_corp");

    private final String key;

    private CorpFailType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static CorpFailType getCorpFailType(String key){
        CorpFailType[] corpFaileTypeArr = CorpFailType.values();
        for (CorpFailType o : corpFaileTypeArr) {
            if (o.getKey().equalsIgnoreCase(key)) {
                return o;
            }
        }
        return null;
    }
}