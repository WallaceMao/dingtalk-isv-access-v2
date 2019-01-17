package com.rishiqing.dingtalk.web.webcrm.controller.app;

import com.rishiqing.dingtalk.req.dingtalk.auth.http.SuiteRequestHelper;
import com.rishiqing.dingtalk.svc.converter.suite.CorpSuiteAuthConverter;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpDO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.api.model.vo.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.CorpSuiteAuthManager;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
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
    private CorpManager corpManager;
    @Autowired
    private SuiteManager suiteManager;
    @Autowired
    private CorpSuiteAuthManager corpSuiteAuthManager;

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
            length = Math.min(length, 100L);
            for (long i = startId; i < startId + length; i++) {
                CorpDO corpDO = corpManager.getCorpById(i);
                if (corpDO == null) {
                    bizLogger.warn("id not found: " + i);
                    continue;
                }
                try {
                    SuiteVO suiteVO = suiteManager.getSuite();
                    SuiteTicketVO suiteTicketVO = suiteManager.getSuiteTicket();
                    CorpAuthInfoVO corpAuthInfoVO = suiteRequestHelper.getCorpAuthInfo(suiteVO, suiteTicketVO, corpDO.getCorpId());
                    CorpSuiteAuthVO dbAuthVO = corpSuiteAuthManager.getCorpSuiteAuth(corpDO.getCorpId());
                    if (dbAuthVO != null && dbAuthVO.getAuthUserId() != null) {
                        continue;
                    }
                    if(dbAuthVO == null){
                        dbAuthVO = CorpSuiteAuthConverter.corpAuthInfoVO2CorpSuiteAuthVO(suiteVO.getSuiteKey(), corpAuthInfoVO);
                    }
                    dbAuthVO.setAuthUserId(corpAuthInfoVO.getAuthUserInfo().getUserId());
                    corpSuiteAuthManager.saveOrUpdateCorpSuiteAuth(dbAuthVO);
                } catch (Exception e) {
                    bizLogger.error("getCorpAuthInfo error corp id is " + i);
                }
            }
            return "success";
        } catch (Exception e) {
            bizLogger.error("getBatchCorpAuthInfo error", e);
            return "fail";
        }
    }
}
