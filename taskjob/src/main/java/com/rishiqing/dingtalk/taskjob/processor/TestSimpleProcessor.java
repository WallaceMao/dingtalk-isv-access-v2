package com.rishiqing.dingtalk.taskjob.processor;

import com.alibaba.edas.schedulerx.ProcessResult;
import com.alibaba.edas.schedulerx.ScxSimpleJobContext;
import com.alibaba.edas.schedulerx.ScxSimpleJobProcessor;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-26 0:30
 */
public class TestSimpleProcessor implements ScxSimpleJobProcessor {
    @Override
    public ProcessResult process(ScxSimpleJobContext scxSimpleJobContext) {
        try {
            // 每次执行的时候创建“/tmp/simpleJob_<执行时刻>”文件
            System.out.println("------TestSimpleProcessor: " + new Date());
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ProcessResult(false);
        }
        return new ProcessResult(true);
    }
}
