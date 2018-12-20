package com.rishiqing.dingtalk.webcrm.controller.app;

import com.rishiqing.dingtalk.biz.converter.suite.CorpSuiteAuthConverter;
import com.rishiqing.dingtalk.biz.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import com.rishiqing.dingtalk.dao.model.corp.CorpDO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.corp.CorpManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 21:59
 */
@RequestMapping("/manual")
@Controller
public class ManualController {
    private static final Logger bizLogger = LoggerFactory.getLogger(ManualController.class);

    @Autowired
    private SuiteRequestHelper suiteRequestHelper;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private CorpSuiteAuthManageService corpSuiteAuthManageService;

    @RequestMapping("/ping")
    @ResponseBody
    public String pong(
            @RequestParam("name") String name
    ) {
        bizLogger.info("----pong----" + name);
        return "pong " + name;
    }

    /**
     * 根据corp的id（long型）获取corp的套件授权信息
     *
     * @param startId
     * @param length
     * @return
     */
    @RequestMapping("/corpAuthInfo")
    @ResponseBody
    public String getBatchCorpAuthInfo(
            @RequestParam("startId") Long startId,
            @RequestParam("length") Long length
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "get",
                LogFormatter.getKV("startId", startId),
                LogFormatter.getKV("length", length)
        ));
        try {
            // 最大不超过50
            length = Math.min(length, 50L);
            for (long i = startId; i < startId + length; i++) {
                CorpDO corpDO = corpManageService.getCorpById(i);
                if (corpDO == null) {
                    bizLogger.warn("id not found: " + i);
                    continue;
                }
                SuiteVO suiteVO = suiteManageService.getSuite();
                SuiteTicketVO suiteTicketVO = suiteManageService.getSuiteTicket();
                CorpAuthInfoVO corpAuthInfoVO = suiteRequestHelper.getCorpAuthInfo(suiteVO, suiteTicketVO, corpDO.getCorpId());
                CorpSuiteAuthVO corpSuiteAuthVO = CorpSuiteAuthConverter.corpAuthInfoVO2CorpSuiteAuthVO(
                        suiteVO.getSuiteKey(), corpAuthInfoVO);
                corpSuiteAuthManageService.saveOrUpdateCorpSuiteAuth(corpSuiteAuthVO);
            }
            return "success";
        } catch (Exception e) {
            bizLogger.error("getBatchCorpAuthInfo error", e);
            return "fail";
        }
    }
}
