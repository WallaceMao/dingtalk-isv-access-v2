package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.manager.corp.CorpManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class OrgUpdateSyncActionHandler implements SyncActionHandler {
    @Autowired
    private CorpManager corpManager;
    /**
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 套件所属企业的corpid
     biz_id             ：为企业corpid
     biz_data         ：数据为如下两种 Json格式
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject json = JSONObject.parseObject(data.getBizData());
        CorpVO corpVO = SuiteDbCheckConverter.json2Corp(json);
        corpVO.setScopeVersion(new Date().getTime());
        corpManager.saveOrUpdateCorp(corpVO);

        //TODO  然后推送到日事清
    }
}
