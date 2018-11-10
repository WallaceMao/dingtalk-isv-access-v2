package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.biz.converter.suite.SuiteDbCheckConverter;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteVO;
import com.rishiqing.dingtalk.isv.api.service.base.suite.SuiteManageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 13:54
 */
public class SuiteTicketSyncActionHandler implements SyncActionHandler{
    @Autowired
    private SuiteManageService suiteManageService;

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
        SuiteVO suiteVO = suiteManageService.getSuite();
        //  如果suiteId不一致，不做处理
        if(!suiteVO.getSuiteId().equals(data.getBizId())){
            return;
        }
        SuiteTicketVO suiteTicketVO = SuiteDbCheckConverter.json2SuiteTicket(json);
        suiteTicketVO.setSuiteKey(suiteVO.getSuiteKey());
        suiteManageService.saveOrUpdateSuiteTicket(suiteTicketVO);
    }
}
