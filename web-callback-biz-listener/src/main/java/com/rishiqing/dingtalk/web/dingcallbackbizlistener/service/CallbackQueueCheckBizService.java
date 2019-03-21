package com.rishiqing.dingtalk.web.dingcallbackbizlistener.service;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.svc.constant.SystemConstant;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;
import com.rishiqing.dingtalk.api.model.domain.dingpush.OpenSyncBizDataDO;
import com.rishiqing.dingtalk.api.model.vo.suite.AuthGlobalLockVO;
import com.rishiqing.dingtalk.api.service.util.AuthGlobalLockService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.corp.CorpCallbackQueueManager;
import com.rishiqing.dingtalk.mgr.dingpush.manager.OpenSyncBizDataManager;
import com.rishiqing.dingtalk.web.dingcallbackbizlistener.push.Callback2DingPushDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2019-01-15 23:52
 */
public class CallbackQueueCheckBizService {
    private static final Logger logger = LoggerFactory.getLogger(CallbackQueueCheckBizService.class);

    private static final Long CORP_CALLBACK_QUEUE_LIMIT = 10L;
    private static final String CALLBACK_CHECK_LOCK_KEY = "callback_check_lock";

    @Autowired
    private AuthGlobalLockService authGlobalLockService;
    @Autowired
    private CorpCallbackQueueManager corpCallbackQueueManager;
    @Autowired
    private Callback2DingPushDataManager callback2DingPushDataManager;
    @Autowired
    private OpenSyncBizDataManager openSyncBizDataManager;

    /**
     * 检查isv_corp_callback_queue中是否有需要处理的callback事件
     */
    public void checkCallbackQueue() {
        AuthGlobalLockVO lock = authGlobalLockService.requireAuthGlobalLock(CALLBACK_CHECK_LOCK_KEY);
        // System.out.println(">>>>>>>>>>>>>>>>>" + lock);
        if (lock == null) {
            return;
        }
        try {
            List<CorpCallbackQueueDO> queue = corpCallbackQueueManager.listCorpCallbackQueueByStatusOrderByTimestampWithLimit(
                    SystemConstant.CORP_CALLBACK_STATUS_DEFAULT, CORP_CALLBACK_QUEUE_LIMIT);
            for (CorpCallbackQueueDO corpCallbackDO : queue) {
                try {
                    List<OpenSyncBizDataDO> bizDataList = callback2DingPushDataManager.convert(corpCallbackDO);
                    for (OpenSyncBizDataDO openSyncBizDataDO : bizDataList) {
                        openSyncBizDataManager.saveOrUpdateOpenSyncBizDataMedium(openSyncBizDataDO);
                    }
                    corpCallbackDO.setStatus(1L);
                } catch (Exception e) {
                    logger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "处理corpCallbackDO failed",
                            new LogFormatter.KeyValue("data", corpCallbackDO)
                    ), e);
                    corpCallbackDO.setStatus(-1L);
                }
                corpCallbackQueueManager.updateCorpCallbackQueue(corpCallbackDO);
            }
        } catch (Exception e) {
            logger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "checkCallbackQueue error"
            ), e);
        } finally {
            authGlobalLockService.releaseAuthGlobalLock(CALLBACK_CHECK_LOCK_KEY);
        }

    }
}
