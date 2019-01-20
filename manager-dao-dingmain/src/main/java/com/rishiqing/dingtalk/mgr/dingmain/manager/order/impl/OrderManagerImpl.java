package com.rishiqing.dingtalk.mgr.dingmain.manager.order.impl;

import com.rishiqing.dingtalk.mgr.dingmain.converter.order.OrderConverter;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp.CorpChargeStatusDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.order.OrderEventDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.order.OrderRsqPushEventDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.order.OrderSpecItemDao;
import com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.order.OrderStatusDao;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderStatusVO;
import com.rishiqing.dingtalk.mgr.dingmain.manager.order.OrderManager;
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
