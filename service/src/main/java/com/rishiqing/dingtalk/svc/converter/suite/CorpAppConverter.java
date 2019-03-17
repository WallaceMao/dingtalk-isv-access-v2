package com.rishiqing.dingtalk.svc.converter.suite;

import com.rishiqing.dingtalk.api.model.vo.corp.CorpAppVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;

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
}
