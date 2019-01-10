package com.rishiqing.dingtalk.biz.converter.suite;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.dingtalk.isv.api.model.order.OrderEventVO;
import com.rishiqing.dingtalk.isv.api.model.suite.CorpSuiteAuthVO;
import com.rishiqing.dingtalk.isv.api.model.suite.SuiteTicketVO;

/**
 * @author Wallace Mao
 * Date: 2018-11-03 17:36
 */
public class SuiteCallbackConverter {
    /**
     * 从json中提取出event type
     * @param json
     * @return
     */
    public static String json2EventTypeString(JSONObject json){
        return json.getString("EventType");
    }

    /**
     * 将钉钉传来的suite ticket相关的参数转换为系统通用的suite ticket
     * @param json
     * @return
     */
    public static SuiteTicketVO json2SuiteTicket(JSONObject json){
        String receiveSuiteTicket = json.getString("SuiteTicket");
        String receiveSuiteKey = json.getString("SuiteKey");
        SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
        suiteTicketVO.setSuiteTicket(receiveSuiteTicket);
        suiteTicketVO.setSuiteKey(receiveSuiteKey);
        return suiteTicketVO;
    }

    /**
     * 临时授权码
     * @param json
     * @return
     */
    public static CorpSuiteAuthVO json2CorpSuiteAuth(JSONObject json){
        CorpSuiteAuthVO corpSuiteAuthVO = new CorpSuiteAuthVO();
        if(json.containsKey("AuthCorpId")){
            corpSuiteAuthVO.setCorpId(json.getString("AuthCorpId"));
        }
        if(json.containsKey("AuthCode")){
            corpSuiteAuthVO.setTempAuthCode(json.getString("AuthCode"));
        }
        return corpSuiteAuthVO;
    }

    /**
     * 授权的corpId
     * @param json
     * @return
     */
    public static String json2AuthCorpIdString(JSONObject json){
        return json.getString("AuthCorpId");
    }

    public static OrderEventVO json2OrderEvent(JSONObject json){
        OrderEventVO obj = new OrderEventVO();
        obj.setEventType(json.getString("EventType"));
        obj.setSuiteKey(json.getString("SuiteKey"));
        obj.setBuyCorpId(json.getString("buyCorpId"));
        obj.setGoodsCode(json.getString("goodsCode"));
        obj.setItemCode(json.getString("itemCode"));
        obj.setItemName(json.getString("itemName"));
        obj.setOrderId(json.getLong("orderId"));
        obj.setPaidtime(json.getLong("paidtime"));
        obj.setServiceStopTime(json.getLong("serviceStopTime"));
        obj.setPayFee(json.getLong("payFee"));

        if(json.containsKey("subQuantity")){
            obj.setSubQuantity(json.getLong("subQuantity"));
        }
        if(json.containsKey("maxOfPeople")){
            obj.setMaxOfPeople(json.getLong("maxOfPeople"));
        }
        if(json.containsKey("minOfPeople")){
            obj.setMinOfPeople(json.getLong("minOfPeople"));
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
