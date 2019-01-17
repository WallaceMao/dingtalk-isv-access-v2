package com.rishiqing.dingtalk.api.service.biz;

import com.rishiqing.dingtalk.api.model.vo.message.MessageVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:17
 */
public interface MessageBizService {
    void sendCorpMessageAsync(MessageVO message);
}
