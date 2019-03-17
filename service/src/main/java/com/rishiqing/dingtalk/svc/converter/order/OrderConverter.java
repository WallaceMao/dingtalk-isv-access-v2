package com.rishiqing.dingtalk.svc.converter.order;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.api.model.vo.corp.CorpChargeStatusVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderRsqPushEventVO;
import com.rishiqing.dingtalk.api.model.vo.order.OrderStatusVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 0:05
 */
public class OrderConverter {
    public static OrderRsqPushEventVO orderStatusVO2OrderRsqPushEventVO(OrderStatusVO orderStatus) {
        if(orderStatus == null){
            return null;
        }
        OrderRsqPushEventVO event = new OrderRsqPushEventVO();
        event.setSuiteKey(orderStatus.getSuiteKey());
        event.setOrderId(orderStatus.getOrderId());
        event.setCorpId(orderStatus.getBuyCorpId());
        event.setServiceStopTime(orderStatus.getServiceStopTime());
        if(orderStatus.getSubQuantity() != null){
            event.setQuantity(orderStatus.getSubQuantity());
        }else{
            event.setQuantity(orderStatus.getMaxOfPeople());
        }
        return event;
    }

    public static OrderStatusVO orderEventVO2OrderStatusVO(OrderEventVO orderEvent){
        if(orderEvent == null){
            return null;
        }
        OrderStatusVO orderStatus = new OrderStatusVO();
        orderStatus.setOrderId(orderEvent.getOrderId());
        orderStatus.setSuiteKey(orderEvent.getSuiteKey());
        orderStatus.setBuyCorpId(orderEvent.getBuyCorpId());
        orderStatus.setGoodsCode(orderEvent.getGoodsCode());
        orderStatus.setItemCode(orderEvent.getItemCode());
        orderStatus.setItemName(orderEvent.getItemName());
        orderStatus.setSubQuantity(orderEvent.getSubQuantity());
        orderStatus.setMaxOfPeople(orderEvent.getMaxOfPeople());
        orderStatus.setMinOfPeople(orderEvent.getMinOfPeople());
        orderStatus.setPaidtime(orderEvent.getPaidtime());
        orderStatus.setServiceStopTime(orderEvent.getServiceStopTime());
        orderStatus.setPayFee(orderEvent.getPayFee());
        orderStatus.setOrderCreateSource(orderEvent.getOrderCreateSource());
        orderStatus.setNominalPayFee(orderEvent.getNominalPayFee());
        orderStatus.setDiscountFee(orderEvent.getDiscountFee());
        orderStatus.setDiscount(orderEvent.getDiscount());
        orderStatus.setDistributorCorpId(orderEvent.getDistributorCorpId());
        orderStatus.setDistributorCorpName(orderEvent.getDistributorCorpName());

        return orderStatus;
    }

    public static CorpChargeStatusVO orderStatusVO2CorpChargeStatusVO(OrderStatusVO orderStatus) {
        if(orderStatus == null){
            return null;
        }
        CorpChargeStatusVO corpChargeStatus = new CorpChargeStatusVO();
        corpChargeStatus.setSuiteKey(orderStatus.getSuiteKey());
        corpChargeStatus.setCorpId(orderStatus.getBuyCorpId());
        corpChargeStatus.setCurrentOrderId(orderStatus.getOrderId());
        corpChargeStatus.setCurrentGoodsCode(orderStatus.getGoodsCode());
        corpChargeStatus.setCurrentItemCode(orderStatus.getItemCode());
        corpChargeStatus.setCurrentSubQuantity(orderStatus.getSubQuantity());
        corpChargeStatus.setCurrentMaxOfPeople(orderStatus.getMaxOfPeople());
        corpChargeStatus.setCurrentMinOfPeople(orderStatus.getMinOfPeople());
        corpChargeStatus.setCurrentServiceStopTime(orderStatus.getServiceStopTime());

        return corpChargeStatus;
    }

    public static OrderEventVO json2OrderEvent(JSONObject json){
        if(json == null){
            return null;
        }
        OrderEventVO obj = new OrderEventVO();
        obj.setSuiteKey(json.getString("suiteKey"));
        obj.setSuiteId(String.valueOf(json.getLong("suiteId")));
        obj.setBuyCorpId(json.getString("corpId"));
        obj.setOrderId(json.getLong("orderId"));
        obj.setGoodsCode(json.getString("goodsCode"));
        obj.setItemCode(json.getString("itemCode"));
        obj.setItemName(json.getString("itemName"));
        obj.setPaidtime(json.getLong("paidtime"));
        obj.setServiceStopTime(json.getLong("serviceStopTime"));
        obj.setPayFee(json.getLong("payFee"));

        //  eventType不能为空。钉钉云使用的字段是syncAction，回调方式使用的字段是eventType
        String eventType;
        if(json.containsKey("syncAction")){
            eventType = json.getString("syncAction");
        }else if(json.containsKey("eventType")){
            eventType = json.getString("eventType");
        }else{
            eventType = "default";
        }
        obj.setEventType(eventType);

        if(json.containsKey("maxOfPeople")){
            obj.setMaxOfPeople(json.getLong("maxOfPeople"));
        }
        if(json.containsKey("minOfPeople")){
            obj.setMinOfPeople(json.getLong("minOfPeople"));
        }
        if(json.containsKey("subQuantity")){
            obj.setSubQuantity(json.getLong("subQuantity"));
        }

        if(json.containsKey("mainArticleCode")){
            obj.setMainArticleCode(json.getString("mainArticleCode"));
        }
        if(json.containsKey("mainArticleName")){
            obj.setMainArticleName(json.getString("mainArticleName"));
        }

        if(json.containsKey("orderCreateSource")){
            obj.setOrderCreateSource(json.getString("orderCreateSource"));
        }
        if(json.containsKey("nominalPayFee")){
            obj.setNominalPayFee(json.getLong("nominalPayFee"));
        }
        if(json.containsKey("discountFee")){
            obj.setDiscountFee(json.getLong("discountFee"));
        }
        if(json.containsKey("discount")){
            obj.setDiscount(json.getDouble("discount"));
        }
        if(json.containsKey("distributorCorpId")){
            obj.setDistributorCorpId(json.getString("distributorCorpId"));
        }
        if(json.containsKey("distributorCorpName")){
            obj.setDistributorCorpName(json.getString("distributorCorpName"));
        }
        return obj;
    }
}
