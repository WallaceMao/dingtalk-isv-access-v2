package com.rishiqing.dingtalk.web.dingcallbackbizlistener.listener;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.service.CallbackQueueCheckBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Wallace Mao
 * Date: 2018-10-31 23:54
 */
public class CallbackQueueCheckApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger bizLogger = LoggerFactory.getLogger(CallbackQueueCheckApplicationListener.class);
    private static final Long EXECUTE_INIT_DELAY_MILLS = 3000L;
    private static final Long EXECUTE_DELAY_MILLS = 10000L;
    private static boolean isStarted = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(!isStarted){
            System.out.println("====SINGLE THREAD corp callback queue check starting...");
            XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext)contextRefreshedEvent.getApplicationContext();
            final CallbackQueueCheckBizService callbackQueueCheckBizService = (CallbackQueueCheckBizService)xmlWebApplicationContext.getBean("callbackQueueCheckBizService");

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try{
                        callbackQueueCheckBizService.checkCallbackQueue();
                    } catch (Exception e){
                        bizLogger.error(LogFormatter.format(
                                LogFormatter.LogEvent.EXCEPTION,
                                "CallbackQueueCheckApplicationListener"
                        ), e);
                    }
                }
            }, EXECUTE_INIT_DELAY_MILLS, EXECUTE_DELAY_MILLS, TimeUnit.MILLISECONDS);

            System.out.println("====SINGLE THREAD corp callback queue check started");
            isStarted = true;
        }
    }
}
