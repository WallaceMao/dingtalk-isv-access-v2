package com.workbei.listener;

import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.workbei.service.SolutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Wallace Mao
 * Date: 2018-11-12 5:35
 */
public class SolutionListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger("LSN_RSQ_CORP_CHANGE_LOGGER");

    @Autowired
    private SolutionService solutionService;

    @Override
    public void onMessage(Message message) {
        try{
            MapMessage mapMessage = (MapMessage)message;
            String teamId = mapMessage.getString("teamId");
            String userId = mapMessage.getString("userId");
            String type = mapMessage.getString("type");

            if("team".equals(type)){
                solutionService.generateTeamSolution(teamId, userId);
            }else if("staff".equals(type)){
                solutionService.generateUserSolution(teamId, userId);
            }else{
                throw new BizRuntimeException("invalid solution type: " + type + ", teamId: " + teamId + ", userId: " + userId);
            }
        } catch (Exception e) {
            logger.error("solution generate error: ", e);
        }
    }
}
