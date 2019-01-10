package com.rishiqing.dingtalk.manager.order.impl;

import com.rishiqing.dingtalk.converter.order.OrderConverter;
import com.rishiqing.dingtalk.dao.mapper.corp.CorpChargeStatusDao;
import com.rishiqing.dingtalk.dao.mapper.order.OrderEventDao;
import com.rishiqing.dingtalk.dao.mapper.order.OrderRsqPushEventDao;
import com.rishiqing.dingtalk.dao.mapper.order.OrderSpecItemDao;
import com.rishiqing.dingtalk.dao.mapper.order.OrderStatusDao;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderStatusVO;
import com.rishiqing.dingtalk.manager.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 0:03
 */
public class OrderManagerImpl implements OrderManager {
    @Autowired
    private OrderEventDao orderEventDao;
    @Autowired
    private OrderRsqPushEventDao orderRsqPushEventDao;
    @Autowired
    private OrderStatusDao orderStatusDao;
    @Autowired
    private OrderSpecItemDao orderSpecItemDao;
    @Autowired
    private CorpChargeStatusDao corpChargeStatusDao;


    @Override
    public OrderEventVO getOrderEventById(Long id) {
        return OrderConverter.orderEventDO2OrderEventVO(
                orderEventDao.getOrderEventById(id)
        );
    }

    @Override
    public OrderEventVO getOrderEventByOrderId(Long orderId) {
        return OrderConverter.orderEventDO2OrderEventVO(
                orderEventDao.getOrderEventByOrderId(orderId)
        );
    }

    @Override
    public OrderEventVO getOrderEventByCorpIdAndLatest(String corpId) {
        return OrderConverter.orderEventDO2OrderEventVO(
                orderEventDao.getLatestOrderEventByCorpId(corpId)
        );
    }

    @Override
    public OrderStatusVO getOrderStatusByOrderId(Long orderId) {
        return OrderConverter.orderStatusDO2OrderStatusVO(
                orderStatusDao.getOrderStatusByOrderId(orderId)
        );
    }

    @Override
    public OrderSpecItemVO getOrderSpecItemByGoodsCodeAndItemCode(String goodsCode, String itemCode){
        return OrderConverter.orderSpecItemDO2OrderSpecItemVO(
                orderSpecItemDao.getOrderSpecItemByGoodsCodeAndItemCode(goodsCode, itemCode)
        );
    }

    @Override
    public void saveOrUpdateOrderStatus(OrderStatusVO orderStatus) {
        orderStatusDao.saveOrUpdateOrderStatus(
                OrderConverter.orderStatusVO2OrderStatusDO(orderStatus)
        );
    }

    @Override
    public void saveOrUpdateCorpChargeStatus(CorpChargeStatusVO corpChargeStatusDO) {
        corpChargeStatusDao.saveOrUpdateCorpChargeStatus(
                OrderConverter.corpChargeStatusVO2CorpChargeStatusDO(corpChargeStatusDO)
        );
    }

    @Override
    public void saveOrUpdateOrderRsqPushEvent(OrderRsqPushEventVO rsqPushEvent) {
        orderRsqPushEventDao.saveOrUpdateOrderRsqPushEvent(
                OrderConverter.orderRsqPushEventVO2OrderRsqPushEventDO(rsqPushEvent)
        );
    }

    @Override
    public void saveOrUpdateOrderEvent(OrderEventVO orderEvent) {
        orderEventDao.saveOrUpdateOrderEvent(
                OrderConverter.orderEventVO2OrderEventDO(orderEvent)
        );
    }
}
