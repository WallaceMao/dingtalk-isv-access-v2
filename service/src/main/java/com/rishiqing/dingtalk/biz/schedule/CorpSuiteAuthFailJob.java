package com.rishiqing.dingtalk.biz.schedule;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import com.rishiqing.dingtalk.isv.api.enumtype.AuthFailType;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.fail.CorpSuiteAuthFailVO;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpBizService;
import com.rishiqing.dingtalk.isv.api.service.biz.FailBizService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.List;


/**
 * 定时获取token
 * @author Wallace Mao
 * Date: 2018-11-09 19:16
 */
public class CorpSuiteAuthFailJob extends QuartzJobBean {
    private static final Logger bizLogger = LoggerFactory.getLogger(CorpSuiteAuthFailJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            bizLogger.info(LogFormatter.format(
                    LogFormatter.LogEvent.START,
                    "CorpSuiteAuthFailJob授权失败的公司检查开始",
                    new LogFormatter.KeyValue("nextFireTime", jobExecutionContext.getNextFireTime())
            ));
            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
            FailBizService failBizService = (FailBizService) xmlWebApplicationContext.getBean("failBizService");
            CorpBizService corpBizService = (CorpBizService) xmlWebApplicationContext.getBean("corpBizService");

            List<CorpSuiteAuthFailVO> failList = failBizService.getCorpSuiteAuthFailList();

            for(CorpSuiteAuthFailVO fail : failList){
                failBizService.deleteCorpSuiteAuthFailById(fail.getId());

                //如果是在激活为应用的阶段失败了.进行如下重试策略
                if(AuthFailType.ACTIVE_CORP_APP_FAILE.equals(fail.getAuthFailType())){
                    CorpAuthInfoVO corpSuiteAuthVO;
                    if(fail.getFailInfo() == null){
                        //  如果failInfo为空，那么只传corpId，在activeCorpApp中再获取authInfo等信息
                        corpSuiteAuthVO = new CorpAuthInfoVO();
                        CorpAuthInfoVO.AuthCorpInfo corpInfo = new CorpAuthInfoVO.AuthCorpInfo();
                        corpInfo.setCorpId(fail.getCorpId());
                        corpSuiteAuthVO.setAuthCorpInfo(corpInfo);
                    }else{
                        //  如果failInfo不为空，那么从failInfo中解析出auth的授权json对象
                        corpSuiteAuthVO = SuiteDbCheckConverter.json2CorpSuiteAuthInfo(
                                JSONObject.parseObject(fail.getFailInfo())
                        );
                    }
                    corpBizService.activateCorpApp(corpSuiteAuthVO);
                }
            }
        }catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "CorpSuiteAuthFailJob任务执行异常"
            ), e);
        }
    }
}
