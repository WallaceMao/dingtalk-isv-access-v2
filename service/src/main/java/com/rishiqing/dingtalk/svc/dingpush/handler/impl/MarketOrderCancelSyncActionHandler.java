package com.rishiqing.dingtalk.svc.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.svc.converter.order.OrderConverter;
import com.rishiqing.dingtalk.svc.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.api.model.vo.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class MarketOrderCancelSyncActionHandler implements SyncActionHandler {
    @Autowired
    private OrderManager orderManager;
    /**
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 套件所属企业的corpid
     biz_id             ：为订单orderId
     biz_data         ：数据为Json格式
     "syncAction": "market_order"，表示市场订单支付
     "syncAction": "market_order_cancel"，表示市场订单取消（当前只有内购订单取消才有）。
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject jsonMessage = JSONObject.parseObject(data.getBizData());
        OrderEventVO orderEvent = OrderConverter.json2OrderEvent(jsonMessage);
        orderManager.saveOrUpdateOrderEvent(orderEvent);
    }
}