package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.biz.converter.fail.FailConverter;
import com.rishiqing.dingtalk.dao.mapper.fail.CorpOrgSyncFailDao;
import com.rishiqing.dingtalk.dao.model.fail.CorpOrgSyncFailDO;
import com.rishiqing.dingtalk.isv.api.enumtype.AuthFailType;
import com.rishiqing.dingtalk.isv.api.enumtype.CorpFailType;
import com.rishiqing.dingtalk.isv.api.enumtype.SuitePushType;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgChangedEvent;
import com.rishiqing.dingtalk.isv.api.event.CorpOrgCreatedEvent;
import com.rishiqing.dingtalk.isv.api.event.CorpSuiteAuthEvent;
import com.rishiqing.dingtalk.isv.api.model.fail.CorpOrgSyncFailVO;
import com.rishiqing.dingtalk.isv.api.model.fail.CorpSuiteAuthFailVO;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import com.rishiqing.dingtalk.dao.mapper.fail.CorpSuiteAuthFailDao;
import com.rishiqing.dingtalk.dao.model.fail.CorpSuiteAuthFailDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        corpSuiteAuthFailDO.setFailInfo(corpSuiteAuthEvent.getInfo());
        corpSuiteAuthFailDao.saveOrUpdateCorpSuiteAuthFail(corpSuiteAuthFailDO);
    }

    /**
     * 将组织结构同步的事件记录到数据库中
     * @param corpOrgCreatedEvent
     */
    @Override
    public void saveCorpOrgSyncFail(CorpOrgCreatedEvent corpOrgCreatedEvent){
        CorpOrgSyncFailDO corpOrgSyncFailDO = new CorpOrgSyncFailDO();
        corpOrgSyncFailDO.setSuiteKey(corpOrgCreatedEvent.getSuiteKey());
        corpOrgSyncFailDO.setCorpId(corpOrgCreatedEvent.getCorpId());
        corpOrgSyncFailDO.setCorpFailType(CorpFailType.PUT_ISV_CORP.getKey());
        corpOrgSyncFailDO.setFailInfo(corpOrgCreatedEvent.getInfo());
        corpOrgSyncFailDao.saveOrUpdateCorpOrgSyncFail(corpOrgSyncFailDO);
    }

    @Override
    public void saveCorpOrgSyncFail(CorpOrgChangedEvent corpOrgChangedEvent){
        CorpOrgSyncFailDO corpOrgSyncFailDO = new CorpOrgSyncFailDO();
        corpOrgSyncFailDO.setSuiteKey(corpOrgChangedEvent.getSuiteKey());
        corpOrgSyncFailDO.setCorpId(corpOrgChangedEvent.getCorpId());
        corpOrgSyncFailDO.setCorpFailType(CorpFailType.PUT_ISV_CORP.getKey());
        corpOrgSyncFailDO.setFailInfo(corpOrgChangedEvent.getInfo());
        corpOrgSyncFailDao.saveOrUpdateCorpOrgSyncFail(corpOrgSyncFailDO);
    }

    @Override
    public List<CorpSuiteAuthFailVO> getCorpSuiteAuthFailList() {
        return FailConverter.corpSuiteAuthFailDOList2CorpSuiteAuthFailVOList(
                corpSuiteAuthFailDao.getCorpSuiteAuthFailList(0, 50)
        );
    }

    @Override
    public List<CorpOrgSyncFailVO> getCorpOrgSyncFailList() {
        return FailConverter.corpOrgSyncFailDOList2CorpOrgSyncFailVOList(
                corpOrgSyncFailDao.getCorpOrgSyncFailList(0, 50)
        );
    }

    @Override
    public void deleteCorpSuiteAuthFailById(Long id) {
        corpSuiteAuthFailDao.deleteCorpSuiteAuthFailById(id);
    }

    @Override
    public void deleteCorpOrgSyncFailById(Long id) {
        corpOrgSyncFailDao.deleteCorpOrgSyncFailById(id);
    }
}
