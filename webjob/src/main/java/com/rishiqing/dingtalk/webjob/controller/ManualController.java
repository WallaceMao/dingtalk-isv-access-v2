package com.rishiqing.dingtalk.webjob.controller;

import com.alibaba.fastjson.JSON;
import com.rishiqing.dingtalk.biz.service.util.SuiteService;
import com.rishiqing.dingtalk.isv.api.model.fail.CorpSuiteAuthFailVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 20:56
 */
@Controller
@RequestMapping("/db")
public class ManualController {
    private static final Logger consoleLogger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    @Autowired
    private SuiteService suiteService;
    @Autowired
    private FailBizService failBizService;
    @Autowired
    private SuiteManageService suiteManageService;
    @RequestMapping("/refreshSuiteToken")
    @ResponseBody
    public String demoLog(){
        try {
            consoleLogger.info("this is consoleLogger from demoLogController:");
//            suiteService.fetchAndSaveSuiteToken();
//            List<CorpSuiteAuthFailVO> failList = failBizService.getCorpSuiteAuthFailList();
//            return JSON.toJSONString(failList);
            SuiteVO suiteVO = suiteManageService.getSuite();
            return JSON.toJSONString(suiteVO);
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
            return "error";
        }
    }
}
