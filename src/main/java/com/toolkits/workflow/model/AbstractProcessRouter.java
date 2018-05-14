/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model;

import com.toolkits.workflow.Exception.WorkFlowException;

/**
 * 抽象业务router类
 * @author XiaokaiSun
 * @version $Id: AbstractProcessRouter.java, v 0.1 2017-12-04 20:12 XiaokaiSun Exp $$
 */
public abstract class AbstractProcessRouter implements ProcessRouter {

    /**
     * 根据流程结果进行状态路由
     *
     * @param workFlowContext 工作流上下文
     * @return
     * @throws WorkFlowException
     */
    @Override
    public Instruction router(WorkFlowContext workFlowContext) throws WorkFlowException {
        doBusinessProcess(workFlowContext);
        setSourceStatus(workFlowContext);
        setDestStatus(workFlowContext);

        return workFlowContext.getInstruction();
    }


    /**
     * 抽象业务处理逻辑
     * @param workFlowContext
     * @return
     */
    public abstract WorkFlowContext doBusinessProcess(WorkFlowContext workFlowContext);

    /**
     * 设置路由原始状态
     * @param workFlowContext
     * @return
     */
    public abstract WorkFlowContext setSourceStatus(WorkFlowContext workFlowContext);

    /**
     * 设置路由目标状态
     * @param workFlowContext
     * @return
     */
    public abstract WorkFlowContext setDestStatus(WorkFlowContext workFlowContext);
}