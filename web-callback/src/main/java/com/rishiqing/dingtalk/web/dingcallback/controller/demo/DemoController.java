package com.rishiqing.dingtalk.web.dingcallback.controller.demo;

import com.google.common.eventbus.EventBus;
import com.rishiqing.dingtalk.api.event.CorpOrgChangedEvent;
import com.rishiqing.dingtalk.api.event.CorpOrgCreatedEvent;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.svc.service.util.QueueService;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:56
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    private static final Logger consoleLogger = LoggerFactory.getLogger(DemoController.class);
    private static final Logger aliLogger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private EventBus asyncCorpOrgCreatedEventBus;
    @Autowired
    private EventBus asyncCorpOrgChangedEventBus;

    @RequestMapping("/rsqPush")
    @ResponseBody
    public String rsqCorpPush(
            @RequestParam("corpId") String corpId
    ){
        try {
//            rsqAccountBizService.pushCreateAll(corpId);
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in rsqCorpPush", e);
            return "error";
        }
    }

    @Autowired
    private CorpDepartmentManager corpDepartmentManager;
    @RequestMapping("/test")
    @ResponseBody
    public Map<String, Object> test(
            @RequestParam("corpId") String corpId
    ){
        List<CorpDepartmentVO> corpDepartmentVO = corpDepartmentManager.listTopCorpDepartmentByScopeVersion(corpId, 0L);
        Map<String, Object> result = new HashMap<>();
        result.put("listdd", corpDepartmentVO);
        return result;
    }

    @Autowired
    private QueueService queueService;
    @RequestMapping("/test2")
    @ResponseBody
    public String test2(
            @RequestParam("corpId") String corpId,
            @RequestParam("userId") String userId,
            @RequestParam("type") String type
    ){
        if("team".equals(type)){
            queueService.sendToGenerateTeamSolution(corpId, userId);
        }else{
            queueService.sendToGenerateStaffSolution(corpId, userId);
        }
        return "success";
    }

    @RequestMapping("/test3")
    @ResponseBody
    public String test3(){
        aliLogger.warn("--------------" + new Date());
        long limitMills = 5 * 1000L;
        Date now = new Date();
        long startMills = now.getTime();
        while (true) {
            if (new Date().getTime() - startMills >= limitMills) break;
        }
        Date end = new Date();
        consoleLogger.warn("consoleLogger--------------" + end);
        return "success：" + now + "---->" + end;
    }

    @RequestMapping("/postCreated")
    @ResponseBody
    public String postCreated(){
        CorpOrgCreatedEvent corpOrgCreatedEvent = new CorpOrgCreatedEvent();
        corpOrgCreatedEvent.setSuiteKey("aaaa");
        corpOrgCreatedEvent.setCorpId("bbbb");
        corpOrgCreatedEvent.setScopeVersion(9999L);
        asyncCorpOrgCreatedEventBus.post(corpOrgCreatedEvent);
        return "success：" + new Date();
    }

    @RequestMapping("/postChanged")
    @ResponseBody
    public String postChanged(){
        CorpOrgChangedEvent corpOrgChangedEvent = new CorpOrgChangedEvent();
        corpOrgChangedEvent.setSuiteKey("xxxxx");
        corpOrgChangedEvent.setCorpId("yyyyy");
        corpOrgChangedEvent.setScopeVersion(99L);
        asyncCorpOrgChangedEventBus.post(corpOrgChangedEvent);
        return "success：" + new Date();
    }
}
