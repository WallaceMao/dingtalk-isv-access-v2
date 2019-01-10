package com.rishiqing.dingtalk.auth.http;

import com.rishiqing.dingtalk.isv.api.model.message.MessageResultVO;
import com.rishiqing.dingtalk.isv.api.model.message.MessageVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:15
 */
public interface MessageRequestHelper {
    MessageResultVO sendCorpConversationAsync(String corpToken, MessageVO message);
}
