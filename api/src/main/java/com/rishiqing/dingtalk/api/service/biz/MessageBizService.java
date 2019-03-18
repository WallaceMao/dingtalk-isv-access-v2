package com.rishiqing.dingtalk.api.service.biz;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.model.vo.message.AsyncSendProgressVO;
import com.rishiqing.dingtalk.api.model.vo.message.AsyncSendResultVO;
import com.rishiqing.dingtalk.api.model.vo.message.MessageVO;

import java.util.Date;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 15:17
 */
public interface MessageBizService {
    void sendCorpMessageAsync(MessageVO message);

    Map publishMessageToAllAdmin(Date scopeDateStart, Date scopeDateEnd, JSONObject message);

    AsyncSendProgressVO queryLatestPublishMessageProgress(String corpId);

    AsyncSendResultVO queryLatestPublishMessageResult(String corpId);
}
