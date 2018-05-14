/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.integrationtest.router;

import org.springframework.stereotype.Component;

import com.toolkits.workflow.model.WorkFlowContext;
import com.toolkits.workflow.util.LoggerUtil;

/**
 *
 * @author XiaokaiSun
 * @version $Id: prSuccessRouter.java, v 0.1 2017-12-28 16:12 XiaokaiSun Exp $$
 */
@Component("prSuccessRouter")
public class PrSuccessRouter extends AcceptSuccessRouter {

    /**
     * 设置路由目标状态
     *
     * @param workFlowContext
     * @return
     */
    @Override
    public WorkFlowContext setDestStatus(WorkFlowContext workFlowContext) {
        workFlowContext.getInstruction().setDestStatus("SU");
        LoggerUtil.info(logger, this.getClass().getSimpleName(), "设置目标状态:", workFlowContext);

        return workFlowContext;
    }
}