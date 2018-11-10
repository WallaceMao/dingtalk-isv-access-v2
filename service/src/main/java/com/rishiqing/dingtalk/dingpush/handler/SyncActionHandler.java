package com.rishiqing.dingtalk.dingpush.handler;

import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 13:51
 */
public interface SyncActionHandler {
    public void handleSyncAction(OpenSyncBizDataVO data);
}
