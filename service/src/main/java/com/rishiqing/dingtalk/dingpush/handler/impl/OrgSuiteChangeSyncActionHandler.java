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
public class OrgSuiteChangeSyncActionHandler implements SyncActionHandler {
    @Autowired
    private CorpBizService corpBizService;
    /**
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 开通套件微应用的企业corpid
     biz_id             ： 套件suiteid
     biz_data         ： 数据为如下Json格式，其中"syncAction" 字段取值含义如下：
     "org_suite_auth" ：表示企业授权套件
     "org_suite_change"：表示企业变更授权范围
     "org_suite_relieve"：表示企业解除授权
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject json = JSONObject.parseObject(data.getBizData());
        Long timestamp = 0L;
        if(data.getGmtModified() != null){
            timestamp = data.getGmtModified().getTime();
        }
        corpBizService.changeCorpApp(
                SuiteDbCheckConverter.json2CorpSuiteAuthInfo(json),
                timestamp
        );    }
}
