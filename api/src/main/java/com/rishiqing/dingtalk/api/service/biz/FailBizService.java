package com.rishiqing.dingtalk.api.service.biz;

import com.rishiqing.dingtalk.api.event.CorpOrgChangedEvent;
import com.rishiqing.dingtalk.api.event.CorpOrgCreatedEvent;
import com.rishiqing.dingtalk.api.event.CorpSuiteAuthEvent;
import com.rishiqing.dingtalk.api.event.CorpSuiteChangeEvent;
import com.rishiqing.dingtalk.api.model.vo.fail.CorpOrgSyncFailVO;
import com.rishiqing.dingtalk.api.model.vo.fail.CorpSuiteAuthFailVO;

import java.util.List;

/**
 * 用来处理各种错误
 * @author Wallace Mao
 * Date: 2018-11-03 21:49
 */
public interface FailBizService {
    void saveCorpSuiteAuthFail(CorpSuiteAuthEvent corpSuiteAuthEvent);

    void saveCorpSuiteChangeFail(CorpSuiteChangeEvent corpSuiteChangeEvent);

    void saveCorpOrgSyncFail(CorpOrgCreatedEvent corpOrgCreatedEvent);

    void saveCorpOrgSyncFail(CorpOrgChangedEvent corpOrgChangedEvent);

    List<CorpSuiteAuthFailVO> getCorpSuiteAuthFailList();

    List<CorpOrgSyncFailVO> getCorpOrgSyncFailList();

    void deleteCorpSuiteAuthFailById(Long id);

    void deleteCorpOrgSyncFailById(Long id);
}
