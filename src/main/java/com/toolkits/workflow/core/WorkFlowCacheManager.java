/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.core;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.toolkits.workflow.Exception.WorkFlowErrorCode;
import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.model.config.Process;
import com.toolkits.workflow.model.config.WorkFlowConfig;
import com.toolkits.workflow.spi.ComponentFetcher;
import com.toolkits.workflow.spi.WorkFlowFethchResult;
import com.toolkits.workflow.spi.WorkFlowTemplateFetcher;
import com.toolkits.workflow.util.StringUtil;

/**
 * 工作流引擎缓存管理
 * @author XiaokaiSun
 * @version $Id: WorkFlowCacheManager.java, v 0.1 2017-11-24 18:03 XiaokaiSun Exp $$
 */
@Component("workFlowCacheManager")
public class WorkFlowCacheManager implements InitializingBean {

    /** logger */
    private static final Logger         logger    = LoggerFactory.getLogger("WORKFLOW-CACHE");

    /** 流程引擎配置获取器 */
    @Resource(name = "workFlowTemplateFetcher")
    private WorkFlowTemplateFetcher     workFlowTemplateFetcher;

    /** 组件获取器 */
    @Resource(name = "componentFetcher")
    private ComponentFetcher            componentFetcher;

    /** 流程引擎配置 */
    private WorkFlowFethchResult        workFlowFethchResult;

    /** 流程引擎配置表，key：workFlowId */
    private Map<String, WorkFlowConfig> configMap = new HashMap<>();

    /** 是否初始化成功 */
    private Boolean                     initOk    = false;

    /**
     * 是否初始化成功
     * @return
     */
    public boolean isInitOk() {
        return this.initOk;
    }

    /**
     * 根据WorkFlowId获取对应的流程配置
     * @param workFlowId
     * @return
     */
    public WorkFlowConfig getById(String workFlowId) throws WorkFlowException {
        WorkFlowConfig rtn = configMap.get(workFlowId);
        if (rtn == null) {
            throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_CONFIG_MISS_ERROR, workFlowId);
        }

        return rtn;
    }

    /**
     * 根据流程Id 和 状态机原状态，目标状态 获取对于的流程配置
     * @param workFlowId 工作流Id
     * @param sourceStatus 原状态
     * @param destStatus 目标状态
     * @return
     */
    public Process getProcessConfig(String workFlowId, String sourceStatus, String destStatus) {
        /** 配置强校验,出现配置错误强制指定整个流程都不能使用, 确保流程正常 */
        if (!isInitOk()) {
            throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_INIT_FAIL);
        }

        if (StringUtil.isBlank(workFlowId) || StringUtil.isBlank(sourceStatus)
            || StringUtil.isBlank(destStatus)) {
            throw new WorkFlowException(WorkFlowErrorCode.INSTRUCTION_CONFIG_MISMATCH_ERROR,
                workFlowId + ", " + sourceStatus + ", " + destStatus);
        }

        Process processConfig = configMap.get(workFlowId).getByStatus(sourceStatus, destStatus);

        if (processConfig == null) {
            throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_CONFIG_MISS_ERROR,
                workFlowId + ", " + sourceStatus + ", " + destStatus);
        }

        return processConfig;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        beanCheck();

        this.init();
    }

    /**
     * 检查应用是否实现spi的组件获取器和流程配置获取器
     */
    private void beanCheck() {
        if (workFlowTemplateFetcher == null) {
            logger
                .error("workFlowTemplateFetcher null error, please check WorkFlowTemplateFetcher interface implemention");
            throw new WorkFlowException(WorkFlowErrorCode.SPI_IMPL_INJECT_ERROR);
        }

        if (componentFetcher == null) {
            logger
                .error("componentFetcher null error, please check ComponentFetcher interface implemention");
            throw new WorkFlowException(WorkFlowErrorCode.SPI_IMPL_INJECT_ERROR);
        }
    }

    /**
     * 配置初始化
     */
    public void init() {
        WorkFlowFethchResult fethchResult = workFlowTemplateFetcher.getAllWorkFlowConfig();
        if (fethchResult == null) {
            logger.error("workFlowFethchResult null error");
            throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_CONFIG_FETCH_ERROR);
        }
        this.workFlowFethchResult = fethchResult;

        try {
            for (String item : fethchResult.getResultList()) {
                WorkFlowConfig tmp = null;
                if (fethchResult.isLocalPath()) {
                    tmp = ConfigLoader.loadByPath(item);
                } else {
                    tmp = ConfigLoader.loadByContent(item);
                }
                this.configMap.put(tmp.getId(), tmp);
            }

            /** 初始化各模板 */
            for (WorkFlowConfig workFlowConfig : configMap.values()) {
                workFlowConfig.init(this.componentFetcher);
            }
            this.initOk = true;

        } catch (WorkFlowException e) {
            logger.error("Init cache error", e);
        } catch (Exception e) {
            logger.error("Init cache error", e);
        }

    }

    /**
     * Setter method for property <tt>workFlowTemplateFetcher<tt>.
     *
     * @param workFlowTemplateFetcher value to be assigned to property workFlowTemplateFetcher
     */
    public void setWorkFlowTemplateFetcher(WorkFlowTemplateFetcher workFlowTemplateFetcher) {
        this.workFlowTemplateFetcher = workFlowTemplateFetcher;
    }

    /**
     * Setter method for property <tt>componentFetcher<tt>.
     *
     * @param componentFetcher value to be assigned to property componentFetcher
     */
    public void setComponentFetcher(ComponentFetcher componentFetcher) {
        this.componentFetcher = componentFetcher;
    }
}