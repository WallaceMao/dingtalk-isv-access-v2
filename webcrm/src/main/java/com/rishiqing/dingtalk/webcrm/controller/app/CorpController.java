package com.rishiqing.dingtalk.webcrm.controller.app;

import com.csvreader.CsvWriter;
import com.rishiqing.common.base.DateUtil;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.biz.http.HttpResult;
import com.rishiqing.dingtalk.biz.http.HttpResultCode;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpCountWithCreatorVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpQueryService;
import com.rishiqing.dingtalk.webcrm.util.jwt.JwtUtil;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Api(tags = "企业")
public class CorpController {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpController.class);

    @Autowired
    private CorpQueryService corpQueryService;

    /**
     * 日期转换
     * @param webDataBinder
     */
    @InitBinder
    public void Init(WebDataBinder webDataBinder){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(true);
        webDataBinder.registerCustomEditor(Date.class,new CustomDateEditor(simpleDateFormat,true));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pageList(
            HttpServletRequest request,
            @RequestParam(value = "pageSize", required = false) Long pageSize,
            @RequestParam(value = "pageNumber", required = false,defaultValue = "1") Long pageNumber,
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
        try{
            //TODO  这里需要根据权限获取用户信息.这里需要读取当前登录用户的信息，作为主叫方
            CorpStaffVO loginUser = (CorpStaffVO) request.getAttribute("loginUser");
            if (loginUser == null) {
                return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
            }
            //更新token放到响应头部
            CorpStaffVO corpStaffVO = JwtUtil.check(token);
            String newToken = JwtUtil.sign(corpStaffVO);
            response.addHeader("token",newToken);

            pageSize = pageSize == null ? 10L : pageSize;
            pageNumber = pageNumber == null ? 1L : pageNumber;
            Map<String, Object> clause = new HashMap<>();
            if (corpName != null) {
                clause.put("corpName", corpName);
            }
            if (startDate != null) {
                //System.out.println(startDate);
                clause.put("startDate", DateUtil.format(startDate));
            }
            if (endDate != null) {
                //System.out.println(endDate);
                clause.put("endDate", DateUtil.format(endDate));
            }
            List<CorpCountWithCreatorVO> dataList = corpQueryService.listPageCorpCount(
                    pageSize, (pageNumber-1L) * pageSize, clause);
            Long total = corpQueryService.getPageCorpTotal();
            Map<String, Object> map = new HashMap<>();
            /*map.put("errcode", 0);*/
            map.put("data", dataList);
            map.put("pageSize", pageSize);
            map.put("pageNumber", pageNumber);
            map.put("total", total);

            return HttpResult.getSuccess(map);
        }catch(Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/pageList",
                    LogFormatter.getKV("pageSize", pageSize),
                    LogFormatter.getKV("pageNumber", pageNumber)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }

    @RequestMapping(value = "/export",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "导出csv格式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate",value = "开始日期",required = true),
            @ApiImplicitParam(name = "endDate",value = "结束日期",required = true)
    })
    public Map<String, Object> exportCorpBetweenDate(@RequestParam(value = "startDate",required = true) Date startDate,
                                                     @RequestParam(value = "endDate",required = true) Date endDate,
                                                     HttpServletResponse response,
                                                     @RequestHeader(value = "token") String token){
        bizLogger.info(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "/export",
                LogFormatter.getKV("startDate", startDate),
                LogFormatter.getKV("endDate", endDate)
        ));
        try {
            //更新token放到响应头部
            CorpStaffVO corpStaffVO = JwtUtil.check(token);
            String newToken = JwtUtil.sign(corpStaffVO);
            response.addHeader("token",newToken);

            List<CorpCountWithCreatorVO> corpCountBetweenDate = corpQueryService.getCorpCountBetweenDate(startDate,endDate);
            //写
            //写入临时文件
            File tempFile = File.createTempFile("vehicle", ".csv");
            CsvWriter csvWriter=new CsvWriter(tempFile.getCanonicalPath(),',', Charset.forName("utf-8"));
            //写入表头
            String[] heads={"id","公司id","公司名称","创建人id","创建人名称","创建时间"};
            csvWriter.writeRecord(heads);
            //写入内容
            String[] record;
            //写内容
            for(CorpCountWithCreatorVO corpCountWithCreatorVO : corpCountBetweenDate){
                csvWriter.write(corpCountWithCreatorVO.getId().toString());
                csvWriter.write(corpCountWithCreatorVO.getCorpId().toString());
                csvWriter.write(corpCountWithCreatorVO.getCorpName().toString());
                csvWriter.write(corpCountWithCreatorVO.getCreatorUserId().toString());
                csvWriter.write(corpCountWithCreatorVO.getCreatorName().toString());
                csvWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(corpCountWithCreatorVO.getGmtCreate()));
                csvWriter.endRecord();
            }
            //关闭csvWriter流
            csvWriter.close();

            //输出
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes=new byte[1024];
            File fileLoad=new File(tempFile.getCanonicalPath());
            response.reset();
            response.setContentType("application/csv");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
            response.setHeader("content-disposition", "attachment; filename="+simpleDateFormat.format(startDate)+"_"+simpleDateFormat.format(endDate)+".csv");
            long fileLength = fileLoad.length();
            String length1 = String.valueOf(fileLength);
            response.setHeader("Content_Length", length1);
            FileInputStream in = new java.io.FileInputStream(fileLoad);
            //将要输出的内容设置BOM标识(以 EF BB BF 开头的字节流)
            outputStream.write(new byte []{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            int n;
            while ((n = in.read(bytes)) != -1) {
                outputStream.write(bytes, 0, n); //每次写入out1024字节
            }
            in.close();
            outputStream.close();
            //返回数据
            Map<String,Object> map=new HashMap<>();
            map.put("data",corpCountBetweenDate);

            return HttpResult.getSuccess(map);
        }catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "/export",
                    LogFormatter.getKV("startDate", startDate),
                    LogFormatter.getKV("endDate", endDate)
            ), e);
            return HttpResult.getFailure(HttpResultCode.SYS_ERROR.getErrCode(),HttpResultCode.SYS_ERROR.getErrMsg());
        }
    }
}
