/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toolkits.workflow.Exception.WorkFlowErrorCode;
import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.spi.ComponentFetcher;
import com.toolkits.workflow.util.CommonUtil;
import com.toolkits.workflow.util.StringUtil;

/**
 * 工作流定义
 * @author XiaokaiSun
 * @version $Id: WorkFlowConfig.java, v 0.1 2017-11-24 15:40 XiaokaiSun Exp $$
 */
public class WorkFlowConfig implements Initable {

    /** WorkFlow对于的id */
    private String               id;

    /** 工作流描述 */
    private String               desc;

    /** 工作流属性 */
    private Map<String, String>  propeties     = new HashMap<>();

    /** 状态流程集合 */
    private List<Process>        processList;

    /** 状态流程Map; key: PRO-源状态-目标状态, 初始化后构建 */
    private Map<String, Process> processMap    = new HashMap<>();

    /** 是否初始化成功 */
    private Boolean              isInitSuccess = false;

    /**
     * 工作流初始化
     *
     * @param componentFetcher 获取对应Component的的bean
     */
    @Override
    public void init(ComponentFetcher componentFetcher) {
        initField();

        /** process初始化 */
        for (Process process : processList) {
            process.init(componentFetcher);
        }

        /** processMap构建 */
        for (Process process : processList) {
            processMap.put(process.getId(), process);
        }

        /** process 的 next 补全 */
        for (Process process : processList) {
            if (StringUtil.isNotBlank(process.getNextProcessId())) {
                Process nextProcess = processMap.get(process.getNextProcessId());
                if (nextProcess == null) {
                    throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_INIT_FAIL,
                        "无法找到next Process配置:" + process.toString());
                }
                process.setNextProcess(nextProcess);
            }
        }

        this.isInitSuccess = true;
    }

    /**
     * 根据原状态和目标状态获取对于的流程
     * @param sourceStatus 原状态
     * @param destStatus 目标状态
     * @return
     */
    public Process getByStatus(String sourceStatus, String destStatus) {
        /** 初始化检查 */
        if (!isInitSuccess()) {
            throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_INIT_FAIL);
        }

        String processKey = CommonUtil.genProcessKeyByStatus(sourceStatus, destStatus);

        Process rtn = processMap.get(processKey);

        if (rtn == null) {
            throw new WorkFlowException(WorkFlowErrorCode.PROCESS_NOT_EXIST_ERROR, processKey);
        }

        return rtn;
    }

    /**
     *  内部变量初始化，在xstream加载后，若无内部变量，则加载的内部对象默认为null
     */
    public void initField(){
        if (this.propeties == null) {
            this.propeties = new HashMap<>();
        }
        if (this.isInitSuccess == null) {
            this.isInitSuccess = false;
        }
        if (this.processMap == null) {
            this.processMap = new HashMap<>();
        }
    }

    /**
     * 根据key获取对应的属性
     * @param key
     * @return
     */
    public String getPropByKey(String key) {
        return this.propeties.get(key);
    }

    /**
     * Getter method for property <tt>isInitSuccess<tt>.
     *
     * @return property value of isInitSuccess
     */
    public Boolean isInitSuccess() {
        return (null != isInitSuccess && isInitSuccess);
    }

    /**
     * Getter method for property <tt>id<tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id<tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>desc<tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc<tt>.
     *
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>propeties<tt>.
     *
     * @return property value of propeties
     */
    public Map<String, String> getPropeties() {
        return propeties;
    }

    /**
     * Setter method for property <tt>propeties<tt>.
     *
     * @param propeties value to be assigned to property propeties
     */
    public void setPropeties(Map<String, String> propeties) {
        this.propeties = propeties;
    }

    /**
     * Getter method for property <tt>processList<tt>.
     *
     * @return property value of processList
     */
    public List<Process> getProcessList() {
        return processList;
    }

    /**
     * Setter method for property <tt>processList<tt>.
     *
     * @param processList value to be assigned to property processList
     */
    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }

    /**
     * Getter method for property <tt>processMap<tt>.
     *
     * @return property value of processMap
     */
    public Map<String, Process> getProcessMap() {
        return processMap;
    }

    /**
     * Setter method for property <tt>processMap<tt>.
     *
     * @param processMap value to be assigned to property processMap
     */
    public void setProcessMap(Map<String, Process> processMap) {
        this.processMap = processMap;
    }
}