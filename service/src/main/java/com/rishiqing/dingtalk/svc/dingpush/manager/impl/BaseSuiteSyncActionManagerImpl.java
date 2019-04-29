package com.rishiqing.dingtalk.svc.dingpush.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.exception.BizRuntimeException;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.svc.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.svc.dingpush.manager.SuiteSyncActionManager;

import java.util.Map;

/**
 * syncActionMap中记录了syncAction对应的handler的映射关系。
 * handleSyncData方法，负责将指定syncAction的数据指派给相应的handler解决
 * @author Wallace Mao
 * Date: 2018-11-10 13:56
 */
public class BaseSuiteSyncActionManagerImpl implements SuiteSyncActionManager {
    private Map<String, SyncActionHandler> syncActionMap;

    public Map<String, SyncActionHandler> getSyncActionMap() {
        return syncActionMap;
    }

    public void setSyncActionMap(Map<String, SyncActionHandler> syncActionMap) {
        this.syncActionMap = syncActionMap;
    }

    /**
     * 用来分发handler的主方法
     * @param data
     */
    @Override
    public void handleSyncData(OpenSyncBizDataVO data){
        JSONObject json = JSONObject.parseObject(data.getBizData());
        String type = SuiteDbCheckConverter.json2SyncActionString(json);
        SyncActionHandler handler = syncActionMap.get(type);
        if(handler == null){
            throw new BizRuntimeException("no handler found for suite sync action: " + type);
        }
        handler.handleSyncAction(data);
    }
}
