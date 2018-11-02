package com.rishiqing.dingtalk.biz.isv.converter;

import com.rishiqing.dingtalk.biz.isv.model.SuiteVO;
import com.rishiqing.dingtalk.dao.model.suite.SuiteDO;

public class SuiteConverter {
    public static SuiteVO suiteDO2SuiteVO(SuiteDO suiteDO){
        if(null==suiteDO){
            return null;
        }
        SuiteVO suiteVO = new SuiteVO();
        suiteVO.setId(suiteDO.getId());
        suiteVO.setSuiteName(suiteDO.getSuiteName());
        suiteVO.setSuiteKey(suiteDO.getSuiteKey());
        suiteVO.setSuiteSecret(suiteDO.getSuiteSecret());
        suiteVO.setEncodingAesKey(suiteDO.getEncodingAesKey());
        suiteVO.setToken(suiteDO.getToken());
        suiteVO.setEventReceiveUrl(suiteDO.getEventReceiveUrl());
        suiteVO.setGmtCreate(suiteDO.getGmtCreate());
        suiteVO.setGmtModified(suiteDO.getGmtModified());
        suiteVO.setRsqAppName(suiteDO.getRsqAppName());
        suiteVO.setRsqAppToken(suiteDO.getRsqAppToken());
        suiteVO.setSuiteId(suiteDO.getSuiteId());
        return suiteVO;
    }

    public static SuiteDO suiteVO2SuiteDO(SuiteVO suiteVO){
        if(null==suiteVO){
            return null;
        }
        SuiteDO suiteDO = new SuiteDO();
        suiteDO.setId(suiteVO.getId());
        suiteDO.setSuiteName(suiteVO.getSuiteName());
        suiteDO.setSuiteKey(suiteVO.getSuiteKey());
        suiteDO.setSuiteSecret(suiteVO.getSuiteSecret());
        suiteDO.setEncodingAesKey(suiteVO.getEncodingAesKey());
        suiteDO.setToken(suiteVO.getToken());
        suiteDO.setEventReceiveUrl(suiteVO.getEventReceiveUrl());
        suiteDO.setGmtCreate(suiteVO.getGmtCreate());
        suiteDO.setGmtModified(suiteVO.getGmtModified());
        suiteDO.setRsqAppName(suiteVO.getRsqAppName());
        suiteDO.setRsqAppToken(suiteVO.getRsqAppToken());
        suiteDO.setSuiteId(suiteVO.getSuiteId());
        return suiteDO;
    }
}