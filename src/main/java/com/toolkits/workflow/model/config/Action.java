/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model.config;

import com.google.common.base.MoreObjects;
import com.toolkits.workflow.Exception.WorkFlowErrorCode;
import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.model.BusinessAction;
import com.toolkits.workflow.spi.ComponentFetcher;

/**
 * 业务Action定义
 * @author XiaokaiSun
 * @version $Id: Action.java, v 0.1 2017-11-24 16:44 XiaokaiSun Exp $$
 */
public class Action implements Initable, Sortable {

    /** action的顺序 */
    private Integer        sequence;

    /** 对应bean的名字 */
    private String         refBeanName;

    /** action描述 */
    private String         desc;

    /** 对应的业务action的bean */
    private BusinessAction businessAction;

    /**
     * 构造函数
     */
    public Action() {
    }

    /**
     * 初始化统一接口，
     *
     * @param componentFetcher 获取对应Component的的bean
     */
    @Override
    public void init(ComponentFetcher componentFetcher) {
        BusinessAction rtn = componentFetcher.getActionByName(refBeanName);
        if (rtn != null) {
            this.businessAction = rtn;
        } else {
            throw new WorkFlowException(WorkFlowErrorCode.FETCH_BEAN_ERROR, this.refBeanName);
        }
    }

    /**
     * 获取对应的顺序编号
     *
     * @return
     */
    @Override
    public int getSeq() {
        return this.sequence;
    }

    /**
     * Setter method for property <tt>sequence<tt>.
     *
     * @param sequence value to be assigned to property sequence
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * Getter method for property <tt>refBeanName<tt>.
     *
     * @return property value of refBeanName
     */
    public String getRefBeanName() {
        return refBeanName;
    }

    /**
     * Setter method for property <tt>refBeanName<tt>.
     *
     * @param refBeanName value to be assigned to property refBeanName
     */
    public void setRefBeanName(String refBeanName) {
        this.refBeanName = refBeanName;
    }

    /**
     * Getter method for property <tt>desc<tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc<tt>.
     *
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>businessAction<tt>.
     *
     * @return property value of businessAction
     */
    public BusinessAction getBusinessAction() {
        return businessAction;
    }

    /**
     * Setter method for property <tt>businessAction<tt>.
     *
     * @param businessAction value to be assigned to property businessAction
     */
    public void setBusinessAction(BusinessAction businessAction) {
        this.businessAction = businessAction;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sequence", sequence)
                .add("refBeanName", refBeanName)
                .add("desc", desc)
                .toString();
    }
}