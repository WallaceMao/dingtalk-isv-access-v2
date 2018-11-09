package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.event.CorpOrgSyncEvent;
import com.rishiqing.dingtalk.isv.api.event.CorpSuiteAuthEvent;
import com.rishiqing.dingtalk.isv.api.model.fail.CorpOrgSyncFailVO;
import com.rishiqing.dingtalk.isv.api.model.fail.CorpSuiteAuthFailVO;

import java.util.List;

/**
 * 用来处理各种错误
 * @author Wallace Mao
 * Date: 2018-11-03 21:49
 */
public interface FailBizService {
    public void saveCorpSuiteAuthFail(CorpSuiteAuthEvent corpSuiteAuthEvent);

    void saveCorpOrgSyncFail(CorpOrgSyncEvent corpOrgSyncEvent);

    List<CorpSuiteAuthFailVO> getCorpSuiteAuthFailList();

    List<CorpOrgSyncFailVO> getCorpOrgSyncFailList();

    void deleteCorpSuiteAuthFailById(Long id);

    void deleteCorpOrgSyncFailById(Long id);
}
