package com.rishiqing.dingtalk.dao.model.order;

import java.util.Date;

/**
 * 规格表，与钉钉付费中的商品代码和商品规格对应一致
 * @author Wallace Mao
 * Date: 2018-10-18 17:56
 */
public class OrderSpecItemDO {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    private String suiteKey;
    //  商品码
    private String goodsCode;
    //  由钉钉生成的商品规格码
    private String itemCode;
    //  由钉钉生成的商品规格名称
    private String itemName;
    //  日事清内部使用的规格标识
    private String innerKey;
    //  日事清对应的充值版本
    private String rsqProductName;

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

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
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

    public String getInnerKey() {
        return innerKey;
    }

    public void setInnerKey(String innerKey) {
        this.innerKey = innerKey;
    }

    public String getRsqProductName() {
        return rsqProductName;
    }

    public void setRsqProductName(String rsqProductName) {
        this.rsqProductName = rsqProductName;
    }

    @Override
    public String toString() {
        return "OrderSpecItemDO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", suiteKey='" + suiteKey + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", innerKey='" + innerKey + '\'' +
                ", rsqProductName='" + rsqProductName + '\'' +
                '}';
    }
}
