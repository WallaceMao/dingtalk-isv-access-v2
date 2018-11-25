package com.rishiqing.digntalk.webjob.job;

import com.alibaba.edas.schedulerx.ProcessResult;
import com.alibaba.edas.schedulerx.ScxSimpleJobContext;
import com.alibaba.edas.schedulerx.ScxSimpleJobProcessor;
import com.rishiqing.dingtalk.biz.service.util.SuiteService;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


/**
 * 定时获取token
 * @author Wallace Mao
 * Date: 2018-11-09 19:16
 */
public class AliSuiteTokenGenerateJob implements ScxSimpleJobProcessor {
    private static final Logger bizLogger = LoggerFactory.getLogger(AliSuiteTokenGenerateJob.class);

    @Autowired
    private SuiteManageService suiteManageService;

    @Override
    public ProcessResult process(ScxSimpleJobContext context) {
        try{
            bizLogger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "AliSuiteTokenGenerateJob套件TOKEN生成任务执行开始"
            ));
            System.out.println("======hello: " + new Date() + ", " + suiteManageService.getSuite());
//            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
//            SuiteService suiteService = (SuiteService)xmlWebApplicationContext.getBean("suiteService");
//            suiteService.fetchAndSaveSuiteToken();
            return new ProcessResult(true);
        }catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "AliSuiteTokenGenerateJob任务执行异常"
            ), e);
            return new ProcessResult(false);
        }
    }
}
