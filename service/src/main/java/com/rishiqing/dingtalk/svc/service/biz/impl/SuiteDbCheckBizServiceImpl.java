package com.rishiqing.dingtalk.svc.service.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.log.LogFormatter;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenGlobalLockVO;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.api.service.biz.SuiteDbCheckBizService;
import com.rishiqing.dingtalk.api.service.util.OpenGlobalLockService;
import com.rishiqing.dingtalk.mgr.dingpush.manager.OpenSyncBizDataManager;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.CorpRequestHelper;
import com.rishiqing.dingtalk.req.dingtalk.auth.http.impl.CorpTopRequestHelper;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.svc.dingpush.handler.CorpSyncActionManager;
import com.rishiqing.dingtalk.svc.dingpush.handler.SuiteSyncActionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:27
 */
public class SuiteDbCheckBizServiceImpl implements SuiteDbCheckBizService {
    private static final Logger bizLogger = LoggerFactory.getLogger(SuiteDbCheckBizServiceImpl.class);

    private static final String DB_CHECK_LOCK_KEY = "auth_check";
    private static final String DB_CHECK_MEDIUM_LOCK_KEY = "auth_medium_check";
    private static final String ORG_SUITE_AUTH="org_suite_auth";
    @Autowired
    private OpenGlobalLockService openGlobalLockService;
    @Autowired
    private OpenSyncBizDataManager openSyncBizDataManager;
    @Autowired
    private SuiteSyncActionManager suiteSyncActionManager;
    @Autowired
    private CorpSyncActionManager corpSyncActionManager;
    @Autowired
    private CorpRequestHelper corpRequestHelper;
    /**
     * 新增如下:
     * 功能：人数限制
     * 场景：用户授权开通微应用的时候
     * 逻辑：调用获取公司人数的接口，如果超过人数限制，不再进行同步，标记这个公司的人数过大，标记钉钉云push（open_sync_biz_data）的状态为-2；
     * 在isv_corp_sync_filter (id,  gmt_create, gmt_modified, corp_id, count, status )
     */
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
                    //获得同步行为
                    JSONObject json = JSONObject.parseObject(data.getBizData());
                    String syncAction = SuiteDbCheckConverter.json2SyncActionString(json);
                    //如果同步行为是org_suite_auth
                    if(ORG_SUITE_AUTH.equals(syncAction)){
                        //调用获取公司人数
                        //corpRequestHelper.getCorpDepartmentStaffByPage();
                    }
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

    public static void main(String[] args) {
        String token="a0abe76791d73cc0a73369c0c0bd43c5";
        String corpId="ding8efdc8bec7b944ac35c2f4657eb6378f";
        Long deptId=1L;
        Long offset=1L;
        Long size=10L;
        CorpTopRequestHelper corpTopRequestHelper=new CorpTopRequestHelper();
        Map<String, Object> corpDepartmentStaffByPage = corpTopRequestHelper.getCorpDepartmentStaffByPage(token, corpId, deptId, offset, size);
        System.out.println(corpDepartmentStaffByPage);
    }
}
