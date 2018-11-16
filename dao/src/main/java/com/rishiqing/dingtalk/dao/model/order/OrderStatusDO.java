package com.rishiqing.dingtalk.dao.model.order;

import java.util.Date;

/**
 * 订单状态表，此表用来做订单状态的更新。OrderEventDO只用来接收保存订单状态
 * @author Wallace Mao
 * Date: 2018-10-18 16:38
 */
public class OrderStatusDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    //  订单id
    private Long orderId;
    //  用户购买套件的SuiteKey
    private String suiteKey;
    private String suiteId;
    //  购买该套件企业的corpid
    private String buyCorpId;
    //  购买的商品码
    private String goodsCode;
    //  购买的商品规格码
    private String itemCode;
    //  购买的商品规格名称
    private String itemName;
    //  订购的具体人数
    private Long subQuantity;
    //  购买的商品规格能服务的最多企业人数
    private Long maxOfPeople;
    //  购买的商品规格能服务的最少企业人数
    private Long minOfPeople;
    //  下单时间
    private Long paidtime;
    //  该订单的服务到期时间
    private Long serviceStopTime;
    //  订单支付费用,以分为单位
    private Long payFee;
    //  订单创建来源，如果来自钉钉分销系统，则值为“DRP”
    private String orderCreateSource;
    //  钉钉分销系统提单价，以分为单位
    private Long nominalPayFee;
    //  折扣减免费用
    private Long discountFee;
    //  订单折扣
    private Double discount;
    //  钉钉分销系统提单的代理商的企业corpId
    private String distributorCorpId;
    //  钉钉分销系统提单的代理商的企业名称
    private String distributorCorpName;
    //  内购订单中，应用商品码
    private String mainArticleCode;
    //  内购订单中，应用商品名称
    private String mainArticleName;
    //  订单的实际状态，状态值为SystemConstant.ORDER_STATUS_PAID或者SystemConstant.ORDER_STATUS_RSQ_SYNC
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
    }

    public String getBuyCorpId() {
        return buyCorpId;
    }

    public void setBuyCorpId(String buyCorpId) {
        this.buyCorpId = buyCorpId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getSubQuantity() {
        return subQuantity;
    }

    public void setSubQuantity(Long subQuantity) {
        this.subQuantity = subQuantity;
    }

    public Long getMaxOfPeople() {
        return maxOfPeople;
    }

    public void setMaxOfPeople(Long maxOfPeople) {
        this.maxOfPeople = maxOfPeople;
    }

    public Long getMinOfPeople() {
        return minOfPeople;
    }

    public void setMinOfPeople(Long minOfPeople) {
        this.minOfPeople = minOfPeople;
    }

    public Long getPaidtime() {
        return paidtime;
    }

    public void setPaidtime(Long paidtime) {
        this.paidtime = paidtime;
    }

    public Long getServiceStopTime() {
        return serviceStopTime;
    }

    public void setServiceStopTime(Long serviceStopTime) {
        this.serviceStopTime = serviceStopTime;
    }

    public Long getPayFee() {
        return payFee;
    }

    public void setPayFee(Long payFee) {
        this.payFee = payFee;
    }

    public String getOrderCreateSource() {
        return orderCreateSource;
    }

    public void setOrderCreateSource(String orderCreateSource) {
        this.orderCreateSource = orderCreateSource;
    }

    public Long getNominalPayFee() {
        return nominalPayFee;
    }

    public void setNominalPayFee(Long nominalPayFee) {
        this.nominalPayFee = nominalPayFee;
    }

    public Long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDistributorCorpId() {
        return distributorCorpId;
    }

    public void setDistributorCorpId(String distributorCorpId) {
        this.distributorCorpId = distributorCorpId;
    }

    public String getDistributorCorpName() {
        return distributorCorpName;
    }

    public void setDistributorCorpName(String distributorCorpName) {
        this.distributorCorpName = distributorCorpName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainArticleCode() {
        return mainArticleCode;
    }

    public void setMainArticleCode(String mainArticleCode) {
        this.mainArticleCode = mainArticleCode;
    }

    public String getMainArticleName() {
        return mainArticleName;
    }

    public void setMainArticleName(String mainArticleName) {
        this.mainArticleName = mainArticleName;
    }

    @Override
    public String toString() {
        return "OrderStatusDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", orderId=" + orderId +
                ", suiteKey='" + suiteKey + '\'' +
                ", suiteId='" + suiteId + '\'' +
                ", buyCorpId='" + buyCorpId + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", subQuantity=" + subQuantity +
                ", maxOfPeople=" + maxOfPeople +
                ", minOfPeople=" + minOfPeople +
                ", paidtime=" + paidtime +
                ", serviceStopTime=" + serviceStopTime +
                ", payFee=" + payFee +
                ", orderCreateSource='" + orderCreateSource + '\'' +
                ", nominalPayFee=" + nominalPayFee +
                ", discountFee=" + discountFee +
                ", discount=" + discount +
                ", distributorCorpId='" + distributorCorpId + '\'' +
                ", distributorCorpName='" + distributorCorpName + '\'' +
                ", mainArticleCode='" + mainArticleCode + '\'' +
                ", mainArticleName='" + mainArticleName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
