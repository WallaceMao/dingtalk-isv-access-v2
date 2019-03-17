package com.rishiqing.dingtalk.web.webcrm.controller.app;

import com.rishiqing.common.base.DateUtil;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.svc.converter.corp.CorpConverter;
import com.rishiqing.dingtalk.svc.http.HttpResult;
import com.rishiqing.dingtalk.svc.http.HttpResultCode;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpCountWithCreatorVO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpStaffVO;
import com.rishiqing.dingtalk.api.service.biz.CorpQueryService;
import com.rishiqing.dingtalk.web.webcrm.util.io.ExportCsv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取team信息相关的controller
 *
 * @author Wallace Mao
 * Date: 2018-12-13 17:51
 */
@RequestMapping("/v3w/corp")
@Controller
@Api(tags = "企业")
public class CorpController {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpController.class);

    @Autowired
    private CorpQueryService corpQueryService;

    /**
     * 日期转换
     *
     * @param webDataBinder
     */
    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(true);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
    }

    /**
     * 分页查询企业信息
     * @param request
     * @param pageSize
     * @param pageNumber
     * @param corpName
     * @param startDate
     * @param endDate
     * @param token
     * @param response
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pageList(
            HttpServletRequest request,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Long pageSize,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Long pageNumber,
            @RequestParam(value = "corpName", required = false) String corpName,
            @RequestParam(value = "startDate", required = false) Long startDate,
            @RequestParam(value = "endDate", required = false) Long endDate,
            @RequestHeader(value = "token") String token,
            HttpServletResponse response
    ) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/pageList",
                LogFormatter.getKV("pageSize", pageSize),
                LogFormatter.getKV("pageNumber", pageNumber),
                LogFormatter.getKV("corpName", corpName),
                LogFormatter.getKV("startDate", startDate),
                LogFormatter.getKV("endDate", endDate)
        ));
        try {
            //TODO  这里需要根据权限获取用户信息.这里需要读取当前登录用户的信息，作为主叫方
            CorpStaffVO loginUser = (CorpStaffVO) request.getAttribute("loginUser");
            if (loginUser == null) {
                return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(), HttpResultCode.SYS_ERROR.getErrMsg());
            }

            pageSize = pageSize == null ? 10L : pageSize;
            pageNumber = pageNumber == null ? 1L : pageNumber;
            Map<String, Object> clause = new HashMap<>();
            if (corpName != null) {
                clause.put("corpName", corpName);
            }
            if (startDate != null) {
                clause.put("startDate", DateUtil.format(startDate));
            }
            if (endDate != null) {
                clause.put("endDate", DateUtil.format(endDate));
            }
            List<CorpCountWithCreatorVO> dataList = corpQueryService.listPageCorpCount(
                    pageSize, (pageNumber - 1L) * pageSize, clause);
            Long total = corpQueryService.getPageCorpTotal(clause);
            Map<String, Object> map = new HashMap<>();
            map.put("data", dataList);
            map.put("pageSize", pageSize);
            map.put("pageNumber", pageNumber);
            map.put("total", total);

            return HttpResult.getSuccess(map);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/pageList",
                    LogFormatter.getKV("pageSize", pageSize),
                    LogFormatter.getKV("pageNumber", pageNumber)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(), HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "导出csv格式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true,dataType = "Long",example = "1483200000000"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true,dataType = "Long",example = "1546272000000")
    })
    public Map<String, Object> exportCorpBetweenDate(@RequestParam(value = "startDate", required = true) Long startDate,
                                                     @RequestParam(value = "endDate", required = true) Long endDate,
                                                     HttpServletResponse response,
                                                     @RequestParam(value = "token", required = true) String token) {
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/export",
                LogFormatter.getKV("startDate", startDate),
                LogFormatter.getKV("endDate", endDate)
        ));
        try {
            List<CorpCountWithCreatorVO> corpCountBetweenDate = corpQueryService.listCorpBetweenDate(new Date(startDate), new Date(endDate));
            //数据写入临时文件
            File tempFile = CorpConverter.CorpCountWithCreatorVO2CsvWriter(corpCountBetweenDate);
            //临时文件置入response输出
            ExportCsv.outPutCVS(response, tempFile);
            //返回数据
            Map<String, Object> map = new HashMap<>();
            map.put("data", corpCountBetweenDate);
            return HttpResult.getSuccess(map);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/export",
                    LogFormatter.getKV("startDate", startDate),
                    LogFormatter.getKV("endDate", endDate)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(), HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
