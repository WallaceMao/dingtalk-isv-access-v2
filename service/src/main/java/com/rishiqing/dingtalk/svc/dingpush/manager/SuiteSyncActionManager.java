package com.rishiqing.dingtalk.svc.dingpush.manager;

import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;

/**
 * @author: Da Shuai
 * @create: 2019-04-29 10:49
 */
public interface SuiteSyncActionManager {
    void handleSyncData(OpenSyncBizDataVO data);
}
