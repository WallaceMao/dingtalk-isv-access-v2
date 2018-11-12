package com.rishiqing.dingtalk.webjob.controller;

import com.rishiqing.dingtalk.biz.service.util.QueueService;
import com.rishiqing.dingtalk.biz.service.util.SuiteService;
import com.workbei.service.SolutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping("/refreshSuiteToken")
    @ResponseBody
    public String demoLog(){
        try {
            consoleLogger.info("this is consoleLogger from demoLogController:");
            suiteService.fetchAndSaveSuiteToken();
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
            return "error";
        }
    }

    @Autowired
    private SolutionService solutionService;
    @RequestMapping("/generateSolution")
    @ResponseBody
    public String generateSolution(
            @RequestParam("teamId") String teamId,
            @RequestParam("userId") String userId,
            @RequestParam("type") String type
    ){
        try {
            if("team".equals(type)){
                solutionService.generateTeamSolution(teamId, userId);
            }else if("staff".equals(type)){
                solutionService.generateUserSolution(teamId, userId);
            }else{
                return "no type";
            }
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
            return "error";
        }
    }

    @Autowired
    private QueueService queueService;
    @RequestMapping("/generateSolutionMq")
    @ResponseBody
    public String generateSolutionMq(
            @RequestParam("corpId") String corpId,
            @RequestParam("staffId") String staffId,
            @RequestParam("type") String type
    ){
        try {
            if("team".equals(type)){
                queueService.sendToGenerateTeamSolution(corpId, staffId);
            }else if("staff".equals(type)){
                queueService.sendToGenerateStaffSolution(corpId, staffId);
            }else{
                return "no type";
            }
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
            return "error";
        }
    }
}
