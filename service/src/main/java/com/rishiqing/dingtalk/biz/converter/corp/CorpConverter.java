package com.rishiqing.dingtalk.biz.converter.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:45
 */
public class CorpConverter {
    public static CorpVO corpAuthInfoVO2CorpVO(CorpAuthInfoVO corpAuthInfo, Long scopeVersion){
        if(corpAuthInfo == null || corpAuthInfo.getAuthCorpInfo() == null){
            return null;
        }
        CorpAuthInfoVO.AuthCorpInfo authCorpInfo = corpAuthInfo.getAuthCorpInfo();
        CorpVO corpVO = new CorpVO();
        corpVO.setCorpId(authCorpInfo.getCorpId());
        corpVO.setCorpLogoUrl(authCorpInfo.getCorpLogoUrl());
        corpVO.setCorpName(authCorpInfo.getCorpName());
        corpVO.setIndustry(authCorpInfo.getIndustry());
        corpVO.setInviteCode(authCorpInfo.getInviteCode());
        corpVO.setInviteUrl(authCorpInfo.getInviteUrl());
        corpVO.setScopeVersion(scopeVersion);
        return corpVO;
    }

    public static CorpVO CorpDO2CorpVO(CorpDO corpDO){
        if(null == corpDO){
            return null;
        }
        CorpVO corpVO = new CorpVO();
        corpVO.setId(corpDO.getId());
        corpVO.setGmtCreate(corpDO.getGmtCreate());
        corpVO.setGmtModified(corpDO.getGmtModified());
        corpVO.setInviteCode(corpDO.getInviteCode());
        corpVO.setInviteUrl(corpDO.getInviteUrl());
        corpVO.setIndustry(corpDO.getIndustry());
        corpVO.setCorpName(corpDO.getCorpName());
        corpVO.setCorpId(corpDO.getCorpId());
        corpVO.setCorpLogoUrl(corpDO.getCorpLogoUrl());
        corpVO.setRsqId(corpDO.getRsqId());
        corpVO.setScopeVersion(corpDO.getScopeVersion());
        return corpVO;
    }

    public static CorpDO CorpVO2CorpDO(CorpVO corpVO){
        if(null == corpVO){
            return null;
        }
        CorpDO corpDO = new CorpDO();
        corpDO.setId(corpVO.getId());
        corpDO.setGmtCreate(corpVO.getGmtCreate());
        corpDO.setGmtModified(corpVO.getGmtModified());
        corpDO.setInviteCode(corpVO.getInviteCode());
        corpDO.setInviteUrl(corpVO.getInviteUrl());
        corpDO.setIndustry(corpVO.getIndustry());
        corpDO.setCorpName(corpVO.getCorpName());
        corpDO.setCorpId(corpVO.getCorpId());
        corpDO.setCorpLogoUrl(corpVO.getCorpLogoUrl());
        corpDO.setRsqId(corpVO.getRsqId());
        corpDO.setScopeVersion(corpVO.getScopeVersion());
        return corpDO;
    }
}
