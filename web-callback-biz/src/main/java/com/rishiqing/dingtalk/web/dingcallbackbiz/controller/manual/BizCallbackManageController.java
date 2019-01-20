package com.rishiqing.dingtalk.web.dingcallbackbiz.controller.manual;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CallbackRequestHelper;
import com.rishiqing.dingtalk.req.dingtalk.model.EventCallbackFailResultVO;
import com.rishiqing.dingtalk.req.dingtalk.model.EventCallbackUrlVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wallace Mao
 * Date: 2019-01-12 14:51
 */
@RestController
@RequestMapping("/bizCallbackUrl")
public class BizCallbackManageController {
    private static final Logger logger = LoggerFactory.getLogger(BizCallbackManageController.class);

    private CallbackRequestHelper callbackRequestHelper;

    @Autowired
    public BizCallbackManageController(CallbackRequestHelper callbackRequestHelper) {
        this.callbackRequestHelper = callbackRequestHelper;
    }

    @PostMapping(produces = "application/json; charset=utf-8")
    public String register(
            @RequestParam("accessToken") String accessToken,
            @RequestBody EventCallbackUrlVO params
    ){
        try {
            logger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "register",
                    LogFormatter.getKV("accessToken", accessToken),
                    LogFormatter.getKV("eventCallbackUrlVO", params)
            ));
            callbackRequestHelper.registerCallbackUrl(
                    accessToken, params.getCallbackTag(), params.getToken(), params.getAesKey(), params.getUrl());
            return "success";
        } catch (Exception e){
            logger.error(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "register",
                    LogFormatter.getKV("accessToken", accessToken),
                    LogFormatter.getKV("eventCallbackUrlVO", params)
            ));
            return "error";
        }
    }

    @GetMapping(produces = "application/json; charset=utf-8")
    public String query(
            @RequestParam("accessToken") String accessToken
    ){
        try {
            logger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "query"),
                    LogFormatter.getKV("accessToken", accessToken));
            EventCallbackUrlVO result = callbackRequestHelper.queryCallbackUrl(accessToken);
            return "result: " + result;
        } catch (Exception e){
            logger.error(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "query",
                    LogFormatter.getKV("accessToken", accessToken)
            ));
            return "error";
        }
    }

    @PutMapping(produces = "application/json; charset=utf-8")
    public String update(
            @RequestParam("accessToken") String accessToken,
            @RequestBody EventCallbackUrlVO params
    ){
        try {
            logger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "update",
                    LogFormatter.getKV("accessToken", accessToken),
                    LogFormatter.getKV("eventCallbackUrlVO", params)
            ));
            callbackRequestHelper.updateCallbackUrl(
                    accessToken, params.getCallbackTag(), params.getToken(), params.getAesKey(), params.getUrl());
            return "success";
        } catch (Exception e){
            logger.error(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "update",
                    LogFormatter.getKV("accessToken", accessToken),
                    LogFormatter.getKV("eventCallbackUrlVO", params)
            ));
            return "error";
        }
    }

    @DeleteMapping(produces = "application/json; charset=utf-8")
    public String delete(
            @RequestParam("accessToken") String accessToken
    ){
        try {
            logger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "delete"),
                    LogFormatter.getKV("accessToken", accessToken));
            callbackRequestHelper.deleteCallbackUrl(accessToken);
            return "success";
        } catch (Exception e){
            logger.error(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "delete",
                    LogFormatter.getKV("accessToken", accessToken)
            ));
            return "error";
        }
    }

    @GetMapping(value = "/listFailed", produces = "application/json; charset=utf-8")
    public String listFailed(
            @RequestParam("accessToken") String accessToken
    ){
        try {
            logger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "listFailed"),
                    LogFormatter.getKV("accessToken", accessToken));
            EventCallbackFailResultVO callbackFailResultVO = callbackRequestHelper.getCallbackFailedResult(accessToken);
            return "result: " + callbackFailResultVO;
        } catch (Exception e){
            logger.error(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "listFailed",
                    LogFormatter.getKV("accessToken", accessToken)
            ));
            return "error";
        }
    }
}
