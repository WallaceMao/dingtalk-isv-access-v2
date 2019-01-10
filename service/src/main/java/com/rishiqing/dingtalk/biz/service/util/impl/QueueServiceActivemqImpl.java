package com.rishiqing.dingtalk.biz.service.util.impl;

import com.rishiqing.dingtalk.biz.service.util.QueueService;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.manager.corp.CorpManager;
import com.rishiqing.dingtalk.manager.corp.CorpStaffManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * @author Wallace Mao
 * Date: 2018-11-12 5:46
 */
public class QueueServiceActivemqImpl implements QueueService {
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpStaffManager corpStaffManager;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("solutionQueue")
    private Queue solutionQueue;

    /**
     * 发送生成团队的解决方案的方法
     * @param corpId
     */
    @Override
    public void sendToGenerateTeamSolution(String corpId, String userId){
        CorpVO corpVO = corpManager.getCorpByCorpId(corpId);
        CorpStaffVO staff;
        if(userId == null){
            staff = corpManager.findATeamCreator(corpId);
        }else{
            staff = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, userId);
        }
        if(staff == null){
            throw new BizRuntimeException("no team solution creator found: " + corpId + ", userId: " + userId);
        }
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
    @Override
    public void sendToGenerateStaffSolution(String corpId, String userId){
        CorpVO corpVO = corpManager.getCorpByCorpId(corpId);
        CorpStaffVO staff = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, userId);
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
