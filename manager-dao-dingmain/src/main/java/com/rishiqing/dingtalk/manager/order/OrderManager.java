package com.rishiqing.dingtalk.manager.order;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderStatusVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 0:02
 */
public interface OrderManager {
    OrderEventVO getOrderEventById(Long id);

    OrderEventVO getOrderEventByOrderId(Long orderId);

    OrderEventVO getOrderEventByCorpIdAndLatest(String corpId);

    OrderStatusVO getOrderStatusByOrderId(Long orderId);

    OrderSpecItemVO getOrderSpecItemByGoodsCodeAndItemCode(String goodsCode, String itemCode);

    void saveOrUpdateOrderStatus(OrderStatusVO orderStatus);

    void saveOrUpdateCorpChargeStatus(CorpChargeStatusVO corpChargeStatusDO);

    void saveOrUpdateOrderRsqPushEvent(OrderRsqPushEventVO rsqPushEvent);

    void saveOrUpdateOrderEvent(OrderEventVO orderEvent);
}
