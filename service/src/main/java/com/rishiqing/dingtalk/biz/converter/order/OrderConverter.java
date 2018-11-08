package com.rishiqing.dingtalk.biz.converter.order;

import com.rishiqing.dingtalk.dao.mapper.order.OrderStatusDao;
import com.rishiqing.dingtalk.dao.model.order.OrderEventDO;
import com.rishiqing.dingtalk.dao.model.order.OrderSpecItemDO;
import com.rishiqing.dingtalk.dao.model.order.OrderStatusDO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.isv.api.model.order.OrderStatusVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 0:05
 */
public class OrderConverter {
    public static OrderEventVO orderEventDO2OrderEventVO(OrderEventDO orderEventDO) {
        OrderEventVO orderEventVO = new OrderEventVO();
        orderEventVO.setId(orderEventDO.getId());
        orderEventVO.setGmtCreate(orderEventDO.getGmtCreate());
        orderEventVO.setGmtModified(orderEventDO.getGmtModified());
        orderEventVO.setEventType(orderEventDO.getEventType());
        orderEventVO.setSuiteKey(orderEventDO.getSuiteKey());
        orderEventVO.setBuyCorpId(orderEventDO.getBuyCorpId());
        orderEventVO.setGoodsCode(orderEventDO.getGoodsCode());
        orderEventVO.setItemCode(orderEventDO.getItemCode());
        orderEventVO.setItemName(orderEventDO.getItemName());
        orderEventVO.setSubQuantity(orderEventDO.getSubQuantity());
        orderEventVO.setMaxOfPeople(orderEventDO.getMaxOfPeople());
        orderEventVO.setMinOfPeople(orderEventDO.getMinOfPeople());
        orderEventVO.setOrderId(orderEventDO.getOrderId());
        orderEventVO.setPaidtime(orderEventDO.getPaidtime());
        orderEventVO.setServiceStopTime(orderEventDO.getServiceStopTime());
        orderEventVO.setPayFee(orderEventDO.getPayFee());
        orderEventVO.setOrderCreateSource(orderEventDO.getOrderCreateSource());
        orderEventVO.setNominalPayFee(orderEventDO.getNominalPayFee());
        orderEventVO.setDiscountFee(orderEventDO.getDiscountFee());
        orderEventVO.setDiscount(orderEventDO.getDiscount());
        orderEventVO.setDistributorCorpId(orderEventDO.getDistributorCorpId());
        orderEventVO.setDistributorCorpName(orderEventDO.getDistributorCorpName());
        return orderEventVO;
    }

    public static OrderStatusVO orderStatusDO2OrderStatusVO(OrderStatusDO orderStatusDO) {
        OrderStatusVO orderStatusVO = new OrderStatusVO();
        orderStatusVO.setId(orderStatusDO.getId());
        orderStatusVO.setGmtCreate(orderStatusDO.getGmtCreate());
        orderStatusVO.setGmtModified(orderStatusDO.getGmtModified());
        orderStatusVO.setSuiteKey(orderStatusDO.getSuiteKey());
        orderStatusVO.setBuyCorpId(orderStatusDO.getBuyCorpId());
        orderStatusVO.setGoodsCode(orderStatusDO.getGoodsCode());
        orderStatusVO.setItemCode(orderStatusDO.getItemCode());
        orderStatusVO.setItemName(orderStatusDO.getItemName());
        orderStatusVO.setSubQuantity(orderStatusDO.getSubQuantity());
        orderStatusVO.setMaxOfPeople(orderStatusDO.getMaxOfPeople());
        orderStatusVO.setMinOfPeople(orderStatusDO.getMinOfPeople());
        orderStatusVO.setOrderId(orderStatusDO.getOrderId());
        orderStatusVO.setPaidtime(orderStatusDO.getPaidtime());
        orderStatusVO.setServiceStopTime(orderStatusDO.getServiceStopTime());
        orderStatusVO.setPayFee(orderStatusDO.getPayFee());
        orderStatusVO.setOrderCreateSource(orderStatusDO.getOrderCreateSource());
        orderStatusVO.setNominalPayFee(orderStatusDO.getNominalPayFee());
        orderStatusVO.setDiscountFee(orderStatusDO.getDiscountFee());
        orderStatusVO.setDiscount(orderStatusDO.getDiscount());
        orderStatusVO.setDistributorCorpId(orderStatusDO.getDistributorCorpId());
        orderStatusVO.setDistributorCorpName(orderStatusDO.getDistributorCorpName());
        orderStatusVO.setStatus(orderStatusDO.getStatus());
        return orderStatusVO;
    }

    public static OrderSpecItemVO orderSpecItemDo2OrderSpecItemVO(OrderSpecItemDO itemDO) {
        if(itemDO == null){
            return null;
        }
        OrderSpecItemVO itemVO = new OrderSpecItemVO();
        itemVO.setId(itemDO.getId());
        itemVO.setGmtCreate(itemDO.getGmtCreate());
        itemVO.setGmtModified(itemDO.getGmtModified());
        itemVO.setSuiteKey(itemDO.getSuiteKey());
        itemVO.setGoodsCode(itemDO.getGoodsCode());
        itemVO.setItemCode(itemDO.getItemCode());
        itemVO.setItemName(itemDO.getItemName());
        itemVO.setInnerKey(itemDO.getInnerKey());
        itemVO.setRsqProductName(itemDO.getRsqProductName());
        return itemVO;
    }
}
