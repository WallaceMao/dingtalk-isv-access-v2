package com.rishiqing.dingtalk.biz.service.util;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * @author Wallace Mao
 * Date: 2018-11-12 5:46
 */
public class QueueService {
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpSuiteAuthManageService corpSuiteAuthManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("solutionQueue")
    private Queue solutionQueue;

    /**
     * 发送生成团队的解决方案的方法
     * @param corpId
     */
    public void sendToGenerateTeamSolution(String corpId, String userId){
        if(userId == null){
            CorpSuiteAuthVO corpSuiteAuthVO = corpSuiteAuthManageService.getCorpSuiteAuth(corpId);
            userId = corpSuiteAuthVO.getAuthUserId();
        }
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpStaffVO staff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, userId);
        final String rsqCorpId = corpVO.getRsqId();
        final String rsqUserId = staff.getRsqUserId();

        jmsTemplate.send(solutionQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("teamId", rsqCorpId);
                mapMessage.setString("userId", rsqUserId);
                mapMessage.setObject("type", "team");
                return mapMessage;
            }
        });
    }

    /**
     * 发送生成个人的解决方案的方法
     * @param corpId
     * @param userId
     */
    public void sendToGenerateStaffSolution(String corpId, String userId){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpStaffVO staff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, userId);
        final String rsqCorpId = corpVO.getRsqId();
        final String rsqUserId = staff.getRsqUserId();

        jmsTemplate.send(solutionQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("teamId", rsqCorpId);
                mapMessage.setString("userId", rsqUserId);
                mapMessage.setObject("type", "staff");
                return mapMessage;
            }
        });
    }
}
