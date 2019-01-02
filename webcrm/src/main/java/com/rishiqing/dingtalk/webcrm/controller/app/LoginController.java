package com.rishiqing.dingtalk.webcrm.controller.app;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.biz.http.HttpResult;
import com.rishiqing.dingtalk.biz.http.HttpResultCode;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.manager.corp.CorpStaffManager;
import com.rishiqing.dingtalk.webcrm.util.jwt.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 17:44
 */
@RequestMapping("/auth")
@Controller
@Api(tags = "登陆")
public class LoginController {
    private static final Logger bizLogger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CorpStaffManager corpStaffManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登陆方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "corpId", value = "公司id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = false, dataType = "String")
    })
    public Map<String, Object> login(
            HttpServletResponse response,
            @RequestParam("corpId") String corpId,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/login",
                LogFormatter.getKV("corpId", corpId),
                LogFormatter.getKV("username", username),
                LogFormatter.getKV("password", password)
        ));
        try{
            CorpStaffVO staffVO = corpStaffManager.getCorpStaffByCorpIdAndUserId(corpId, username);
            if (staffVO == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(), HttpResultCode.SYS_ERROR.getErrMsg());
            }
            //token放到头部
            String token = JwtUtil.sign(staffVO);
            response.addHeader("token",token);

            Map<String, Object> map = new HashMap<>();
            /*map.put("errcode", 0);*/
            return HttpResult.getSuccess(null);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/login",
                    LogFormatter.getKV("corpId", corpId),
                    LogFormatter.getKV("username", username),
                    LogFormatter.getKV("password", password)
            ), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
