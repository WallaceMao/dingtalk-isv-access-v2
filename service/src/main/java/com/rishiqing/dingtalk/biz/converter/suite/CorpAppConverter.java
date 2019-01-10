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
}
