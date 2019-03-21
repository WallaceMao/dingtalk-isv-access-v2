package com.rishiqing.dingtalk.web.dingcloud.controller.suite;

import com.rishiqing.dingtalk.svc.converter.corp.CorpStaffConverter;
import com.rishiqing.dingtalk.svc.http.HttpResult;
import com.rishiqing.dingtalk.svc.http.HttpResultCode;
import com.rishiqing.dingtalk.svc.service.biz.impl.StaffService;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpStaffManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方免登获取用户信息，用来对接前端jssdk
 * @link https://open-doc.dingtalk.com/microapp/serverapi2/mox50d
 * @author Wallace Mao
 * Date: 2018-11-11 19:55
 */
@Controller
public class CorpStaffLoginController {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpStaffLoginController.class);
    @Autowired
    private StaffService staffService;
    @Autowired
    private CorpStaffManager corpStaffManager;

    /**
     * 根据authCode获取用户信息
     * @param appId
     * @param corpId
     * @param code
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/staff/authCode", method = {RequestMethod.GET})
    public Map<String, Object> getStaffByAuthCode(
            @RequestParam("appid") Long appId,
            @RequestParam("corpid") String corpId,
            @RequestParam("code") String code
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/staff/authCode",
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("appId", appId),
                new LogFormatter.KeyValue("code", code)
        ));
        try{

            //  请求钉钉服务器获取当前登录的staff信息
            CorpStaffVO staffVO = staffService.fetchCorpStaffByAuthCode(corpId, code);

            //  返回用户，只保留必要信息即可
            Map<String,Object> map = new HashMap<>();
            map.put("user", CorpStaffConverter.corpStaffVO2CorpStaffResultVO(staffVO));

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/staff/authCode",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("appId", appId),
                    new LogFormatter.KeyValue("code", code)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    /**
     * 根据userId获取用户信息
     * TODO 注意以后需要做安全验证
     * @param appId
     * @param corpId
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/staff/userId", method = {RequestMethod.GET})
    public Map<String, Object> getStaffByUserId(
            @RequestParam("appid") Long appId,
            @RequestParam("corpid") String corpId,
            @RequestParam("userid") String userId
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/staff/userId",
                new LogFormatter.KeyValue("corpId", corpId),
                new LogFormatter.KeyValue("appId", appId),
                new LogFormatter.KeyValue("userId", userId)
        ));
        try{

            //  直接读取数据库获取用户信息
            CorpStaffVO staffVO = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, userId);

            //  返回用户，只保留必要信息即可
            Map<String,Object> map = new HashMap<>();
            map.put("user", CorpStaffConverter.corpStaffVO2CorpStaffResultVO(staffVO));

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/staff/userId",
                    new LogFormatter.KeyValue("corpId", corpId),
                    new LogFormatter.KeyValue("appId", appId),
                    new LogFormatter.KeyValue("userId", userId)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
