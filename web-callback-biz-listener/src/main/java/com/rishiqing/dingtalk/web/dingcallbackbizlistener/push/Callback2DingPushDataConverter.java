package com.rishiqing.dingtalk.web.dingcallbackbizlistener.push;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;
import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-15 23:32
 */
public interface Callback2DingPushDataConverter {
    List<OpenSyncBizDataDO> convert(CorpCallbackQueueDO callbackDO);
}
