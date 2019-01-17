package com.rishiqing.dingtalk.svc.http;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求返回对象
 * @author Wallace Mao
 * Date: 2018-11-03 17:56
 */
public class HttpResult {
    /**
     *
     * @param errorCode
     * @return
     */
    public static Map<String, Object> getFailure(String errorCode, String msg) {
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("errcode", errorCode);
        result.put("errmsg", msg);
        return result;
    }

    public static Map<String, Object> getSuccess(Map<String, Object> data) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("errcode","0");
        result.put("errmsg", "ok");
        if (data != null) {
            result.putAll(data);
        }
        return result;
    }
}
