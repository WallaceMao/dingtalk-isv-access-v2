package com.rishiqing.dingtalk.web.dingcallback.controller.suite;

import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.api.service.biz.CorpBizService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.order.OrderManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import com.rishiqing.dingtalk.svc.converter.suite.CorpSuiteAuthConverter;
import com.rishiqing.dingtalk.svc.service.biz.impl.ChargeBizService;
import com.rishiqing.dingtalk.web.dingcallback.service.CorpCallbackBizService;
import net.bytebuddy.asm.Advice;
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
    private Logger consoleLogger = LoggerFactory.getLogger(ManualController.class);

    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private CorpCallbackBizService corpCallbackBizService;
    @Autowired
    private ChargeBizService chargeBizService;
    @Autowired
    private CorpBizService corpBizService;

    @RequestMapping("/updateCorpCallbackUrl")
    @ResponseBody
    public String updateCorpCallbackUrl(
            @RequestParam("suiteKey") String suiteKey,
            @RequestParam("corpId") String corpId
    ){
        try {
            if (!suiteKey.equals(suiteManager.getSuite().getSuiteKey())) {
                return "no auth";
            }
            corpCallbackBizService.saveOrUpdateCorpCallback(corpId);
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in updateCorpCallbackUrl", e);
            return "error";
        }
    }

    @RequestMapping("/doChargeFromEvent")
    @ResponseBody
    public String doChargeFromEvent(
            @RequestParam("suiteKey") String suiteKey,
            @RequestParam("corpId") String corpId
    ){
        try {
            if (!suiteKey.equals(suiteManager.getSuite().getSuiteKey())) {
                return "no auth";
            }
            OrderEventVO orderEventVO = orderManager.getOrderEventByCorpIdAndLatest(corpId);
            //  充值
            chargeBizService.charge(orderEventVO);
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in doChargeFromEvent", e);
            return "error";
        }
    }

    @RequestMapping("/syncCorp")
    @ResponseBody
    public String syncCorp(
            @RequestParam("suiteKey") String suiteKey,
            @RequestParam("corpId") String corpId
    ){
        try {
            if (!suiteKey.equals(suiteManager.getSuite().getSuiteKey())) {
                return "no auth";
            }
            CorpAuthInfoVO corpAuthInfo = CorpSuiteAuthConverter.corpId2CorpAuthInfoVO(corpId);
            corpBizService.changeCorpApp(corpAuthInfo, new Date().getTime());
            return "success";
        } catch (Exception e){
            consoleLogger.error("error in doChargeFromEvent", e);
            return "error";
        }
    }
}
