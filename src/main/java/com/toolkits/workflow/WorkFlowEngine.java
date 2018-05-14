/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow;

import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.model.Instruction;

/**
 * 流程引擎统一api
 * @author XiaokaiSun
 * @version $Id: WorkFlowEngine.java, v 0.1 2017-11-24 11:40 XiaokaiSun Exp $$
 */
public interface WorkFlowEngine {


    /**
     * 进入流程引擎
     * @param instruction
     */
    public void launch(Instruction instruction) throws WorkFlowException;
}