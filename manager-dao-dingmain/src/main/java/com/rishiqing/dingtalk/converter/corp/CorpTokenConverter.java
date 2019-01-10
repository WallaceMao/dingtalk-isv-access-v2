package com.rishiqing.dingtalk.converter.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpTokenDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 10:49
 */
public class CorpTokenConverter {
    public static CorpTokenVO CorpTokenDO2CorpTokenVO(CorpTokenDO corpTokenDO){
        if(null==corpTokenDO){
            return null;
        }
        CorpTokenVO corpTokenVO = new CorpTokenVO();
        corpTokenVO.setId(corpTokenDO.getId());
        corpTokenVO.setGmtCreate(corpTokenDO.getGmtCreate());
        corpTokenVO.setGmtModified(corpTokenDO.getGmtModified());
        corpTokenVO.setCorpId(corpTokenDO.getCorpId());
        corpTokenVO.setSuiteKey(corpTokenDO.getSuiteKey());
        corpTokenVO.setCorpToken(corpTokenDO.getCorpToken());
        corpTokenVO.setExpiredTime(corpTokenDO.getExpiredTime());
        return corpTokenVO;
    }

    public static CorpTokenDO CorpTokenVO2CorpTokenDO(CorpTokenVO corpTokenVO){
        if(null==corpTokenVO){
            return null;
        }
        CorpTokenDO corpTokenDO = new CorpTokenDO();
        corpTokenDO.setId(corpTokenVO.getId());
        corpTokenDO.setGmtCreate(corpTokenVO.getGmtCreate());
        corpTokenDO.setGmtModified(corpTokenVO.getGmtModified());
        corpTokenDO.setCorpId(corpTokenVO.getCorpId());
        corpTokenDO.setSuiteKey(corpTokenVO.getSuiteKey());
        corpTokenDO.setCorpToken(corpTokenVO.getCorpToken());
        corpTokenDO.setExpiredTime(corpTokenVO.getExpiredTime());
        return corpTokenDO;
    }
}
