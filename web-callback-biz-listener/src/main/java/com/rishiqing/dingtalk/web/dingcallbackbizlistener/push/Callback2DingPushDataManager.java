package com.rishiqing.dingtalk.web.dingcallbackbizlistener.push;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;
import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;

import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2019-01-15 23:34
 */
public class Callback2DingPushDataManager {
    private Map<String, Callback2DingPushDataConverter> converterMap;

    public Map<String, Callback2DingPushDataConverter> getConverterMap() {
        return converterMap;
    }

    public void setConverterMap(Map<String, Callback2DingPushDataConverter> converterMap) {
        this.converterMap = converterMap;
    }

    public List<OpenSyncBizDataDO> convert(CorpCallbackQueueDO callbackDO) {
        String tag = callbackDO.getTag();
        Callback2DingPushDataConverter converter = converterMap.get(tag);
        if(converter == null){
            throw new BizRuntimeException("no handler found for suite sync action: " + tag);
        }
        return converter.convert(callbackDO);
    }
}
