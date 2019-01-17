package com.rishiqing.dingtalk.req.dingtalk.auth.http;

import com.rishiqing.dingtalk.api.model.vo.message.MessageResultVO;
import com.rishiqing.dingtalk.api.model.vo.message.MessageVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:15
 */
public interface MessageRequestHelper {
    MessageResultVO sendCorpConversationAsync(String corpToken, MessageVO message);
}
