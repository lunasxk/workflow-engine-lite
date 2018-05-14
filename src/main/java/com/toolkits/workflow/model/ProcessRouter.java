/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model;

import com.toolkits.workflow.Exception.WorkFlowException;

/**
 * 业务Router 接口
 * @author XiaokaiSun
 * @version $Id: ProcessRouter.java, v 0.1 2017-11-24 16:56 XiaokaiSun Exp $$
 */
public interface ProcessRouter {

    /**
     * 根据流程结果进行状态路由
     * @param workFlowContext 工作流上下文
     * @return
     * @throws WorkFlowException
     */
    public Instruction router(WorkFlowContext workFlowContext) throws WorkFlowException;
}