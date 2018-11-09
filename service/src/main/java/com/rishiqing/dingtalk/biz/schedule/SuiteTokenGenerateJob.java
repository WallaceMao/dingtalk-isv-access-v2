package com.rishiqing.dingtalk.biz.schedule;

import com.rishiqing.dingtalk.biz.service.util.SuiteService;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.XmlWebApplicationContext;


/**
 * 定时获取token
 * @author Wallace Mao
 * Date: 2018-11-09 19:16
 */
public class SuiteTokenGenerateJob  extends QuartzJobBean {
    private static final Logger bizLogger = LoggerFactory.getLogger("JOB_SUITE_TOKEN_LOGGER");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            bizLogger.info("套件TOKEN生成任务执行开始", "nextFireTime", jobExecutionContext.getNextFireTime());
            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
            SuiteService suiteService = (SuiteService)xmlWebApplicationContext.getBean("suiteService");
            suiteService.fetchAndSaveSuiteToken();
        }catch (Exception e){
            bizLogger.error("SuiteTokenGenerateJob任务执行异常", e);
        }
    }
}
