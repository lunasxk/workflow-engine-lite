/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.integrationtest.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toolkits.workflow.Exception.BusinessActionException;
import com.toolkits.workflow.integrationtest.model.ProcessInstruction;
import com.toolkits.workflow.model.BusinessAction;
import com.toolkits.workflow.model.WorkFlowContext;
import com.toolkits.workflow.util.LoggerUtil;

/**
 * 模板action
 * @author XiaokaiSun
 * @version $Id: TemplateAction.java, v 0.1 2017-12-28 15:51 XiaokaiSun Exp $$
 */
public class TemplateAction implements BusinessAction {

    /** logger */
    public static final Logger logger      = LoggerFactory.getLogger("TEST-ACTION");

    /** 执行是否抛出异常 */
    private Boolean            needThrowEx = false;

    /**
     * 执行Action操作
     *
     * @param context
     * @throws BusinessActionException
     */
    @Override
    public void process(WorkFlowContext context) throws BusinessActionException {

        ProcessInstruction processInstruction = context.getInstruction(ProcessInstruction.class);

        LoggerUtil.info(logger, this.getClass().getSimpleName(), "收到请求:", processInstruction);

        LoggerUtil.info(logger, this.getClass().getSimpleName(), "处理中:~~~~~~");

        if (needThrowEx) {
            throw new BusinessActionException("action error", "action error");
        }

        LoggerUtil.info(logger, this.getClass().getSimpleName(), "处理结束");
    }


    /**
     * Getter method for property <tt>needThrowEx<tt>.
     *
     * @return property value of needThrowEx
     */
    public Boolean getNeedThrowEx() {
        return needThrowEx;
    }

    /**
     * Setter method for property <tt>needThrowEx<tt>.
     *
     * @param needThrowEx value to be assigned to property needThrowEx
     */
    public void setNeedThrowEx(Boolean needThrowEx) {
        this.needThrowEx = needThrowEx;
    }
}