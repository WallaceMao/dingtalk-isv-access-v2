package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.rishiqing.dingtalk.biz.http.MessageRequestHelper;
import com.rishiqing.dingtalk.isv.api.model.message.MessageBody;
import com.rishiqing.dingtalk.isv.api.model.message.MessageVO;
import com.rishiqing.dingtalk.isv.api.service.biz.MessageBizService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:17
 */
public class MessageBizServiceImpl implements MessageBizService {
    @Autowired
    private MessageRequestHelper messageRequestHelper;

    /**
     * 发送通知，由于钉钉中对于userIdList和deptIdList的数量有限制，因此这里需要分批次发送
     * @param message
     */
    @Override
    public void sendCorpMessageAsync(MessageVO message) {
        //TODO 分批次发送
    }
}
