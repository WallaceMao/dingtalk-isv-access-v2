package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.dao.mapper.corp.CorpOrgSyncFailDao;
import com.rishiqing.dingtalk.dao.model.corp.CorpOrgSyncFailDO;
import com.rishiqing.dingtalk.isv.api.enumtype.AuthFailType;
import com.rishiqing.dingtalk.isv.api.enumtype.CorpFailType;
import com.rishiqing.dingtalk.isv.api.enumtype.SuitePushType;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgSyncEvent;
import com.rishiqing.dingtalk.isv.api.event.CorpSuiteAuthEvent;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import com.rishiqing.dingtalk.dao.mapper.suite.CorpSuiteAuthFailDao;
import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthFailDO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 21:51
 */
public class FailBizServiceImpl implements FailBizService {
    @Autowired
    private CorpSuiteAuthFailDao corpSuiteAuthFailDao;
    @Autowired
    private CorpOrgSyncFailDao corpOrgSyncFailDao;

    /**
     * 将corpSuiteAuth事件失败的记录插入到数据库
     * @param corpSuiteAuthEvent
     */
    @Override
    public void saveCorpSuiteAuthFail(CorpSuiteAuthEvent corpSuiteAuthEvent) {
        CorpSuiteAuthFailDO corpSuiteAuthFailDO = new CorpSuiteAuthFailDO();
        corpSuiteAuthFailDO.setSuiteKey(corpSuiteAuthEvent.getSuiteKey());
        corpSuiteAuthFailDO.setCorpId(corpSuiteAuthEvent.getCorpId());
        corpSuiteAuthFailDO.setAuthFailType(AuthFailType.ACTIVE_CORP_APP_FAILE.getKey());
        corpSuiteAuthFailDO.setSuitePushType(SuitePushType.TMP_AUTH_CODE.getKey());
        corpSuiteAuthFailDao.saveOrUpdateCorpSuiteAuthFail(corpSuiteAuthFailDO);
    }

    /**
     * 将组织结构同步的事件记录到数据库中
     * @param corpOrgSyncEvent
     */
    @Override
    public void saveCorpOrgSyncFail(CorpOrgSyncEvent corpOrgSyncEvent){
        CorpOrgSyncFailDO corpOrgSyncFailDO = new CorpOrgSyncFailDO();
        corpOrgSyncFailDO.setSuiteKey(corpOrgSyncEvent.getSuiteKey());
        corpOrgSyncFailDO.setCorpId(corpOrgSyncEvent.getCorpId());
        corpOrgSyncFailDO.setCorpFailType(CorpFailType.PUT_ISV_CORP.getKey());
        corpOrgSyncFailDO.setFailInfo("error");
        corpOrgSyncFailDao.saveOrUpdateCorpOrgSyncFail(corpOrgSyncFailDO);
    }
}
