package com.rishiqing.dingtalk.svc.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.svc.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.svc.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.api.model.vo.suite.SuiteVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.suite.SuiteManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 13:54
 */
public class SuiteTicketSyncActionHandler implements SyncActionHandler{
    @Autowired
    private SuiteManager suiteManager;

    /**
     * @link https://open-doc.dingtalk.com/microapp/ln6dmh/troq7i
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 套件所属企业的corpid
     biz_id             ： 套件suiteid
     biz_data         :   数据为如下Json格式

     {
     "syncAction":"suite_ticket",
     "suiteTicket":"QsfJCEVF1h6E9fAaGwnAzbvYzRF6UzyUh"
     }
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject json = JSONObject.parseObject(data.getBizData());
        SuiteVO suiteVO = suiteManager.getSuite();
        //  如果suiteId不一致，不做处理
        if(!suiteVO.getSuiteId().equals(data.getBizId())){
            return;
        }
        SuiteTicketVO suiteTicketVO = SuiteDbCheckConverter.json2SuiteTicket(json);
        suiteTicketVO.setSuiteKey(suiteVO.getSuiteKey());
        suiteManager.saveOrUpdateSuiteTicket(suiteTicketVO);
    }
}
