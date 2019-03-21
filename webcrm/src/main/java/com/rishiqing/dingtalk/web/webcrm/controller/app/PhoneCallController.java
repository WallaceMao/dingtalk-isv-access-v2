package com.rishiqing.dingtalk.web.webcrm.controller.app;

import com.rishiqing.dingtalk.svc.http.HttpResult;
import com.rishiqing.dingtalk.svc.http.HttpResultCode;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;
import com.rishiqing.dingtalk.api.service.biz.PhoneCallBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 主叫方发起免费电话给授权企业下的授权范围内的人员等相关接口
 *
 * @author Wallace Mao
 * Date: 2018-12-13 17:50
 * @link https://open-doc.dingtalk.com/docs/api.htm?spm=a219a.7395905.0.0.36224a97i7LvL2&apiId=29003#
 */
@RequestMapping(value = {"/v3w/isv", "/crm/v3w/isv"})
@Controller
public class PhoneCallController {
    private static final Logger bizLogger = LoggerFactory.getLogger(PhoneCallController.class);

    @Autowired
    private PhoneCallBizService phoneCallBizService;

    /**
     * 只能给公司中开通微应用的管理员打电话，因此只需要传入corpId的参数即可
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "/callActivateAdmin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> callActivateAdmin(
            HttpServletRequest request,
            @RequestParam("corpId") String corpId
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/callActivateAdmin",
                new LogFormatter.KeyValue("corpId", corpId)
        ));
        try{
            //这里需要根据权限获取用户信息.这里需要读取当前登录用户的信息，作为主叫方
            CorpStaffVO loginUser = (CorpStaffVO) request.getAttribute("loginUser");
            String result = phoneCallBizService.callActivateAdmin(corpId, loginUser.getUserId());
            Map<String, Object> map = new HashMap<>();
            map.put("result", result);

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/callActivateAdmin",
                    new LogFormatter.KeyValue("corpId", corpId)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @RequestMapping(value = "/calleeList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setCalleeList(
            HttpServletRequest request,
            @RequestParam("calleeList") String calleeList
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/setCalleeList",
                new LogFormatter.KeyValue("calleeList", calleeList)
        ));
        try{
            CorpStaffVO loginUser = (CorpStaffVO) request.getAttribute("loginUser");
            if (loginUser == null) {
                return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
            }
            phoneCallBizService.setCalleeList(calleeList);
            Map<String, Object> map = new HashMap<>();
            map.put("errcode", 0);

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/setCalleeList",
                    new LogFormatter.KeyValue("calleeList", calleeList)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @RequestMapping(value = "/calleeList", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> removeCalleeList(
            HttpServletRequest request,
            @RequestParam("calleeList") String calleeList
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/removeCalleeList",
                new LogFormatter.KeyValue("calleeList", calleeList)
        ));
        try{
            CorpStaffVO loginUser = (CorpStaffVO) request.getAttribute("loginUser");
            if (loginUser == null) {
                return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
            }
            phoneCallBizService.removeCalleeList(calleeList);
            Map<String, Object> map = new HashMap<>();
            map.put("errcode", 0);

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/removeCalleeList",
                    new LogFormatter.KeyValue("calleeList", calleeList)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
