package com.rishiqing.dingtalk.biz.converter.suite;

import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.dao.model.suite.CorpSuiteAuthDO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:28
 */
public class CorpSuiteAuthConverter {
    public static CorpSuiteAuthVO CorpSuiteAuthDO2CorpSuiteAuthVO(CorpSuiteAuthDO corpSuiteAuthDO){
        if(null==corpSuiteAuthDO){
            return null;
        }
        CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
        corpSuiteAuthVO.setId(corpSuiteAuthDO.getId());
        corpSuiteAuthVO.setGmtCreate(corpSuiteAuthDO.getGmtCreate());
        corpSuiteAuthVO.setGmtModified(corpSuiteAuthDO.getGmtModified());
        corpSuiteAuthVO.setSuiteKey(corpSuiteAuthDO.getSuiteKey());
        corpSuiteAuthVO.setCorpId(corpSuiteAuthDO.getCorpId());
        corpSuiteAuthVO.setPermanentCode(corpSuiteAuthDO.getPermanentCode());
        corpSuiteAuthVO.setChPermanentCode(corpSuiteAuthDO.getChPermanentCode());
        corpSuiteAuthVO.setAuthUserId(corpSuiteAuthDO.getAuthUserId());
        return corpSuiteAuthVO;
    }

    public static CorpSuiteAuthDO CorpSuiteAuthVO2CorpSuiteAuthDO(CorpSuiteAuthVO corpSuiteAuthVO){
        if(null==corpSuiteAuthVO){
            return null;
        }
        CorpSuiteAuthDO corpSuiteAuthDO = new CorpSuiteAuthDO();
        corpSuiteAuthDO.setId(corpSuiteAuthVO.getId());
        corpSuiteAuthDO.setGmtCreate(corpSuiteAuthVO.getGmtCreate());
        corpSuiteAuthDO.setGmtModified(corpSuiteAuthVO.getGmtModified());
        corpSuiteAuthDO.setSuiteKey(corpSuiteAuthVO.getSuiteKey());
        corpSuiteAuthDO.setCorpId(corpSuiteAuthVO.getCorpId());
        corpSuiteAuthDO.setPermanentCode(corpSuiteAuthVO.getPermanentCode());
        corpSuiteAuthDO.setChPermanentCode(corpSuiteAuthVO.getChPermanentCode());
        corpSuiteAuthDO.setAuthUserId(corpSuiteAuthVO.getAuthUserId());
        return corpSuiteAuthDO;
    }
}
