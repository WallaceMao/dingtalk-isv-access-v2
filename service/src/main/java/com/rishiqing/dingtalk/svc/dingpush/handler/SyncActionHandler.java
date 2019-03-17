package com.rishiqing.dingtalk.svc.dingpush.handler;

import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 13:51
 */
public interface SyncActionHandler {
    public void handleSyncAction(OpenSyncBizDataVO data);
}
