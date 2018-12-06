package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.event.CorpOrgChangedEvent;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgCreatedEvent;
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
    void saveCorpSuiteAuthFail(CorpSuiteAuthEvent corpSuiteAuthEvent);

    void saveCorpOrgSyncFail(CorpOrgCreatedEvent corpOrgCreatedEvent);

    void saveCorpOrgSyncFail(CorpOrgChangedEvent corpOrgChangedEvent);

    List<CorpSuiteAuthFailVO> getCorpSuiteAuthFailList();

    List<CorpOrgSyncFailVO> getCorpOrgSyncFailList();

    void deleteCorpSuiteAuthFailById(Long id);

    void deleteCorpOrgSyncFailById(Long id);
}
