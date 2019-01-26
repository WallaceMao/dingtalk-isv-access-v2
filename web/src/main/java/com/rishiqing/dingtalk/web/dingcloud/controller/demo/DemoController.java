package com.rishiqing.dingtalk.web.dingcloud.controller.demo;

import com.rishiqing.dingtalk.svc.service.util.QueueService;
import com.rishiqing.dingtalk.svc.dingpush.handler.SuiteSyncActionManager;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpDepartmentVO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpDepartmentManager;
import com.rishiqing.dingtalk.mgr.dingpush.manager.OpenSyncBizDataManager;
import com.rishiqing.dingtalk.api.service.rsq.RsqAccountBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger consoleLogger = LoggerFactory.getLogger(DemoController.class);
    private static final Logger aliLogger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private OpenSyncBizDataManager openSyncBizDataManager;
    @Autowired
    private SuiteSyncActionManager suiteSyncActionManager;
    @Autowired
    private RsqAccountBizService rsqAccountBizService;

    @RequestMapping("/syncAuth")
    @ResponseBody
    public String syncAuth(){
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataListByStatus(0L);
            for(OpenSyncBizDataVO data : syncList){
                try {
                    suiteSyncActionManager.handleSyncData(data);
                    data.setStatus(1L);
                } catch (Exception e){
                    consoleLogger.error("syncAuth error: ", e);
                    data.setStatus(-1L);
                }
                openSyncBizDataManager.updateStatus(data);
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
//            rsqAccountBizService.pushCreateAll(corpId);
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
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataMediumListByStatus(0L);
            for(OpenSyncBizDataVO data : syncList){
                try {
                    suiteSyncActionManager.handleSyncData(data);
                    data.setStatus(1L);
                } catch (Exception e){
                    consoleLogger.error("syncMediumAuth error: ", e);
                    data.setStatus(-1L);
                }
                openSyncBizDataManager.updateMediumStatus(data);
            }
            consoleLogger.info("this is consoleLogger from syncMediumAuth=====:");
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in demo test", e);
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
        List<CorpDepartmentVO> corpDepartmentVO = corpDepartmentManager.listTopCorpDepartmentByScopeVersion(corpId, 0L);
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
