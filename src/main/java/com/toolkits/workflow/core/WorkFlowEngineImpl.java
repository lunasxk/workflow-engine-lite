/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.core;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.toolkits.workflow.Exception.WorkFlowErrorCode;
import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.WorkFlowController;
import com.toolkits.workflow.WorkFlowEngine;
import com.toolkits.workflow.model.Instruction;
import com.toolkits.workflow.model.WorkFlowContext;
import com.toolkits.workflow.model.runtime.RunTimeContextHolder;
import com.toolkits.workflow.util.LoggerUtil;
import com.toolkits.workflow.util.Profiler;

/**
 * 工作流引擎实现
 * @author XiaokaiSun
 * @version $Id: WorkFlowEngineImpl.java, v 0.1 2017-11-24 11:42 XiaokaiSun Exp $$
 */
@Component("workFlowEngine")
public class WorkFlowEngineImpl implements WorkFlowEngine {

    /** logger */
    private static final Logger logger         = LoggerFactory.getLogger("WORKFLOW-CORE");

    /** logger */
    private static final Logger prifilerLogger = LoggerFactory.getLogger("WORKFLOW-PROFILER");

    /** 流程控制器 */
    @Resource(name = "workFlowController")
    private WorkFlowController  workFlowController;

    /**
     * 进入流程引擎
     *
     * @param instruction
     */
    @Override
    public void launch(Instruction instruction) throws WorkFlowException {
        if (instruction == null) {
            throw new WorkFlowException(WorkFlowErrorCode.INSTRUCTION_NULL_EXCEPTION);
        }

        LoggerUtil.info(logger, "[", instruction.getWorkFlowId(), ",",
            instruction.getSourceStatus(), "-", instruction.getDestStatus(), "]流程进入:instruction=",
            instruction);

        /** 构建流程控制上下文 */
        WorkFlowContext workFlowContext = genWorkFlowContext(instruction);

        /** 构建运行时上下文 */
        RunTimeContextHolder.createWorkFlowRunTimeContext();

        try {
            Profiler.start("开始流程:workFlowId=" + instruction.getWorkFlowId());

            workFlowController.process(workFlowContext, null);

        } catch (WorkFlowException e) {

            throw e;

        } catch (Exception e) {
            LoggerUtil.error(logger, "流程执行发生未知异常:instruction=", instruction, " workFlowContext=",
                workFlowContext, " exception=", e);

            throw new WorkFlowException(WorkFlowErrorCode.SYSTEM_ERROR, e);
        } finally {

            RunTimeContextHolder.cleanAll();

            Profiler.release();

            LoggerUtil.info(prifilerLogger, Profiler.dump());

            Profiler.reset();
        }
    }

    /**
     * Setter method for property <tt>processorController<tt>.
     *
     * @param workFlowController value to be assigned to property processorController
     */
    public void setWorkFlowController(WorkFlowController workFlowController) {
        this.workFlowController = workFlowController;
    }

    /***
     * 生成流程控制的上下文
     * @return
     * @throws WorkFlowException
     */
    private WorkFlowContext genWorkFlowContext(Instruction instruction) throws WorkFlowException {

        WorkFlowContext rtn = new WorkFlowContext();
        rtn.setInstruction(instruction);
        return rtn;
    }

}