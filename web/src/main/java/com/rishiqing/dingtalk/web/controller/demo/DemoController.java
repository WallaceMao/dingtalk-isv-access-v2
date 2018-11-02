package com.rishiqing.dingtalk.web.controller.demo;

import com.rishiqing.dingtalk.biz.isv.model.GlobalSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:56
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    private static final Logger consoleLogger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    @Autowired
    private GlobalSuite globalSuite;
    @RequestMapping("/test")
    @ResponseBody
    public String demoLog(){
        try {
            String suiteKey = globalSuite.getSuiteKey();
            consoleLogger.info("this is consoleLogger from demoLogController:" + suiteKey);
            return suiteKey;
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
            return "error";
        }
    }
}
