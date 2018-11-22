package com.rishiqing.dingtalk.biz.schedule;

import com.rishiqing.dingtalk.biz.util.LogFormatter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Wallace Mao
 * Date: 2018-11-09 20:26
 */
public class RsqCorpChangePushFailJob extends QuartzJobBean {
    private static final Logger bizLogger = LoggerFactory.getLogger("JOB_RSQ_CORP_CHANGE_FAIL_LOGGER");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
//            bizLogger.info("授权失败的公司检查开始", "nextFireTime", jobExecutionContext.getNextFireTime());
            //TODO  如果公司的组织结构变化同步到日事清发生了失败，那么也会进行重试

        }catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "CorpSuiteAuthFailJob任务执行异常"
            ), e);
        }
    }
}
