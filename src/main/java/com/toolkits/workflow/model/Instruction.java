/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model;

/**
 * 进入工作流的模型统一要实现此接口
 * @author XiaokaiSun
 * @version $Id: Instruction.java, v 0.1 2017-11-23 20:27 XiaokaiSun Exp $$
 */
public interface Instruction {

    /**
     * 设置工作流Id
     */
    public void setWorkFlowId(String workFlowId);

    /**
     * 获取工作流程Id
     * @return
     */
    public String getWorkFlowId();

    /**
     * 获取流程引擎的原状态
     * @return
     */
    public String getSourceStatus();

    /**
     * 设置初始状态
     */
    public void setSourceStatus(String sourceStatus);

    /**
     * 获取流程引擎的目标状态
     * @return
     */
    public String getDestStatus();

    /**
     * 设置目标状态
     */
    public void setDestStatus(String destStatus);

}