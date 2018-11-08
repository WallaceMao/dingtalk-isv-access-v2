package com.rishiqing.dingtalk.isv.api.service.biz;

import com.rishiqing.dingtalk.isv.api.model.message.MessageBody;
import com.rishiqing.dingtalk.isv.api.model.message.MessageVO;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:17
 */
public interface MessageBizService {
    void sendCorpMessageAsync(MessageVO message);
}
