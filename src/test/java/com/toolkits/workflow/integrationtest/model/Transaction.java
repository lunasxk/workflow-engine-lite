/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.integrationtest.model;

import java.io.Serializable;

/**
 * 交易模型, 用于验证流程引擎的简易模型
 * @author XiaokaiSun
 * @version $Id: Transaction.java, v 0.1 2017-12-28 14:45 XiaokaiSun Exp $$
 */
public class Transaction implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1931013205046110002L;

    /** 交易Id */
    private long              transactionId;

    /** buyerId */
    private long              buyerId;

    /** 商品名称 */
    private String            goodsName;

    /** 交易状态 */
    private String            status;

    /**
     * Getter method for property <tt>transactionId<tt>.
     *
     * @return property value of transactionId
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     * Setter method for property <tt>transactionId<tt>.
     *
     * @param transactionId value to be assigned to property transactionId
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Getter method for property <tt>buyerId<tt>.
     *
     * @return property value of buyerId
     */
    public long getBuyerId() {
        return buyerId;
    }

    /**
     * Setter method for property <tt>buyerId<tt>.
     *
     * @param buyerId value to be assigned to property buyerId
     */
    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    /**
     * Getter method for property <tt>goodsName<tt>.
     *
     * @return property value of goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Setter method for property <tt>goodsName<tt>.
     *
     * @param goodsName value to be assigned to property goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("transactionId=").append(transactionId);
        sb.append(", buyerId=").append(buyerId);
        sb.append(", goodsName='").append(goodsName).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}