package com.rishiqing.dingtalk.mgr.dingmain.converter.order;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpChargeStatusDO;
import com.rishiqing.dingtalk.api.model.domain.order.OrderEventDO;
import com.rishiqing.dingtalk.api.model.domain.order.OrderRsqPushEventDO;
import com.rishiqing.dingtalk.api.model.domain.order.OrderSpecItemDO;
import com.rishiqing.dingtalk.api.model.domain.order.OrderStatusDO;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderSpecItemVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderStatusVO;

/**
 * @author Wallace Mao
 * Date: 2018-12-24 14:49
 */
public class OrderConverter {
    public static OrderEventVO orderEventDO2OrderEventVO(OrderEventDO orderEventDO) {
        if(orderEventDO == null){
            return null;
        }
        OrderEventVO orderEventVO = new OrderEventVO();
        orderEventVO.setId(orderEventDO.getId());
        orderEventVO.setGmtCreate(orderEventDO.getGmtCreate());
        orderEventVO.setGmtModified(orderEventDO.getGmtModified());
        orderEventVO.setEventType(orderEventDO.getEventType());
        orderEventVO.setSuiteKey(orderEventDO.getSuiteKey());
        orderEventVO.setSuiteId(orderEventDO.getSuiteId());
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
        orderEventVO.setMainArticleCode(orderEventDO.getMainArticleCode());
        orderEventVO.setMainArticleName(orderEventDO.getMainArticleName());
        return orderEventVO;
    }

    public static OrderEventDO orderEventVO2OrderEventDO(OrderEventVO vo) {
        if(vo == null){
            return null;
        }
        OrderEventDO orderEventDO = new OrderEventDO();
        orderEventDO.setId(vo.getId());
        orderEventDO.setGmtCreate(vo.getGmtCreate());
        orderEventDO.setGmtModified(vo.getGmtModified());
        orderEventDO.setEventType(vo.getEventType());
        orderEventDO.setSuiteKey(vo.getSuiteKey());
        orderEventDO.setSuiteId(vo.getSuiteId());
        orderEventDO.setBuyCorpId(vo.getBuyCorpId());
        orderEventDO.setGoodsCode(vo.getGoodsCode());
        orderEventDO.setItemCode(vo.getItemCode());
        orderEventDO.setItemName(vo.getItemName());
        orderEventDO.setSubQuantity(vo.getSubQuantity());
        orderEventDO.setMaxOfPeople(vo.getMaxOfPeople());
        orderEventDO.setMinOfPeople(vo.getMinOfPeople());
        orderEventDO.setOrderId(vo.getOrderId());
        orderEventDO.setPaidtime(vo.getPaidtime());
        orderEventDO.setServiceStopTime(vo.getServiceStopTime());
        orderEventDO.setPayFee(vo.getPayFee());
        orderEventDO.setOrderCreateSource(vo.getOrderCreateSource());
        orderEventDO.setNominalPayFee(vo.getNominalPayFee());
        orderEventDO.setDiscountFee(vo.getDiscountFee());
        orderEventDO.setDiscount(vo.getDiscount());
        orderEventDO.setDistributorCorpId(vo.getDistributorCorpId());
        orderEventDO.setDistributorCorpName(vo.getDistributorCorpName());
        orderEventDO.setMainArticleCode(vo.getMainArticleCode());
        orderEventDO.setMainArticleName(vo.getMainArticleName());
        return orderEventDO;
    }

    public static OrderStatusVO orderStatusDO2OrderStatusVO(OrderStatusDO orderStatusDO) {
        if(orderStatusDO == null){
            return null;
        }
        OrderStatusVO orderStatusVO = new OrderStatusVO();
        orderStatusVO.setId(orderStatusDO.getId());
        orderStatusVO.setGmtCreate(orderStatusDO.getGmtCreate());
        orderStatusVO.setGmtModified(orderStatusDO.getGmtModified());
        orderStatusVO.setSuiteKey(orderStatusDO.getSuiteKey());
        orderStatusVO.setSuiteId(orderStatusDO.getSuiteId());
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
        orderStatusVO.setMainArticleCode(orderStatusDO.getMainArticleCode());
        orderStatusVO.setMainArticleName(orderStatusDO.getMainArticleName());
        return orderStatusVO;
    }

    public static OrderStatusDO orderStatusVO2OrderStatusDO(OrderStatusVO orderStatus) {
        if(orderStatus == null){
            return null;
        }
        OrderStatusDO orderStatusDO = new OrderStatusDO();
        orderStatusDO.setId(orderStatus.getId());
        orderStatusDO.setGmtCreate(orderStatus.getGmtCreate());
        orderStatusDO.setGmtModified(orderStatus.getGmtModified());
        orderStatusDO.setSuiteKey(orderStatus.getSuiteKey());
        orderStatusDO.setSuiteId(orderStatus.getSuiteId());
        orderStatusDO.setBuyCorpId(orderStatus.getBuyCorpId());
        orderStatusDO.setGoodsCode(orderStatus.getGoodsCode());
        orderStatusDO.setItemCode(orderStatus.getItemCode());
        orderStatusDO.setItemName(orderStatus.getItemName());
        orderStatusDO.setSubQuantity(orderStatus.getSubQuantity());
        orderStatusDO.setMaxOfPeople(orderStatus.getMaxOfPeople());
        orderStatusDO.setMinOfPeople(orderStatus.getMinOfPeople());
        orderStatusDO.setOrderId(orderStatus.getOrderId());
        orderStatusDO.setPaidtime(orderStatus.getPaidtime());
        orderStatusDO.setServiceStopTime(orderStatus.getServiceStopTime());
        orderStatusDO.setPayFee(orderStatus.getPayFee());
        orderStatusDO.setOrderCreateSource(orderStatus.getOrderCreateSource());
        orderStatusDO.setNominalPayFee(orderStatus.getNominalPayFee());
        orderStatusDO.setDiscountFee(orderStatus.getDiscountFee());
        orderStatusDO.setDiscount(orderStatus.getDiscount());
        orderStatusDO.setDistributorCorpId(orderStatus.getDistributorCorpId());
        orderStatusDO.setDistributorCorpName(orderStatus.getDistributorCorpName());
        orderStatusDO.setStatus(orderStatus.getStatus());
        orderStatusDO.setMainArticleCode(orderStatus.getMainArticleCode());
        orderStatusDO.setMainArticleName(orderStatus.getMainArticleName());
        return orderStatusDO;
    }

    public static OrderSpecItemVO orderSpecItemDO2OrderSpecItemVO(OrderSpecItemDO itemDO) {
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

    public static CorpChargeStatusDO corpChargeStatusVO2CorpChargeStatusDO(CorpChargeStatusVO corpChargeStatus) {
        if(corpChargeStatus == null){
            return null;
        }
        CorpChargeStatusDO doModel = new CorpChargeStatusDO();
        doModel.setId(corpChargeStatus.getId());
        doModel.setGmtCreate(corpChargeStatus.getGmtCreate());
        doModel.setGmtModified(corpChargeStatus.getGmtModified());
        doModel.setSuiteKey(corpChargeStatus.getSuiteKey());
        doModel.setCorpId(corpChargeStatus.getCorpId());
        doModel.setStatus(corpChargeStatus.getStatus());
        doModel.setTotalQuantity(corpChargeStatus.getTotalQuantity());
        doModel.setCurrentOrderId(corpChargeStatus.getCurrentOrderId());
        doModel.setCurrentGoodsCode(corpChargeStatus.getCurrentGoodsCode());
        doModel.setCurrentItemCode(corpChargeStatus.getCurrentItemCode());
        doModel.setCurrentSubQuantity(corpChargeStatus.getCurrentSubQuantity());
        doModel.setCurrentMaxOfPeople(corpChargeStatus.getCurrentMaxOfPeople());
        doModel.setCurrentMinOfPeople(corpChargeStatus.getCurrentMinOfPeople());
        doModel.setCurrentServiceStopTime(corpChargeStatus.getCurrentServiceStopTime());

        return doModel;
    }

    public static OrderRsqPushEventDO orderRsqPushEventVO2OrderRsqPushEventDO(OrderRsqPushEventVO orderRsqPushEventVO) {
        if(orderRsqPushEventVO == null){
            return null;
        }
        OrderRsqPushEventDO orderRsqPushEventDO = new OrderRsqPushEventDO();
        orderRsqPushEventDO.setId(orderRsqPushEventVO.getId());
        orderRsqPushEventDO.setGmtCreate(orderRsqPushEventVO.getGmtCreate());
        orderRsqPushEventDO.setGmtModified(orderRsqPushEventVO.getGmtModified());
        orderRsqPushEventDO.setSuiteKey(orderRsqPushEventVO.getSuiteKey());
        orderRsqPushEventDO.setOrderId(orderRsqPushEventVO.getOrderId());
        orderRsqPushEventDO.setCorpId(orderRsqPushEventVO.getCorpId());
        orderRsqPushEventDO.setServiceStopTime(orderRsqPushEventVO.getServiceStopTime());
        orderRsqPushEventDO.setQuantity(orderRsqPushEventVO.getQuantity());
        orderRsqPushEventDO.setStatus(orderRsqPushEventVO.getStatus());
        orderRsqPushEventDO.setRsqTeamId(orderRsqPushEventVO.getRsqTeamId());
        return orderRsqPushEventDO;
    }
}
