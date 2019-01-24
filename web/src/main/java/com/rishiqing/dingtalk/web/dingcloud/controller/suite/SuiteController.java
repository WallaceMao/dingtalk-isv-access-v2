package com.rishiqing.dingtalk.web.dingcloud.controller.suite;

import com.rishiqing.dingtalk.svc.http.HttpResult;
import com.rishiqing.dingtalk.svc.http.HttpResultCode;
import com.rishiqing.dingtalk.svc.service.biz.impl.SuiteService;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-26 0:15
 */
@Controller
public class SuiteController {
    private static final Logger bizLogger = LoggerFactory.getLogger(SuiteController.class);

    @Autowired
    private SuiteService suiteService;
    @Autowired
    private SuiteManager suiteManager;

    @ResponseBody
    @RequestMapping(value = "/suite/token/{suiteKey}", method = {RequestMethod.POST})
    public Map<String, Object> refreshSuiteToken(
            @PathVariable("suiteKey") String passSuiteKey
    ){
        try{
            bizLogger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "/suite/token/{suiteKey}",
                    new LogFormatter.KeyValue("suiteKey", passSuiteKey)
            ));
            SuiteVO suiteVO = suiteManager.getSuite();
            if(!passSuiteKey.equals(suiteVO.getSuiteKey())){
                throw new BizRuntimeException("suiteKey not match: passSuiteKey: " + passSuiteKey + ", suiteKey: " + suiteVO.getSuiteKey());
            }
            suiteService.fetchAndSaveSuiteToken();
            return HttpResult.getSuccess(null);
        }catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/suite/token/{suiteKey}",
                    new LogFormatter.KeyValue("suiteKey", passSuiteKey)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
