package com.rishiqing.dingtalk.dingpush.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.dingtalk.biz.converter.order.OrderConverter;
import com.rishiqing.dingtalk.dingpush.handler.SyncActionHandler;
import com.rishiqing.dingtalk.isv.api.event.OrderChargeEvent;
import com.rishiqing.dingtalk.isv.api.model.dingpush.OpenSyncBizDataVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-10 14:33
 */
public class MarketOrderSyncActionHandler implements SyncActionHandler {
    @Autowired
    private AsyncEventBus asyncOrderChargeEventBus;
    /**
     subscribe_id  ： 套件suiteid加下划线0
     copp_id : 套件所属企业的corpid
     biz_id             ：为订单orderId
     biz_data         ：数据为Json格式
     "syncAction": "market_order"，表示市场订单支付
     "syncAction": "market_order_cancel"，表示市场订单取消（当前只有内购订单取消才有）。
     {
     "suiteId": 12345677,
     "corpId": "ding9f50xxxxxxxx",
     "orderId": 312139204444444,
     "syncAction": "market_order",
     "itemCode": "FW_GOODS-1000633333-2",
     "subQuantity": 3,
     "eventType": "market_buy",
     "maxOfPeople": 0,
     "orgId": 123321,
     "itemName": "免费规格",
     "payFee": 1,
     "serviceStopTime": 1527436800000,
     "suiteKey": "suitemsdwjzswebvrcggw",
     "goodsCode": "FW_GOODS-1000633333",
     "mainArticleCode": "FW_GOODS-xxxxxxxx",
     "mainArticleName": "xxxxxxxxxxxxxxxxx",
     "paidtime": 1524897069000,
     "minOfPeople": 0
     }
     * @param data
     */
    @Override
    public void handleSyncAction(OpenSyncBizDataVO data) {
        JSONObject jsonMessage = JSONObject.parseObject(data.getBizData());
        OrderEventVO orderEvent = OrderConverter.json2OrderEvent(jsonMessage);

        //  使用eventBus异步调用
        OrderChargeEvent event = new OrderChargeEvent();
        event.setSuiteKey(orderEvent.getSuiteKey());
        event.setOrderEventId(orderEvent.getId());

        asyncOrderChargeEventBus.post(event);
    }
}
