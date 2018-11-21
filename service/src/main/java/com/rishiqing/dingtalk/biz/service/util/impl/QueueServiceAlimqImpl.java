package com.rishiqing.dingtalk.biz.service.util.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.rishiqing.dingtalk.biz.event.SolutionEvent;
import com.rishiqing.dingtalk.biz.service.util.QueueService;
import com.rishiqing.dingtalk.isv.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpStaffManageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-21 15:16
 */
public class QueueServiceAlimqImpl implements QueueService {
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private Producer alimqSolutionProducer;
    @Resource(name = "alimqSolutionConfig")
    private Map<String, String> alimqSolutionConfig;

    @Override
    public void sendToGenerateTeamSolution(String corpId, String userId) {
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpStaffVO staff;
        if(userId == null){
            staff = corpManageService.findATeamCreator(corpId);
        }else{
            staff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, userId);
        }
        if(staff == null){
            throw new BizRuntimeException("no team solution creator found: " + corpId + ", userId: " + userId);
        }

        SolutionEvent event = new SolutionEvent(
                "team",
                corpVO.getRsqId(),
                staff.getRsqUserId()
        );

        Message msg = new Message(
                alimqSolutionConfig.get("topic"),
                alimqSolutionConfig.get("tag"),
                JSON.toJSONBytes(event));

        msg.setKey(event.generateMessageKey());
        alimqSolutionProducer.send(msg);
    }

    @Override
    public void sendToGenerateStaffSolution(String corpId, String userId) {
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpStaffVO staff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpId, userId);

        SolutionEvent event = new SolutionEvent(
                "staff",
                corpVO.getRsqId(),
                staff.getRsqUserId()
        );

        Message msg = new Message(
                alimqSolutionConfig.get("topic"),
                alimqSolutionConfig.get("tag"),
                JSON.toJSONBytes(event));

        msg.setKey(event.generateMessageKey());
        alimqSolutionProducer.send(msg);
    }
}
