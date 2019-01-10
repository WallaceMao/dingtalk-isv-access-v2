package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.auth.http.MessageRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpTokenVO;
import com.rishiqing.dingtalk.isv.api.model.message.MessageVO;
import com.rishiqing.dingtalk.manager.corp.CorpManager;
import com.rishiqing.dingtalk.isv.api.service.biz.MessageBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:17
 */
public class MessageBizServiceImpl implements MessageBizService {
    private static final long BATCH_SEND_SIZE = 20L;
    @Autowired
    private CorpManager corpManager;
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
}
