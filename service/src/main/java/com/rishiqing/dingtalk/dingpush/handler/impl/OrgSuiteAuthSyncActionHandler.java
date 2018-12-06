package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.service.biz.CorpBizService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgSuiteAuthSyncActionHandler implements SyncActionHandler {
    @Autowired
    private CorpBizService corpBizService;
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject json = JSONObject.parseObject(data.getBizData());
        Long timestamp = 0L;
        if(data.getGmtModified() != null){
            timestamp = data.getGmtModified().getTime();
        }
        corpBizService.activateCorpApp(
                SuiteDbCheckConverter.json2CorpSuiteAuthInfo(json),
                timestamp
        );
    }
}
