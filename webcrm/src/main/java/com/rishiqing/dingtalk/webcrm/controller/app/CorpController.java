package com.rishiqing.dingtalk.webcrm.controller.app;

import com.rishiqing.dingtalk.biz.http.HttpResult;
import com.rishiqing.dingtalk.biz.http.HttpResultCode;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpCountWithCreatorVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpQueryService;
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
import java.util.List;
import java.util.Map;

/**
 * 获取team信息相关的controller
 * @author Wallace Mao
 * Date: 2018-12-13 17:51
 */
@RequestMapping("/v3w/corp")
@Controller
public class CorpController {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpController.class);

    @Autowired
    private CorpQueryService corpQueryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pageList(
            HttpServletRequest request,
            @RequestParam(value = "pageSize", required = false) Long pageSize,
            @RequestParam(value = "pageOffset", required = false) Long pageOffset
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/pageList",
                LogFormatter.getKV("pageSize", pageSize),
                LogFormatter.getKV("pageOffset", pageOffset)
        ));
        try{
            //TODO  这里需要根据权限获取用户信息.这里需要读取当前登录用户的信息，作为主叫方
            // CorpStaffVO loginUser = (CorpStaffVO) request.getAttribute("loginUser");
            pageSize = pageSize == null ? 10L : pageSize;
            pageOffset = pageOffset == null ? 0L : pageOffset;
            List<CorpCountWithCreatorVO> dataList = corpQueryService.listPageCorpCount(pageSize, pageOffset * pageSize);
            Long total = corpQueryService.getPageCorpTotal();
            Map<String, Object> map = new HashMap<>();
            map.put("errcode", 0);
            map.put("data", dataList);
            map.put("pageSize", pageSize);
            map.put("pageOffset", pageOffset);
            map.put("total", total);

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/pageList",
                    LogFormatter.getKV("pageSize", pageSize),
                    LogFormatter.getKV("pageOffset", pageOffset)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}