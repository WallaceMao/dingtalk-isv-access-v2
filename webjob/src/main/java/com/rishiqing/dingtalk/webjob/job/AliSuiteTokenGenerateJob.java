package com.rishiqing.dingtalk.webjob.job;

import com.alibaba.edas.schedulerx.ProcessResult;
import com.alibaba.edas.schedulerx.ScxSimpleJobContext;
import com.alibaba.edas.schedulerx.ScxSimpleJobProcessor;
import com.rishiqing.dingtalk.biz.service.biz.impl.SuiteService;
import com.rishiqing.common.log.LogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 定时获取token
 *
 * @author Wallace Mao
 * Date: 2018-11-09 19:16
 */
public class AliSuiteTokenGenerateJob implements ScxSimpleJobProcessor {
    private static final Logger bizLogger = LoggerFactory.getLogger(AliSuiteTokenGenerateJob.class);

    @Autowired
    private SuiteService suiteService;

    @Override
    public ProcessResult process(ScxSimpleJobContext context) {
        try {
            bizLogger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "AliSuiteTokenGenerateJob套件TOKEN生成任务执行开始"
            ));
            suiteService.fetchAndSaveSuiteToken();
            return new ProcessResult(true);
        } catch (Exception e) {
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "AliSuiteTokenGenerateJob任务执行异常"
            ), e);
            return new ProcessResult(false);
        }
    }
}
