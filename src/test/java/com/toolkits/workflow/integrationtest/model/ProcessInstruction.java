/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.integrationtest.model;

import java.util.HashMap;
import java.util.Map;

import com.toolkits.workflow.model.AbstractInstruction;

/**
 * 测试指令
 * @author XiaokaiSun
 * @version $Id: ProcessInstruction.java, v 0.1 2017-12-28 14:39 XiaokaiSun Exp $$
 */
public class ProcessInstruction extends AbstractInstruction {

    /** 交易 */
    private Transaction transaction;

    /** 其他内容 */
    private Object      other;

    /** 交易相关属性 */
    private Map<String, String> prop = new HashMap<>();

    /**
     * Getter method for property <tt>transaction<tt>.
     *
     * @return property value of transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Setter method for property <tt>transaction<tt>.
     *
     * @param transaction value to be assigned to property transaction
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * Getter method for property <tt>other<tt>.
     *
     * @return property value of other
     */
    public Object getOther() {
        return other;
    }

    /**
     * Setter method for property <tt>other<tt>.
     *
     * @param other value to be assigned to property other
     */
    public void setOther(Object other) {
        this.other = other;
    }

    /**
     * Getter method for property <tt>prop<tt>.
     *
     * @return property value of prop
     */
    public Map<String, String> getProp() {
        return prop;
    }

    /**
     * Setter method for property <tt>prop<tt>.
     *
     * @param prop value to be assigned to property prop
     */
    public void setProp(Map<String, String> prop) {
        this.prop = prop;
    }
}