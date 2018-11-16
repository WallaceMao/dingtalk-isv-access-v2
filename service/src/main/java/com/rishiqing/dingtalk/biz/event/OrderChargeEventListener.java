package com.rishiqing.dingtalk.biz.event;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.rishiqing.dingtalk.biz.service.biz.impl.ChargeBizService;
import com.rishiqing.dingtalk.isv.api.event.EventListener;
import com.rishiqing.dingtalk.isv.api.event.OrderChargeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 20:37
 */
public class OrderChargeEventListener implements EventListener {
    private static final Logger bizLogger = LoggerFactory.getLogger("LSN_ORDER_CHARGE_LOGGER");

    @Autowired
    private ChargeBizService chargeBizService;

    /**
     * 企业授权套件临时授权码异步逻辑
     * @param event
     */
    @Subscribe
    @AllowConcurrentEvents //  event并行执行
    public void listenOrderChargeEvent(OrderChargeEvent event) {
        try{
            bizLogger.info("listenOrderChargeEvent: " + JSON.toJSONString(event));
            String suiteKey = event.getSuiteKey();
            Long eventId = event.getOrderEventId();
            //  充值
//            chargeBizService.charge(suiteKey, eventId);
        }catch (Exception e){
            bizLogger.error("OrderChargeEvent: " + event, e);
        }
    }
}
