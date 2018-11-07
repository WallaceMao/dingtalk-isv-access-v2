package com.rishiqing.dingtalk.biz.converter.suite;

import com.rishiqing.dingtalk.dao.model.suite.CorpAppDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAppVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:49
 */
public class CorpAppConverter {
    public static List<CorpAppVO> corpAuthInfoVO2AppVOList(CorpAuthInfoVO corpAuthInfo){
        if(corpAuthInfo == null || corpAuthInfo.getAuthInfo() == null || corpAuthInfo.getAuthCorpInfo() == null){
            return null;
        }
        CorpAuthInfoVO.AuthCorpInfo corpInfo = corpAuthInfo.getAuthCorpInfo();
        List<CorpAuthInfoVO.Agent> agentList = corpAuthInfo.getAuthInfo().getAgent();
        List<CorpAppVO> corpAppVOList = new ArrayList<>(agentList.size());
        for (CorpAuthInfoVO.Agent agent : agentList) {
            CorpAppVO corpAppVO = new CorpAppVO();
            corpAppVO.setCorpId(corpInfo.getCorpId());
            corpAppVO.setAgentId(agent.getAgentId());
            corpAppVO.setAgentName(agent.getAgentName());
            corpAppVO.setLogoUrl(agent.getLogoUrl());
            corpAppVO.setAppId(agent.getAppId());
            corpAppVOList.add(corpAppVO);
        }
        return corpAppVOList;
    }

    public static CorpAppVO corpAppDO2CorpAppVO(CorpAppDO corpAppDO){
        if(null == corpAppDO){
            return null;
        }
        CorpAppVO corpAppVO = new CorpAppVO();
        corpAppVO.setId(corpAppDO.getId());
        corpAppVO.setGmtCreate(corpAppDO.getGmtCreate());
        corpAppVO.setGmtModified(corpAppDO.getGmtModified());
        corpAppVO.setAppId(corpAppDO.getAppId());
        corpAppVO.setAgentName(corpAppDO.getAgentName());
        corpAppVO.setAgentId(corpAppDO.getAgentId());
        corpAppVO.setLogoUrl(corpAppDO.getLogoUrl());
        corpAppVO.setCorpId(corpAppDO.getCorpId());
        return corpAppVO;
    }

    public static CorpAppDO corpAppVO2CorpAppDO(CorpAppVO corpAppVO){
        if(null == corpAppVO){
            return null;
        }
        CorpAppDO corpAppDO = new CorpAppDO();
        corpAppDO.setId(corpAppVO.getId());
        corpAppDO.setGmtCreate(corpAppVO.getGmtCreate());
        corpAppDO.setGmtModified(corpAppVO.getGmtModified());
        corpAppDO.setAppId(corpAppVO.getAppId());
        corpAppDO.setAgentId(corpAppVO.getAgentId());
        corpAppDO.setAgentName(corpAppVO.getAgentName());
        corpAppDO.setLogoUrl(corpAppVO.getLogoUrl());
        corpAppDO.setCorpId(corpAppVO.getCorpId());
        return corpAppDO;
    }
}
