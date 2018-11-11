package com.rishiqing.dingtalk.web.controller.suite;

import com.rishiqing.dingtalk.biz.converter.corp.CorpStaffConverter;
import com.rishiqing.dingtalk.biz.http.HttpResult;
import com.rishiqing.dingtalk.biz.http.HttpResultCode;
import com.rishiqing.dingtalk.biz.service.util.StaffService;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
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
    private static final Logger bizLogger = LoggerFactory.getLogger("CON_CORP_STAFF_LOGIN_LOGGER");
    @Autowired
    private StaffService staffService;

    @ResponseBody
    @RequestMapping(value = "/staff/authCode", method = {RequestMethod.GET})
    public Map<String, Object> sendAsyncCorpMessage(
            @RequestParam("appid") Long appId,
            @RequestParam("corpid") String corpId,
            @RequestParam("code") String code
    ) {
        bizLogger.info("corpid: " + corpId + ", appid: " + appId + ", code: " + code);
        try{

            //  请求钉钉服务器获取当前登录的staff信息
            CorpStaffVO staffVO = staffService.fetchCorpStaffByAuthCode(corpId, code);

            //  返回用户，只保留必要信息即可
            Map<String,Object> map = new HashMap<>();
            map.put("user", CorpStaffConverter.corpStaffVO2CorpStaffResultVO(staffVO));

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error("sendAsyncCorpMessage系统错误: " + ", json: " + code + ", appid: " + appId + ", corpId: " + corpId,e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
