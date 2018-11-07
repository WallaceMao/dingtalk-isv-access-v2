package com.rishiqing.dingtalk.biz.service.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.isv.api.enumtype.SuiteSyncActionType;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenGlobalLockVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.service.base.dingpush.OpenSyncBizDataManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.CorpSuiteAuthManageService;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpBizService;
import com.rishiqing.dingtalk.isv.api.service.biz.OpenGlobalLockBizService;
import com.rishiqing.dingtalk.isv.api.service.biz.SuiteDbCheckBizService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-06 21:27
 */
public class SuiteDbCheckBizServiceImpl implements SuiteDbCheckBizService {
    private static final String DB_CHECK_LOCK_KEY = "auth_check";
    @Autowired
    private OpenGlobalLockBizService openGlobalLockBizService;
    @Autowired
    private OpenSyncBizDataManageService openSyncBizDataManageService;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private CorpBizService corpBizService;
    @Autowired
    private CorpSuiteAuthManageService corpSuiteAuthManageService;
    @Autowired
    private AsyncEventBus asyncCorpSuiteAuthEventBus;

    @Override
    public void checkDingPushEvent(){
        OpenGlobalLockVO lock = openGlobalLockBizService.requireOpenGlobalLock(DB_CHECK_LOCK_KEY);
        System.out.println(">>>>>>>>>>>>>>>>>" + lock);
        if(lock == null){
            return;
        }
        try{
            List<OpenSyncBizDataVO> syncList = openSyncBizDataManageService.getOpenSyncBizDataListByStatus(0L);
            for(OpenSyncBizDataVO data : syncList){
                handleSyncData(data);
                data.setStatus(1L);
                openSyncBizDataManageService.updateStatus(data);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            openGlobalLockBizService.releaseOpenGlobalLock(DB_CHECK_LOCK_KEY);
        }
    }

    @Override
    public void handleSyncData(OpenSyncBizDataVO data){
        JSONObject json = JSONObject.parseObject(data.getBizData());
        SuiteSyncActionType type = SuiteDbCheckConverter.json2SuiteSyncActionType(json);
        switch (type){
            case SUITE_TICKET:
                suiteManageService.saveOrUpdateSuiteTicket(
                        SuiteDbCheckConverter.json2SuiteTicket(json)
                );
                break;
            case ORG_SUITE_AUTH:
                corpBizService.activateCorpApp(
                        SuiteDbCheckConverter.json2CorpSuiteAuthInfo(json)
                );
                break;
            default:
                throw new RuntimeException("===unhandled syncAction: " + type);
        }
    }
}
