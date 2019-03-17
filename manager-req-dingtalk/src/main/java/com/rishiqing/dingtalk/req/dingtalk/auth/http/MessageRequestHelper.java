package com.rishiqing.dingtalk.req.dingtalk.auth.http;

import com.rishiqing.dingtalk.api.model.vo.message.AsyncSendProgressVO;
import com.rishiqing.dingtalk.api.model.vo.message.AsyncSendResultVO;
import com.rishiqing.dingtalk.api.model.vo.message.MessageResultVO;
import com.rishiqing.dingtalk.api.model.vo.message.MessageVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:15
 */
public interface MessageRequestHelper {
    MessageResultVO sendCorpConversationAsync(String corpToken, MessageVO message);

    AsyncSendProgressVO queryMessageSendProgress(String corpToken, Long agentId, Long taskId);

    AsyncSendResultVO queryMessageSendResult(String corpToken, Long agentId, Long taskId);
}
