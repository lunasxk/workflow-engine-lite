/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model;


/**
 * 抽象工作流引擎指令,
 * @author XiaokaiSun
 * @version $Id: AbstractInstruction.java, v 0.1 2017-11-24 17:58 XiaokaiSun Exp $$
 */
public class AbstractInstruction implements Instruction {

    /** 工作流Id */
    private String workFlowId;

    /** 初始状态 */
    private String sourceStatus;

    /** 目标状态 */
    private String destStatus;


    /**
     * Getter method for property <tt>workFlowId<tt>.
     *
     * @return property value of workFlowId
     */
    @Override
    public String getWorkFlowId() {
        return workFlowId;
    }

    /**
     * Setter method for property <tt>workFlowId<tt>.
     *
     * @param workFlowId value to be assigned to property workFlowId
     */
    @Override
    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    /**
     * Getter method for property <tt>sourceStatus<tt>.
     *
     * @return property value of sourceStatus
     */
    @Override
    public String getSourceStatus() {
        return sourceStatus;
    }

    /**
     * Setter method for property <tt>sourceStatus<tt>.
     *
     * @param sourceStatus value to be assigned to property sourceStatus
     */
    @Override
    public void setSourceStatus(String sourceStatus) {
        this.sourceStatus = sourceStatus;
    }

    /**
     * Getter method for property <tt>destStatus<tt>.
     *
     * @return property value of destStatus
     */
    @Override
    public String getDestStatus() {
        return destStatus;
    }

    /**
     * Setter method for property <tt>destStatus<tt>.
     *
     * @param destStatus value to be assigned to property destStatus
     */
    @Override
    public void setDestStatus(String destStatus) {
        this.destStatus = destStatus;
    }

    /**
     * toString
     *
     * @return
     */
    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("workFlowId", workFlowId)
                .add("sourceStatus", sourceStatus)
                .add("destStatus", destStatus)
                .toString();
    }
}