/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.core;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.toolkits.workflow.Exception.BusinessActionException;
import com.toolkits.workflow.Exception.ProcessContinueException;
import com.toolkits.workflow.Exception.WorkFlowErrorCode;
import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.WorkFlowController;
import com.toolkits.workflow.model.Instruction;
import com.toolkits.workflow.model.ProcessRouter;
import com.toolkits.workflow.model.WorkFlowContext;
import com.toolkits.workflow.model.config.Action;
import com.toolkits.workflow.model.config.Process;
import com.toolkits.workflow.model.runtime.WorkFlowRunTimeContext;
import com.toolkits.workflow.util.LoggerUtil;
import com.toolkits.workflow.util.Profiler;
import com.toolkits.workflow.util.StringUtil;

/**
 * 流程控制器实现
 * @author XiaokaiSun
 * @version $Id: ProcessorControllerImpl.java, v 0.1 2017-11-24 14:02 XiaokaiSun Exp $$
 */
@Component("workFlowController")
public class WorkFlowControllerImpl implements WorkFlowController {

    /** logger */
    private static final Logger  logger = LoggerFactory.getLogger("WORKFLOW-CORE");

    /** 缓存管理器 */
    @Resource(name = "workFlowCacheManager")
    private WorkFlowCacheManager workFlowCacheManager;

    /** action执行器 */
    @Resource(name = "businessActionRunner")
    private BusinessActionRunner businessActionRunner;

    /**
     * 流程控制方法
     *
     * @param  workFlowContext
     * @param  processConfig process配置,
     * @throws WorkFlowException
     */
    @Override
    public void process(WorkFlowContext workFlowContext, Process processConfig)
                                                                               throws WorkFlowException {
        try {

            /** 若为空,则根据instruction构建processConfig */
            if (processConfig == null) {
                processConfig = getProcess(workFlowContext);
            }

            /** 性能统计 */
            Profiler.enter(processConfig.getId() + ":" + processConfig.getDesc());

            LoggerUtil.debug(logger, "获取process配置:processConfig=", processConfig);

            try {

                executeProcess(processConfig, workFlowContext);

            } catch (ProcessContinueException e) {
                LoggerUtil.info(logger, "Action 抛出 流程继续异常, 终止后面action执行, 执行router跳转");
            }

            /** action执行成功，执行路由逻辑 */
            doSuccessRouter(processConfig, workFlowContext);

        } catch (BusinessActionException e) {
            throw e;
        } catch (WorkFlowException e) {
            throw e;
        } catch (Exception e) {
            LoggerUtil.error(logger, "流程执行发生未知异常, workFlowContext:", workFlowContext,
                ", exception:", e);
            throw new WorkFlowException(WorkFlowErrorCode.SYSTEM_ERROR, e);
        } finally {
            Profiler.release();
        }

    }

    /**
     * 根据上下文获取Process的配置
     * @param workFlowContext
     * @return
     */
    private Process getProcess(WorkFlowContext workFlowContext) {
        Instruction instruction = workFlowContext.getInstruction();
        Process processConfig = workFlowCacheManager.getProcessConfig(instruction.getWorkFlowId(),
            instruction.getSourceStatus(), instruction.getDestStatus());

        /** 设置流程Id境变量 */
        workFlowContext.getRunTimeContext().setWorkFlowId(instruction.getWorkFlowId());

        return processConfig;
    }

    /**
     * 执行process过程
     * @param processConfig
     * @param workFlowContext
     */
    private void executeProcess(Process processConfig, WorkFlowContext workFlowContext) {
        WorkFlowRunTimeContext runTimeContext = workFlowContext.getRunTimeContext();

        /** 重置上下文信息,用于本流程 */
        runTimeContext.resetProcess();

        try {
            /** 启动子流程 */
            launchProcess(processConfig, workFlowContext);

            /** 没有抛出异常证明执行成功 */
            runTimeContext.setProcessSuccess(true);

        } catch (ProcessContinueException e) {
            throw e;
        } catch (BusinessActionException e) {
            /** 更新上下文 */
            runTimeContext.setErrorCode(e.getErrorCode());
            runTimeContext.setException(e);
            workFlowContext.getRunTimeContext().setError(true);
            LoggerUtil.recordErrorSnapShot(logger, runTimeContext);

            /** 错误路由 */
            this.processException(processConfig, workFlowContext);

            /** 抛出异常 */
            throw e;
        } catch (Exception e) {
            /** 更新上下文 */
            runTimeContext.setErrorCode(WorkFlowErrorCode.SYSTEM_ERROR.getCode());
            runTimeContext.setException(e);
            workFlowContext.getRunTimeContext().setError(true);
            LoggerUtil.recordErrorSnapShot(logger, runTimeContext);

            /** 错误路由 */
            this.processException(processConfig, workFlowContext);

            /** 抛出异常 */
            throw new WorkFlowException(WorkFlowErrorCode.SYSTEM_ERROR, e);
        }

    }

    /**
     * 发起子流程
     * @param processConfig
     * @param workFlowContext
     */
    private void launchProcess(Process processConfig, WorkFlowContext workFlowContext)
                                                                                      throws Exception {

        LoggerUtil.debug(logger, "流程[", processConfig.getId(), "]开始:processConfig=", processConfig,
            "  workFlowContext=", workFlowContext);

        WorkFlowRunTimeContext runTimeContext = workFlowContext.getRunTimeContext();
        int actionSize = processConfig.getActionList().size();

        /** 填充线程变量 */
        runTimeContext.setProceccId(processConfig.getId());
        runTimeContext.setProcessDesc(processConfig.getDesc());
        runTimeContext.setRouterBean(processConfig.getRouter());
        runTimeContext.setFailRouterBean(processConfig.getFailRouter());

        /** 顺序执行action */
        for (int i = 0; i < actionSize; i++) {
            Action actionConfig = processConfig.getActionList().get(i);

            runTimeContext.setActionSeq(i + 1);
            runTimeContext.setActionDesc(actionConfig.getDesc());
            runTimeContext.setBusinessAction(actionConfig.getBusinessAction());

            if (i == actionSize - 1) {
                runTimeContext.setLastAction(true);
            } else {
                runTimeContext.setLastAction(false);
            }

            businessActionRunner.runAction(actionConfig, workFlowContext);
        }
    }

    /**
     * 流程action序列执行成功后，执行成功router实现状态跳转
     * @param processConfig
     * @param workFlowContext
     */
    private void doSuccessRouter(Process processConfig, WorkFlowContext workFlowContext) {
        /** 优先执行存在next的配置 */
        if (StringUtil.isNotBlank(processConfig.getNextProcessId())) {
            Process next = processConfig.getNextProcess();
            if (next == null) {
                throw new WorkFlowException(WorkFlowErrorCode.INSTRUCTION_CONFIG_MISMATCH_ERROR,
                    processConfig.toString());
            }
            /** 开启下一个流程 */
            this.process(workFlowContext, next);

        } else {
            ProcessRouter router = processConfig.getRouter();
            try {
                Profiler.enter("Success-Router:"
                               + StringUtil.defaultIfBlank(processConfig.getRouterName(), "-"));
                if (router != null) {
                    LoggerUtil.debug(logger, "Router[", router.getClass().getSimpleName(),
                        "]开始:workFlowContext=", workFlowContext);

                    Instruction instruction = router.router(workFlowContext);
                    if (instruction == null) {
                        logger.error("交易指令为空, 不执行路由:", workFlowContext);
                    }

                    LoggerUtil.debug(logger, "Router[", router.getClass().getSimpleName(),
                        "]成功, 进入新流程:instruction=", instruction);

                    /** 设置新指令环境变量 */
                    workFlowContext.setInstruction(instruction);
                    /** 继续执行下一个process */
                    this.process(workFlowContext, null);
                } else {
                    LoggerUtil.info(logger, "Router [", processConfig.getId(),"] 流程结束");
                }
            } finally {
                Profiler.release();
            }

        }
    }

    /**
     * 处理异常
     * @param processConfig
     * @param workFlowContext
     */
    private void processException(Process processConfig, WorkFlowContext workFlowContext) {

        LoggerUtil.info(logger, "开始处理异常流程:workFlowContext=", workFlowContext, ", processConfig=",
            processConfig);

        try {
            Profiler.enter("Fail-Router:"
                           + StringUtil.defaultIfBlank(processConfig.getFailRouterName(), "-"));

            if (StringUtil.isNotBlank(processConfig.getFailRouterName())) {
                ProcessRouter failRouter = processConfig.getFailRouter();
                if (failRouter != null) {
                    Instruction instruction = failRouter.router(workFlowContext);
                    workFlowContext.setInstruction(instruction);
                    this.process(workFlowContext, null);
                }
            }
        } catch (Exception e) {
            LoggerUtil.error(logger, "处理异常流程异常:workFlowContext=", workFlowContext, ", exception=",
                e);
        } finally {
            Profiler.release();
            // todo XiaokaiSun  2017/11/28-11:50  性能统计
        }

    }

    /**
     * Setter method for property <tt>workFlowCacheManager<tt>.
     *
     * @param workFlowCacheManager value to be assigned to property workFlowCacheManager
     */
    public void setWorkFlowCacheManager(WorkFlowCacheManager workFlowCacheManager) {
        this.workFlowCacheManager = workFlowCacheManager;
    }

    /**
     * Setter method for property <tt>businessActionRunner<tt>.
     *
     * @param businessActionRunner value to be assigned to property businessActionRunner
     */
    public void setBusinessActionRunner(BusinessActionRunner businessActionRunner) {
        this.businessActionRunner = businessActionRunner;
    }
}