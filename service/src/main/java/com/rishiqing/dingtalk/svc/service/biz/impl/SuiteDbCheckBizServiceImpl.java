package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.alibaba.fastjson.JSON;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.exception.CorpOverstaffedException;
import com.rishiqing.dingtalk.api.model.domain.corp.CorpSyncFilterDO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenGlobalLockVO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.api.service.biz.SuiteDbCheckBizService;
import com.rishiqing.dingtalk.api.service.util.OpenGlobalLockService;
import com.rishiqing.dingtalk.mgr.dingmain.manager.filter.FilterManager;
import com.rishiqing.dingtalk.mgr.dingpush.manager.OpenSyncBizDataManager;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.svc.dingpush.manager.CorpSyncActionManager;
import com.rishiqing.dingtalk.svc.dingpush.manager.SuiteSyncActionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
    @Qualifier("filterSuiteSyncActionManager")
    private SuiteSyncActionManager suiteSyncActionManager;
    @Autowired
    @Qualifier("filterCorpSyncActionManager")
    private CorpSyncActionManager corpSyncActionManager;
    @Autowired
    private FilterManager filterManager;

    /**
     * 新增如下:
     * 功能：人数限制
     * 场景：用户授权开通微应用的时候
     * 逻辑：调用获取公司人数的接口，如果超过人数限制，不再进行同步，标记这个公司的人数过大，标记钉钉云push（open_sync_biz_data）的状态为-2；
     * 在isv_corp_sync_filter (id,  gmt_create, gmt_modified, corp_id, count, status )
     */
    @Override
    public void checkDingPushEvent() {
        OpenGlobalLockVO lock = openGlobalLockService.requireOpenGlobalLock(DB_CHECK_LOCK_KEY);
        // System.out.println(">>>>>>>>>>>>>>>>>" + lock);
        if (lock == null) {
            return;
        }
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataListByStatus(0L);
            for (OpenSyncBizDataVO openSyncBizDataVO : syncList) {
                try {
                    suiteSyncActionManager.handleSyncData(openSyncBizDataVO);
                    openSyncBizDataVO.setStatus(1L);
                } catch (CorpOverstaffedException e) {
                    bizLogger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "handleSyncData处理钉钉云重要推送消息失败" + ":" + e.getMessage(),
                            new LogFormatter.KeyValue("data", openSyncBizDataVO)
                    ), e);
                    String corpId = openSyncBizDataVO.getCorpId();
                    String bizData = openSyncBizDataVO.getBizData();
                    String syncAction = SuiteDbCheckConverter.json2SyncActionString(JSON.parseObject(bizData));
                    CorpSyncFilterDO corpSyncFilterDO = new CorpSyncFilterDO();
                    corpSyncFilterDO.setCorpId(corpId);
                    corpSyncFilterDO.setStatus(syncAction + " above staff_count_threshold");
                    filterManager.saveOrUpdateCorpSyncFilter(corpSyncFilterDO);
                    openSyncBizDataVO.setStatus(-2L);
                } catch (Exception e) {
                    bizLogger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "handleSyncData处理钉钉云重要推送消息失败",
                            new LogFormatter.KeyValue("data", openSyncBizDataVO)
                    ), e);
                    openSyncBizDataVO.setStatus(-1L);
                }
                openSyncBizDataManager.updateStatus(openSyncBizDataVO);
            }
        } catch (Exception e) {
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "checkDingPushEvent error"
            ), e);
        } finally {
            openGlobalLockService.releaseOpenGlobalLock(DB_CHECK_LOCK_KEY);
        }
    }

    /**
     * 新增功能：限制人数
     * 用户变更通讯录的时候：通讯录回调（open_sync_biz_data_medium）的时候，当查到这个公司属于“人数过大”时，标记钉钉云push的状态为-2
     */
    @Override
    public void checkDingMediumPushEvent() {
        OpenGlobalLockVO lock = openGlobalLockService.requireOpenGlobalLock(DB_CHECK_MEDIUM_LOCK_KEY);
        // System.out.println(">>>>>>>>>>>>>>>>>" + lock);
        if (lock == null) {
            return;
        }
        try {
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManager.getOpenSyncBizDataMediumListByStatus(0L);
            for (OpenSyncBizDataVO openSyncBizDataVO : syncList) {
                try {
                    //员工添加时做特殊处理
                    corpSyncActionManager.handleSyncData(openSyncBizDataVO);
                    openSyncBizDataVO.setStatus(1L);
                } catch (CorpOverstaffedException e) {
                    bizLogger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "handleSyncData处理钉钉云普通推送消息失败：" + e.getMessage(),
                            new LogFormatter.KeyValue("data", openSyncBizDataVO)
                    ), e);
                    String corpId = openSyncBizDataVO.getCorpId();
                    String bizData = openSyncBizDataVO.getBizData();
                    String syncAction = SuiteDbCheckConverter.json2SyncActionString(JSON.parseObject(bizData));
                    CorpSyncFilterDO corpSyncFilterDO = new CorpSyncFilterDO();
                    corpSyncFilterDO.setCorpId(corpId);
                    corpSyncFilterDO.setStatus(syncAction + " above staff_count_threshold");
                    filterManager.saveOrUpdateCorpSyncFilter(corpSyncFilterDO);
                    openSyncBizDataVO.setStatus(-2L);
                } catch (Exception e) {
                    bizLogger.error(LogFormatter.format(
                            LogFormatter.LogEvent.EXCEPTION,
                            "handleSyncData处理钉钉云普通推送消息失败",
                            new LogFormatter.KeyValue("data", openSyncBizDataVO)
                    ), e);
                    openSyncBizDataVO.setStatus(-1L);
                }
                openSyncBizDataManager.updateMediumStatus(openSyncBizDataVO);
            }
        } catch (Exception e) {
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "checkDingMediumPushEvent error"
            ), e);
        } finally {
            openGlobalLockService.releaseOpenGlobalLock(DB_CHECK_MEDIUM_LOCK_KEY);
        }
    }
}
