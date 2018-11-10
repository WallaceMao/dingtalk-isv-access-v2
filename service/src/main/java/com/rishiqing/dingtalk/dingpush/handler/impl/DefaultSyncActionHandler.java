package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import org.slf4j.LoggerFactory;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:58
 */
public class DefaultSyncActionHandler implements SyncActionHandler {
    private static final org.slf4j.Logger consoleLogger = LoggerFactory.getLogger("CONSOLE_LOGGER");
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        consoleLogger.error("--------------handler没有实现，default handler将什么也不做！");
    }
}
