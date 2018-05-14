/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model;

import com.google.common.base.MoreObjects;
import com.toolkits.workflow.Exception.BusinessActionException;
import com.toolkits.workflow.Exception.WorkFlowErrorCode;
import com.toolkits.workflow.model.runtime.RunTimeContextHolder;
import com.toolkits.workflow.model.runtime.WorkFlowRunTimeContext;

/**
 * 流程上下文定义
 * @author XiaokaiSun
 * @version $Id: ProcessContextImpl.java, v 0.1 2017-11-24 10:19 XiaokaiSun Exp $$
 */
public class WorkFlowContext {

    /** 流程指令 */
    private Instruction instruction;


    /**
     * 获取runtime阶段的上下文信息
     * @return
     */
    public WorkFlowRunTimeContext getRunTimeContext(){
        return RunTimeContextHolder.getRunTimeContext();
    }

    /**
     * 交易指令类型转换
     *
     * @param clazz
     * @return
     * @throws BusinessActionException
     */
    @SuppressWarnings("unchecked")
    public <T extends Instruction> T getInstruction(Class<T> clazz) throws BusinessActionException {
        if (instruction == null || null == clazz) {
            return null;
        }
        try {
            return (T) instruction;
        } catch (ClassCastException e) {
            throw new BusinessActionException(WorkFlowErrorCode.INSTRUCTION_CAST_EXCEPTION, e);
        }
    }

    /**
     * 子模型类型转换
     *
     * @param clazz
     * @return
     * @throws BusinessActionException
     */
    @SuppressWarnings("unchecked")
    public <T extends ModelAware> T getModelAware(Class<T> clazz) throws BusinessActionException {
        if (instruction == null || null == clazz) {
            return null;
        }
        try {
            return (T) instruction;
        } catch (ClassCastException e) {
            throw new BusinessActionException(WorkFlowErrorCode.INSTRUCTION_CAST_EXCEPTION, e);
        }
    }

    /**
     * 获取流程指令
     *
     * @return
     */
    public Instruction getInstruction() {
        return this.instruction;
    }

    /**
     * Setter method for property <tt>instruction<tt>.
     *
     * @param instruction value to be assigned to property instruction
     */
    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }


    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("instruction", instruction)
                .add("runTimeContext", this.getRunTimeContext())
                .toString();
    }
}