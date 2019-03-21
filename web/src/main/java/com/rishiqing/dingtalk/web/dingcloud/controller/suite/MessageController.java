package com.rishiqing.dingtalk.web.dingcloud.controller.suite;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.svc.converter.message.MessageConverter;
import com.rishiqing.dingtalk.svc.http.HttpResult;
import com.rishiqing.dingtalk.svc.http.HttpResultCode;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAppVO;
import com.rishiqing.dingtalk.api.model.vo.message.MessageVO;
import com.rishiqing.dingtalk.api.model.vo.suite.AppVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.AppManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.CorpAppManager;
import com.rishiqing.dingtalk.api.service.biz.MessageBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 11:10
 */
@Controller
public class MessageController {
    private static final Logger bizLogger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageBizService messageBizService;
    @Autowired
    private AppManager appManager;
    @Autowired
    private CorpAppManager corpAppManager;

    /**
     * 该接口供前端直接调用。日事清后台调用的接口是sendNotification
     * @param request
     * @param corpId
     * @param appId
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/msg/sendasynccorpmessage", method = {RequestMethod.POST})
    public Map<String, Object> sendAsyncCorpMessage(HttpServletRequest request,
                                                    @RequestParam("corpid") String corpId,
                                                    @RequestParam("appid") Long appId,
                                                    @RequestBody JSONObject json
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/msg/sendasynccorpmessage",
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("appId", appId),
                new LogFormatter.KeyValue("json", json)
        ));
        try{
            CorpAppVO corpApp = corpAppManager.getCorpAppByCorpIdAndAppId(corpId, appId);
            MessageVO messageVO = MessageConverter.json2MessageVO(corpId, corpApp.getAgentId(), json);
            messageBizService.sendCorpMessageAsync(messageVO);

            Map<String, Object> map = new HashMap<>();
            map.put("errcode", 0);

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/msg/sendasynccorpmessage",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("appId", appId),
                    new LogFormatter.KeyValue("json", json)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 该接口供日事清后台调用。前端调用的接口是sendAsyncCorpMessage。日事清后台发送的格式如下：
     {
     "touser" : "UserID1|UserID2|UserID3",
     "textcard" : {
     "title" : "领奖通知",
     "description" : "<div class=\"gray\">2016年9月26日</div> <div class=\"normal\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\"highlight\">请于2016年10月10日前联系行政同事领取</div>",
     "url" : "URL",
     "btntxt":"更多"
     }
     }
     * @param request
     * @param corpId
     * @param json
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/msg/sendNotification", method = {RequestMethod.POST})
    public Map<String, Object> sendNotification(HttpServletRequest request,
                                                @RequestParam("corpid") String corpId,
                                                @RequestBody JSONObject json
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/msg/sendNotification",
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("json", json)
        ));
        try{
            JSONObject content = json.getJSONObject("textcard");
            //  针对文集、笔记、邀请类型的通知，不发送钉钉通知
            if("corpus".equals(content.getString("from"))
                    || "invite".equals(content.getString("from"))
                    || "summary".equals(content.getString("from"))){
                Map<String, Object> map = new HashMap<>();
                map.put("errcode", 0);

                return HttpResult.getSuccess(map);
            }

            final String RSQ_DEFAULT_MESSAGE_TYPE = "oa";
            AppVO appVO = appManager.getDefaultAppVO();
            CorpAppVO corpApp = corpAppManager.getCorpAppByCorpIdAndAppId(corpId, appVO.getAppId());
            MessageVO messageVO = MessageConverter.rsqJson2MessageVO(appVO, corpId, corpApp.getAgentId(), RSQ_DEFAULT_MESSAGE_TYPE, json);
            messageBizService.sendCorpMessageAsync(messageVO);

            Map<String, Object> map = new HashMap<>();
            map.put("errcode", 0);

            return HttpResult.getSuccess(map);

        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/msg/sendNotification",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("json", json)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

//    @ResponseBody
//    @RequestMapping(value = "/msg/remind", method = {RequestMethod.POST})
//    public Map<String, Object> setRemind(HttpServletRequest request,
//                                         @RequestParam("corpid") String corpId,
//                                         @RequestParam("appid") Long appId,
//                                         @RequestBody JSONObject json
//    ) {
//        bizLogger.info(LogFormatter.getKVLogData(LogFormatter.LogEvent.START,
//                LogFormatter.KeyValue.getNew("corpid", corpId),
//                LogFormatter.KeyValue.getNew("appid", appId),
//                LogFormatter.KeyValue.getNew("json", json)
//        ));
//        try{
//            String todoId = json.getString("todo_id");
//            //  定时的时间列表
//            JSONArray millsArray = json.getJSONArray("mills_array");
//            //  定时的规则列表
//            JSONArray remindArray = json.getJSONArray("remind_array");
//            String userList = json.getString("userid_list");
//            String msgType = json.getString("msgtype");
//            JSONObject jsonContent = json.getJSONObject("msgcontent");
//
//            //  暂定一次不超过5个
//            if (millsArray.size() > 5) {
//                return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
//            }
//
//            String groupKeyString = "rsq-remind-" + corpId + "-" + todoId;
//            //  先根据corpId和todoId，查出是否已经有计时任务存在，如果有，先统一删除
//            GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals("J-" + groupKeyString);
//            for(JobKey jobKey : quartzRemindScheduler.getJobKeys(matcher)) {
//                System.out.println("Found job identified by: " + jobKey);
//                quartzRemindScheduler.deleteJob(jobKey);
//            }
//            Iterator it = millsArray.iterator();
//            Iterator itRule = remindArray.iterator();
//            while (it.hasNext()) {
//                Long mills = (Long)it.next();
//                String remind = (String)itRule.next();
//                JobKey jobKey = new JobKey("J-" + mills, "J-" + groupKeyString);
//                //JobDetail currentDetail = quartzRemindScheduler.getJobDetail(jobKey);
//                //if(currentDetail != null){
//                //    quartzRemindScheduler.deleteJob(jobKey);
//                //}
//                String msgContent = MessageUtil.remindText(jsonContent, remind);
//
//                JobDetail job = JobBuilder.newJob(SendCorpMessageJob.class)
//                        .withIdentity(jobKey)
//                        .usingJobData("corpId", corpId)
//                        .usingJobData("appId", appId)
//                        .usingJobData("userIdList", userList)
//                        .usingJobData("msgType", msgType)
//                        .usingJobData("msgContent", msgContent)
//                        .build();
//
//                Trigger trigger = TriggerBuilder.newTrigger()
//                        .withIdentity("T-" + mills, "T-" + groupKeyString)
//                        .startAt(new Date(mills))
//                        .forJob(job)
//                        .build();
//                quartzRemindScheduler.scheduleJob(job, trigger);
//            }
//
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("errcode", 0);
//
//            return HttpResult.getSuccess(map);
//        }catch(Exception e){
//            bizLogger.error(LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
//                    "系统错误",
//                    LogFormatter.KeyValue.getNew("json", json),
//                    LogFormatter.KeyValue.getNew("appid", appId),
//                    LogFormatter.KeyValue.getNew("corpId", corpId)
//            ),e);
//            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
//        }
//    }
}
