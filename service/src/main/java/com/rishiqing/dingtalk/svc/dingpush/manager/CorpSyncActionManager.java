package com.rishiqing.dingtalk.svc.dingpush.manager;

import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;

/**
 * @author: Da Shuai
 * @create: 2019-04-29 10:48
 */
public interface CorpSyncActionManager {
    void handleSyncData(OpenSyncBizDataVO data);
}
