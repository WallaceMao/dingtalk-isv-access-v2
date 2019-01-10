package com.rishiqing.dingtalk.converter.fail;

import com.rishiqing.dingtalk.dao.model.fail.CorpOrgSyncFailDO;
import com.rishiqing.dingtalk.dao.model.fail.CorpSuiteAuthFailDO;
import com.rishiqing.dingtalk.isv.api.model.fail.CorpOrgSyncFailVO;
import com.rishiqing.dingtalk.isv.api.model.fail.CorpSuiteAuthFailVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-09 19:49
 */
public class FailConverter {
    public static CorpSuiteAuthFailVO corpSuiteAuthFailDO2CorpSuiteAuthFailVO(CorpSuiteAuthFailDO corpSuiteAuthFailDO){
        if(corpSuiteAuthFailDO == null){
            return null;
        }
        CorpSuiteAuthFailVO vo = new CorpSuiteAuthFailVO();
        vo.setId(corpSuiteAuthFailDO.getId());
        vo.setGmtCreate(corpSuiteAuthFailDO.getGmtCreate());
        vo.setGmtModified(corpSuiteAuthFailDO.getGmtModified());
        vo.setSuiteKey(corpSuiteAuthFailDO.getSuiteKey());
        vo.setCorpId(corpSuiteAuthFailDO.getCorpId());
        vo.setAuthFailType(corpSuiteAuthFailDO.getAuthFailType());
        vo.setFailInfo(corpSuiteAuthFailDO.getFailInfo());
        vo.setSuitePushType(corpSuiteAuthFailDO.getSuitePushType());
        return vo;
    }

    public static List<CorpSuiteAuthFailVO> corpSuiteAuthFailDOList2CorpSuiteAuthFailVOList(List<CorpSuiteAuthFailDO> doList){
        if(doList == null){
            return null;
        }
        List<CorpSuiteAuthFailVO> voList = new ArrayList<>(doList.size());
        for(CorpSuiteAuthFailDO corpSuiteAuthFailDO : doList){
            voList.add(corpSuiteAuthFailDO2CorpSuiteAuthFailVO(corpSuiteAuthFailDO));
        }
        return voList;
    }

    public static CorpOrgSyncFailVO corpOrgSyncFailDO2CorpOrgSyncFailVO(CorpOrgSyncFailDO corpOrgSyncFailDO){
        if(corpOrgSyncFailDO == null){
            return null;
        }
        CorpOrgSyncFailVO vo = new CorpOrgSyncFailVO();
        vo.setId(corpOrgSyncFailDO.getId());
        vo.setGmtCreate(corpOrgSyncFailDO.getGmtCreate());
        vo.setGmtModified(corpOrgSyncFailDO.getGmtModified());
        vo.setSuiteKey(corpOrgSyncFailDO.getSuiteKey());
        vo.setCorpId(corpOrgSyncFailDO.getCorpId());
        vo.setCorpFailType(corpOrgSyncFailDO.getCorpFailType());
        vo.setFailInfo(corpOrgSyncFailDO.getFailInfo());
        return vo;
    }

    public static List<CorpOrgSyncFailVO> corpOrgSyncFailDOList2CorpOrgSyncFailVOList(List<CorpOrgSyncFailDO> doList){
        if(doList == null){
            return null;
        }
        List<CorpOrgSyncFailVO> voList = new ArrayList<>(doList.size());
        for(CorpOrgSyncFailDO corpOrgSyncFailDO : doList){
            voList.add(corpOrgSyncFailDO2CorpOrgSyncFailVO(corpOrgSyncFailDO));
        }
        return voList;
    }
}
