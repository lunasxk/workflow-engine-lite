/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.integrationtest.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.toolkits.workflow.model.AbstractProcessRouter;
import com.toolkits.workflow.model.WorkFlowContext;
import com.toolkits.workflow.util.LoggerUtil;

/**
 * AcceptSuccessRouter
 * @author XiaokaiSun
 * @version $Id: AcceptSuccessRouter.java, v 0.1 2017-12-28 15:40 XiaokaiSun Exp $$
 */
@Component("acceptSuccessRouter")
public class AcceptSuccessRouter extends AbstractProcessRouter {

    /** logger */
    public static final Logger logger = LoggerFactory.getLogger("TEST-ROUTER");

    /**
     * 抽象业务处理逻辑
     *
     * @param workFlowContext
     * @return
     */
    @Override
    public WorkFlowContext doBusinessProcess(WorkFlowContext workFlowContext) {
        LoggerUtil.info(logger, this.getClass().getSimpleName(), "业务处理中 ");

        return workFlowContext;
    }

    /**
     * 设置路由原始状态
     *
     * @param workFlowContext
     * @return
     */
    @Override
    public WorkFlowContext setSourceStatus(WorkFlowContext workFlowContext) {
        workFlowContext.getInstruction().setSourceStatus(
            workFlowContext.getInstruction().getDestStatus());

        LoggerUtil.info(logger, this.getClass().getSimpleName(), "设置原状态:", workFlowContext);

        return workFlowContext;
    }

    /**
     * 设置路由目标状态
     *
     * @param workFlowContext
     * @return
     */
    @Override
    public WorkFlowContext setDestStatus(WorkFlowContext workFlowContext) {
        workFlowContext.getInstruction().setDestStatus("PR");
        LoggerUtil.info(logger, this.getClass().getSimpleName(), "设置目标状态:", workFlowContext);
        return workFlowContext;
    }
}