package com.rishiqing.dingtalk.web.controller.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rishiqing.dingtalk.biz.model.GlobalSuite;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionManager;
import com.rishiqing.dingtalk.isv.api.enumtype.SyncActionType;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.service.base.dingpush.OpenSyncBizDataManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.SuiteDbCheckBizService;
import com.rishiqing.self.api.service.RsqAccountBizService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private OpenSyncBizDataManageService openSyncBizDataManageService;
    @Autowired
    private SyncActionManager syncActionManager;
    @Autowired
    private RsqAccountBizService rsqAccountBizService;

    @RequestMapping("/dbCheck")
    @ResponseBody
    public String demoLog(
            @RequestParam("corpId") String corpId
    ){
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManageService.getOpenSyncBizDataListByStatus(0L);
            for(OpenSyncBizDataVO data : syncList){
                try {
                    syncActionManager.handleSyncData(data);
                    data.setStatus(1L);
                } catch (Exception e){
                    consoleLogger.error("handleSyncData error: ", e);
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
}
