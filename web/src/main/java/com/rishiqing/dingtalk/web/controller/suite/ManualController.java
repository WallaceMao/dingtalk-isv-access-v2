package com.rishiqing.dingtalk.web.controller.suite;

import com.rishiqing.dingtalk.biz.service.util.QueueService;
import com.rishiqing.dingtalk.biz.service.util.SuiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:56
 */
@Controller
@RequestMapping("/manual")
public class ManualController {
    private Logger logbackLogger = LoggerFactory.getLogger(ManualController.class);

    @RequestMapping("/testLog")
    @ResponseBody
    public String generateSolution(){
        logbackLogger.info("====logback web====: " + new Date());
        return "success";
    }
}
