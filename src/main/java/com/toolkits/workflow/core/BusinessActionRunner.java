/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.toolkits.workflow.model.WorkFlowContext;
import com.toolkits.workflow.model.config.Action;
import com.toolkits.workflow.util.LoggerUtil;
import com.toolkits.workflow.util.Profiler;

/**
 * action执行器
 * @author XiaokaiSun
 * @version $Id: BusinessActionRunner.java, v 0.1 2017-11-27 20:22 XiaokaiSun Exp $$
 */
@Component("businessActionRunner")
public class BusinessActionRunner {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger("WORKFLOW-CORE");

    /**
     * 执行action
     * @param actionConfig
     * @param workFlowContext
     */
    public void runAction(Action actionConfig, WorkFlowContext workFlowContext) throws Exception {
        try {
            Profiler.enter(actionConfig.getDesc() + ":" + actionConfig.getRefBeanName());

            LoggerUtil.debug(logger, "action[", actionConfig.getSeq(), ",",
                actionConfig.getRefBeanName(), "]开始:actionConfig=", actionConfig,
                " workFlowContext=", workFlowContext);

            actionConfig.getBusinessAction().process(workFlowContext);
        } finally {
            Profiler.release();
        }
    }

}