package com.rishiqing.dingtalk.svc.event;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:36
 */
public class AsyncCorpSuiteAuthEventExecutorFactory {
    public Executor getExecutor(){
        //  建立默认10个线程的线程池
        return Executors.newFixedThreadPool(15);
    }
}
