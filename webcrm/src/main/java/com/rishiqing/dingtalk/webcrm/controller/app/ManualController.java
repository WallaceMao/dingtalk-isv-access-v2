package com.rishiqing.dingtalk.webcrm.controller.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 21:59
 */
@RequestMapping("/manual")
@Controller
public class ManualController {
    private static final Logger bizLogger = LoggerFactory.getLogger(ManualController.class);

    @RequestMapping("/ping")
    @ResponseBody
    public String pong(
            @RequestParam("name") String name
    ) {
        bizLogger.info("----pong----" + name);
        return "pong " + name;
    }
}
