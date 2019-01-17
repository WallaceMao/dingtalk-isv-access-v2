package com.rishiqing.dingtalk.web.dingcallbackbizlistener.controller;

import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.mgr.dingpush.manager.OpenSyncBizDataManager;
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
@RequestMapping("/manual")
public class DemoController {
    private static final Logger consoleLogger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    private OpenSyncBizDataManager openSyncBizDataManager;

    @RequestMapping("/open")
    @ResponseBody
    public String openData(){
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataListByStatus(0L);
            consoleLogger.info("this is consoleLogger from demoLogController=====:" + syncList);
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
            return "error";
        }
    }
}
