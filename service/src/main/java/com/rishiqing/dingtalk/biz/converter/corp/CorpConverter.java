package com.rishiqing.dingtalk.biz.converter.corp;

import com.rishiqing.dingtalk.dao.model.corp.CorpDO;
import com.rishiqing.dingtalk.dao.model.corp.CorpStaffDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpCountWithCreatorVO;
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
}
