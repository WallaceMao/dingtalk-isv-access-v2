package com.rishiqing.dingtalk.svc.dingpush.handler.impl;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.svc.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:58
 */
public class DefaultSyncActionHandler implements SyncActionHandler {
    private static final Logger bizLogger = LoggerFactory.getLogger(DefaultSyncActionHandler.class);

    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        bizLogger.warn(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "--------------handler没有实现，default handler将什么也不做！",
                LogFormatter.getKV("openSyncBizData", data)));
    }
}
