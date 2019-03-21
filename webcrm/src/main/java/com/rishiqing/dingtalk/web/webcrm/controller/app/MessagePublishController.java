package com.rishiqing.dingtalk.web.webcrm.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.vo.message.AsyncSendProgressVO;
import com.rishiqing.dingtalk.api.model.vo.message.AsyncSendResultVO;
import com.rishiqing.dingtalk.api.service.biz.MessageBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * 群发消息的接口
 * @link https://open-doc.dingtalk.com/microapp/serverapi3/is3zms
 * Date: 2019-03-14 10:29
 */
@RestController
@RequestMapping(value = {"/v3w/message", "/crm/v3w/message"})
public class MessagePublishController {
    private static final Logger bizLogger = LoggerFactory.getLogger(MessagePublishController.class);

    @Autowired
    private MessageBizService messageBizService;

    @PostMapping("/publish/teamsAdmins")
    public Map publishMessageToTeamsAdmins(
            @RequestBody String jsonString
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/publishMessageToTeamsAdmins",
                new LogFormatter.KeyValue("jsonString", jsonString)
        ));
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject json = JSON.parseObject(jsonString);
            Date startDate = parser.parse(json.getString("scopeDateStart"));
            Date endDate = parser.parse(json.getString("scopeDateEnd"));
            JSONObject msg = json.getJSONObject("message");
            Map publishResultMap = messageBizService.publishMessageToAllAdmin(startDate, endDate, msg);
            map.put("errcode", 0);
            map.put("data", publishResultMap);
        } catch (ParseException e) {
            bizLogger.error("format error: ", e);
            map.put("errcode", 1);
            map.put("errmsg", "Date format error");
        } catch (Exception e) {
            bizLogger.error("exception in publishMessageToTeamsAdmins", e);
            map.put("errcode", 1);
            map.put("errmsg", "error");
        }
        return map;
    }

    @GetMapping("/publish/progress/{corpId}")
    public Map getLatestPublishMessageProgress(
            @PathVariable("corpId") String corpId
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/getLatestPublishMessageProgress",
                new LogFormatter.KeyValue("corpId", corpId)
        ));
        Map<String, Object> map = new HashMap<>();
        try {
            AsyncSendProgressVO progress = messageBizService.queryLatestPublishMessageProgress(corpId);
            map.put("errcode", 0);
            map.put("data", JSON.toJSONString(progress));
        } catch(IllegalArgumentException e) {
            map.put("errcode", 2);
            map.put("errmsg", "send task not found");
        } catch (Exception e) {
            bizLogger.error("exception in getLatestPublishMessageProgress", e);
            map.put("errcode", 1);
            map.put("errmsg", "error");
        }
        return map;
    }

    @GetMapping("/publish/result/{corpId}")
    public Map getLatestPublishMessageResult(
            @PathVariable("corpId") String corpId
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/getLatestPublishMessageResult",
                new LogFormatter.KeyValue("corpId", corpId)
        ));
        Map<String, Object> map = new HashMap<>();
        try {
            AsyncSendResultVO result = messageBizService.queryLatestPublishMessageResult(corpId);
            map.put("errcode", 0);
            map.put("data", JSON.toJSONString(result));
        } catch(IllegalArgumentException e) {
            map.put("errcode", 2);
            map.put("errmsg", "send task not found");
        } catch (Exception e) {
            bizLogger.error("exception in getLatestPublishMessageResult", e);
            map.put("errcode", 1);
            map.put("errmsg", "error");
        }
        return map;
    }
}
