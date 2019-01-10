package com.rishiqing.dingtalk.web.controller.demo;

import com.rishiqing.dingtalk.biz.service.util.QueueService;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.manager.corp.CorpDepartmentManager;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:56
 */
@Controller
@RequestMapping("/manual")
public class DemoController {
    private static final Logger consoleLogger = LoggerFactory.getLogger(DemoController.class);
    private static final Logger aliLogger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private RsqAccountBizService rsqAccountBizService;

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
        CorpDepartmentVO corpDepartmentVO = corpDepartmentManager.getTopCorpDepartmentByScopeVersion(corpId, 0L);
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
}
