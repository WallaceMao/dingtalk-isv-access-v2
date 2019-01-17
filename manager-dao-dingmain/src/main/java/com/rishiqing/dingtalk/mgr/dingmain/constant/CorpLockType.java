package com.rishiqing.dingtalk.mgr.dingmain.constant;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 2:35
 */
public enum CorpLockType {

    TOKEN("corpToken"),
    JSAPI_TICKET("corpJsapiTicket"),
    CHN_TOKEN("corpChannelJsapiTicket"),
    CHN_JSAPI_TICKET("corpChannelJsapiTicket");


    private final String key;

    private CorpLockType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static CorpLockType getCorpLockType(String key){
        CorpLockType[] authFaileTypeArr = CorpLockType.values();
        for (CorpLockType o : authFaileTypeArr) {
            if (o.getKey().equalsIgnoreCase(key)) {
                return o;
            }
        }
        return null;
    }
}
