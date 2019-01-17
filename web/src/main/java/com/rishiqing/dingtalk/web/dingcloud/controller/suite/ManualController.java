package com.rishiqing.dingtalk.web.dingcloud.controller.suite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
