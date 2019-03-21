package com.rishiqing.dingtalk.svc.converter.corp;


import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;

import static com.rishiqing.dingtalk.svc.constant.SystemConstant.CORP_CALLBACK_STATUS_DEFAULT;

/**
 * @author Wallace Mao
 * Date: 2019-01-12 17:09
 */
public class CorpCallbackConverter {
    public static CorpCallbackQueueDO json2CorpCallbackQueueDO(String suiteKey, JSONObject json) {
        if (json == null) {
            return null;
        }
        CorpCallbackQueueDO callbackDO = new CorpCallbackQueueDO();
        callbackDO.setSuiteKey(suiteKey);
        callbackDO.setTag(json.getString("EventType"));
        callbackDO.setCorpId(json.getString("CorpId"));
        callbackDO.setEventJSON(json.toJSONString());
        callbackDO.setSuccess(false);
        callbackDO.setTimestamp(json.getLong("TimeStamp"));
        callbackDO.setStatus(CORP_CALLBACK_STATUS_DEFAULT);

        return callbackDO;
    }
}
