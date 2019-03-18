package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpMessagePublishLogDO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAppVO;
import com.rishiqing.dingtalk.api.model.vo.message.AsyncSendProgressVO;
import com.rishiqing.dingtalk.api.model.vo.message.AsyncSendResultVO;
import com.rishiqing.dingtalk.api.model.vo.message.MessageResultVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpMessageManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpStaffManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.AppManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.CorpAppManager;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.MessageRequestHelper;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpTokenVO;
import com.rishiqing.dingtalk.api.model.vo.message.MessageVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.api.service.biz.MessageBizService;
import com.rishiqing.dingtalk.svc.converter.message.MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:17
 */
public class MessageBizServiceImpl implements MessageBizService {
    private static final Logger bizLogger = LoggerFactory.getLogger(MessageBizServiceImpl.class);

    private static final long BATCH_SEND_SIZE = 20L;
    @Autowired
    private AppManager appManager;
    @Autowired
    private CorpAppManager corpAppManager;
    @Autowired
    private CorpManager corpManager;
    @Autowired
    private CorpMessageManager corpMessageManager;
    @Autowired
    private CorpStaffManager corpStaffManager;
    @Autowired
    private MessageRequestHelper messageRequestHelper;

    /**
     * 发送通知，由于钉钉中对于userIdList和deptIdList的数量有限制，因此这里需要分批次发送
     * @param message
     */
    @Override
    public void sendCorpMessageAsync(MessageVO message) {
        //TODO 要修改为分批次发送
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(message.getCorpId());
        messageRequestHelper.sendCorpConversationAsync(corpTokenVO.getCorpToken(), message);
    }

    @Override
    public Map publishMessageToAllAdmin(Date scopeDateStart, Date scopeDateEnd, JSONObject message) {
        List<String> corpIdList = corpManager.listCorpCorpIdByCreateTimeBetween(scopeDateStart, scopeDateEnd);
        Map<String, Object> resultMap = new HashMap<>();
        long successCount = 0;
        long failCount = 0;
        for (String corpId : corpIdList) {
            try {
                CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
                CorpAppVO corpAppVO = corpAppManager.getCorpAppByCorpIdAndAppId(
                        corpId, appManager.getDefaultAppVO().getAppId());
                Long agentId = corpAppVO.getAgentId();
                MessageVO messageVO = MessageConverter.json2MessageVO(corpId, agentId, message);

                //  获取到公司的管理员列表，给前20个管理员发送通知
                List<String> adminUserIdList = corpStaffManager.listCorpStaffUserIdByCorpIdAndIsAdminWithLimit(corpId, true, BATCH_SEND_SIZE);
                messageVO.setUserIdList(adminUserIdList);

                MessageResultVO result = messageRequestHelper.sendCorpConversationAsync(corpTokenVO.getCorpToken(), messageVO);
                CorpMessagePublishLogDO log = CorpMessagePublishLogDO.build(corpId, agentId, message.toJSONString());
                log.setUserIdList(JSON.toJSONString(adminUserIdList));
                log.setTaskId(result.getTaskId());

                corpMessageManager.saveOrUpdateCorpMessagePublishLog(log);
                successCount++;
            } catch (Exception e) {
                failCount++;
                bizLogger.error("post message exception", e);
            }
        }
        resultMap.put("successCount", successCount);
        resultMap.put("failCount", failCount);
        return resultMap;
    }

    @Override
    public AsyncSendProgressVO queryLatestPublishMessageProgress(String corpId) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        CorpAppVO corpAppVO = corpAppManager.getCorpAppByCorpIdAndAppId(
                corpId, appManager.getDefaultAppVO().getAppId());
        CorpMessagePublishLogDO messageLog = corpMessageManager.getLatestCorpMessagePublishLog(corpId);
        if (messageLog == null || messageLog.getTaskId() == null) {
            throw new IllegalArgumentException("queryLatestPublishMessageProgress task Id not found, corpId: " + corpId);
        }
        AsyncSendProgressVO progressVO = messageRequestHelper.queryMessageSendProgress(
                corpTokenVO.getCorpToken(), corpAppVO.getAgentId(), messageLog.getTaskId());
        progressVO.setSendTime(messageLog.getGmtCreate());
        return progressVO;
    }

    @Override
    public AsyncSendResultVO queryLatestPublishMessageResult(String corpId) {
        CorpTokenVO corpTokenVO = corpManager.getCorpTokenByCorpId(corpId);
        CorpAppVO corpAppVO = corpAppManager.getCorpAppByCorpIdAndAppId(
                corpId, appManager.getDefaultAppVO().getAppId());
        CorpMessagePublishLogDO messageLog = corpMessageManager.getLatestCorpMessagePublishLog(corpId);
        if (messageLog == null || messageLog.getTaskId() == null) {
            throw new IllegalArgumentException("queryLatestPublishMessageResult task Id not found, corpId: " + corpId);
        }
        AsyncSendResultVO result = messageRequestHelper.queryMessageSendResult(
                corpTokenVO.getCorpToken(), corpAppVO.getAgentId(), messageLog.getTaskId());
        result.setSendTime(messageLog.getGmtCreate());
        return result;
    }
}
