package com.rishiqing.dingtalk.web.controller.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rishiqing.dingtalk.biz.model.GlobalSuite;
import com.rishiqing.dingtalk.biz.service.util.QueueService;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpDepartmentDao;
import com.rishiqing.dingtalk.dao.model.corp.CorpDepartmentDO;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionManager;
import com.rishiqing.dingtalk.isv.api.enumtype.SyncActionType;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpDepartmentManageService;
import com.rishiqing.dingtalk.isv.api.service.base.dingpush.OpenSyncBizDataManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.SuiteDbCheckBizService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/manual")
public class DemoController {
    private static final Logger consoleLogger = LoggerFactory.getLogger("CONSOLE_LOGGER");
    private static final Logger aliLogger = LoggerFactory.getLogger("ALIYUN_LOGHUB_LOGGER");

    @Autowired
    private OpenSyncBizDataManageService openSyncBizDataManageService;
    @Autowired
    private SyncActionManager syncActionManager;
    @Autowired
    private RsqAccountBizService rsqAccountBizService;

    @RequestMapping("/syncAuth")
    @ResponseBody
    public String syncAuth(){
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManageService.getOpenSyncBizDataListByStatus(0L);
            for(OpenSyncBizDataVO data : syncList){
                try {
                    syncActionManager.handleSyncData(data);
                    data.setStatus(1L);
                } catch (Exception e){
                    consoleLogger.error("syncAuth error: ", e);
                    data.setStatus(-1L);
                }
                openSyncBizDataManageService.updateStatus(data);
            }
            consoleLogger.info("this is consoleLogger from demoLogController=====:");
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
            return "error";
        }
    }

    @RequestMapping("/rsqPush")
    @ResponseBody
    public String rsqCorpPush(
            @RequestParam("corpId") String corpId
    ){
        try {
            rsqAccountBizService.pushCreateAll(corpId);
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in rsqCorpPush", e);
            return "error";
        }
    }

    @RequestMapping("/syncMediumAuth")
    @ResponseBody
    public String syncMediumAuth(){
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManageService.getOpenSyncBizDataMediumListByStatus(0L);
            for(OpenSyncBizDataVO data : syncList){
                try {
                    syncActionManager.handleSyncData(data);
                    data.setStatus(1L);
                } catch (Exception e){
                    consoleLogger.error("syncMediumAuth error: ", e);
                    data.setStatus(-1L);
                }
                openSyncBizDataManageService.updateMediumStatus(data);
            }
            consoleLogger.info("this is consoleLogger from syncMediumAuth=====:");
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
            return "error";
        }
    }

    @Autowired
    private CorpDepartmentManageService corpDepartmentManageService;
    @RequestMapping("/test")
    @ResponseBody
    public Map<String, Object> test(
            @RequestParam("corpId") String corpId
    ){
        CorpDepartmentVO corpDepartmentVO = corpDepartmentManageService.getTopCorpDepartment(corpId);
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
        consoleLogger.warn("consoleLogger--------------", "a--", "b--", new Date());
        return "success";
    }
}
