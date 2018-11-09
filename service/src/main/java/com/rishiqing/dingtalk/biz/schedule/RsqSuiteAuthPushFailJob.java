package com.rishiqing.dingtalk.biz.schedule;

import com.alibaba.fastjson.JSON;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-09 20:26
 */
public class RsqSuiteAuthPushFailJob extends QuartzJobBean {
    private static final Logger bizLogger = LoggerFactory.getLogger("JOB_RSQ_SUITE_AUTH_FAIL_LOGGER");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
//            bizLogger.info("授权失败的公司检查开始", "nextFireTime", jobExecutionContext.getNextFireTime());
            //TODO  如果公司同步到日事清发生了失败，那么会进行重试

        }catch (Exception e){
            bizLogger.error("CorpSuiteAuthFailJob任务执行异常", e);
        }
    }
}
