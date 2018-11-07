package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:27
 */
public interface SuiteDbCheckBizService {
    void checkDingPushEvent();

    void handleSyncData(OpenSyncBizDataVO data);
}
