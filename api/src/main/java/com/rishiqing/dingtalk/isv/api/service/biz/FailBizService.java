package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.event.CorpOrgSyncEvent;
import com.rishiqing.dingtalk.isv.api.event.CorpSuiteAuthEvent;

/**
 * 用来处理各种错误
 * @author Wallace Mao
 * Date: 2018-11-03 21:49
 */
public interface FailBizService {
    public void saveCorpSuiteAuthFail(CorpSuiteAuthEvent corpSuiteAuthEvent);

    void saveCorpOrgSyncFail(CorpOrgSyncEvent corpOrgSyncEvent);
}
