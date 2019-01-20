package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.svc.dingpush.handler.CorpSyncActionManager;
import com.rishiqing.dingtalk.svc.dingpush.handler.SuiteSyncActionManager;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenGlobalLockVO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.api.service.util.OpenGlobalLockService;
import com.rishiqing.dingtalk.api.service.biz.SuiteDbCheckBizService;
import com.rishiqing.dingtalk.mgr.dingpush.manager.OpenSyncBizDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:27
 */
public class SuiteDbCheckBizServiceImpl implements SuiteDbCheckBizService {
    private static final Logger bizLogger = LoggerFactory.getLogger(SuiteDbCheckBizServiceImpl.class);

    private static final String DB_CHECK_LOCK_KEY = "auth_check";
    private static final String DB_CHECK_MEDIUM_LOCK_KEY = "auth_medium_check";
    @Autowired
    private OpenGlobalLockService openGlobalLockService;
    @Autowired
    private OpenSyncBizDataManager openSyncBizDataManager;
    @Autowired
    private SuiteSyncActionManager suiteSyncActionManager;
    @Autowired
    private CorpSyncActionManager corpSyncActionManager;

    @Override
    public void checkDingPushEvent(){
        OpenGlobalLockVO lock = openGlobalLockService.requireOpenGlobalLock(DB_CHECK_LOCK_KEY);
        // System.out.println(">>>>>>>>>>>>>>>>>" + lock);
        if(lock == null){
            return;
        }
        try{
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataListByStatus(0L);
            for(OpenSyncBizDataVO data : syncList){
                try {
                    suiteSyncActionManager.handleSyncData(data);
                    data.setStatus(1L);
                } catch (Exception e){
                    bizLogger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "handleSyncData处理钉钉云重要推送消息失败",
                            new LogFormatter.KeyValue("data", data)
                    ), e);
                    data.setStatus(-1L);
                }
                openSyncBizDataManager.updateStatus(data);
            }
        }catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "checkDingPushEvent error"
            ), e);
        }finally {
            openGlobalLockService.releaseOpenGlobalLock(DB_CHECK_LOCK_KEY);
        }
    }

    @Override
    public void checkDingMediumPushEvent() {
        OpenGlobalLockVO lock = openGlobalLockService.requireOpenGlobalLock(DB_CHECK_MEDIUM_LOCK_KEY);
        // System.out.println(">>>>>>>>>>>>>>>>>" + lock);
        if(lock == null){
            return;
        }
        try{
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataMediumListByStatus(0L);
            for(OpenSyncBizDataVO data : syncList){
                try {
                    corpSyncActionManager.handleSyncData(data);
                    data.setStatus(1L);
                } catch (Exception e){
                    bizLogger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "handleSyncData处理钉钉云普通推送消息失败",
                            new LogFormatter.KeyValue("data", data)
                    ), e);
                    data.setStatus(-1L);
                }
                openSyncBizDataManager.updateMediumStatus(data);
            }
        }catch (Exception e){
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "checkDingMediumPushEvent error"
            ), e);
        }finally {
            openGlobalLockService.releaseOpenGlobalLock(DB_CHECK_MEDIUM_LOCK_KEY);
        }
    }
}
