/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.toolkits.workflow.Exception.WorkFlowErrorCode;
import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.model.ProcessRouter;
import com.toolkits.workflow.spi.ComponentFetcher;
import com.toolkits.workflow.util.StringUtil;

/**
 * 一个状态跳转的工作流定义
 * @author XiaokaiSun
 * @version $Id: Process.java, v 0.1 2017-11-24 15:47 XiaokaiSun Exp $$
 */
public class Process implements Initable {

    /** 状态跳转处理流程Id */
    private String        id;

    /** 描述 */
    private String        desc;

    /** 源状态 */
    private String        sourceStatus;

    /** 目标状态 */
    private String        destStatus;

    /** 正常业务路由器 */
    private String        routerName;

    /** router的bean */
    private ProcessRouter router;

    /** 处理异常路由器 */
    private String        failRouterName;

    /** 处理异常的路由器bean */
    private ProcessRouter failRouter;

    /** 状态流程包含的Action */
    private List<Action>  actionList = new ArrayList<>();

    /** 下一个Process Id, 若配置存在nextProcessId配置，则成功后直接进入下一个流程*/
    private String        nextProcessId;

    /** 下一个流程 */
    private Process       nextProcess;

    /**
     * 初始化统一接口，
     *
     * @param componentFetcher 获取对应Component的的bean
     */
    @Override
    public void init(ComponentFetcher componentFetcher) {
        /** 初始化正常router */
        if (StringUtil.isNotBlank(routerName)) {
            this.router = getByName(componentFetcher, routerName);
        }

        /** 初始化异常router */
        if (StringUtil.isNotBlank(failRouterName)) {
            this.failRouter = getByName(componentFetcher, failRouterName);
        }

        /** 初始化Action */
        for (Action action : actionList) {
            action.init(componentFetcher);
        }

        /** Action排序 */
        Collections.sort(this.actionList, new SortableComparator());
    }

    /**
     * 根据beanName获取对应的bean
     * @param componentFetcher
     * @param name
     * @return
     */
    private ProcessRouter getByName(ComponentFetcher componentFetcher, String name) {
        ProcessRouter rtn = componentFetcher.getRouterByName(name);
        if (rtn != null) {
            return rtn;
        } else {
            throw new WorkFlowException(WorkFlowErrorCode.FETCH_BEAN_ERROR, name);
        }
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
     * Getter method for property <tt>sourceStatus<tt>.
     *
     * @return property value of sourceStatus
     */
    public String getSourceStatus() {
        return sourceStatus;
    }

    /**
     * Setter method for property <tt>sourceStatus<tt>.
     *
     * @param sourceStatus value to be assigned to property sourceStatus
     */
    public void setSourceStatus(String sourceStatus) {
        this.sourceStatus = sourceStatus;
    }

    /**
     * Getter method for property <tt>destStatus<tt>.
     *
     * @return property value of destStatus
     */
    public String getDestStatus() {
        return destStatus;
    }

    /**
     * Setter method for property <tt>destStatus<tt>.
     *
     * @param destStatus value to be assigned to property destStatus
     */
    public void setDestStatus(String destStatus) {
        this.destStatus = destStatus;
    }

    /**
     * Getter method for property <tt>routerName<tt>.
     *
     * @return property value of routerName
     */
    public String getRouterName() {
        return routerName;
    }

    /**
     * Setter method for property <tt>routerName<tt>.
     *
     * @param routerName value to be assigned to property routerName
     */
    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    /**
     * Getter method for property <tt>router<tt>.
     *
     * @return property value of router
     */
    public ProcessRouter getRouter() {
        return router;
    }

    /**
     * Setter method for property <tt>router<tt>.
     *
     * @param router value to be assigned to property router
     */
    public void setRouter(ProcessRouter router) {
        this.router = router;
    }

    /**
     * Getter method for property <tt>failRouterName<tt>.
     *
     * @return property value of failRouterName
     */
    public String getFailRouterName() {
        return failRouterName;
    }

    /**
     * Setter method for property <tt>failRouterName<tt>.
     *
     * @param failRouterName value to be assigned to property failRouterName
     */
    public void setFailRouterName(String failRouterName) {
        this.failRouterName = failRouterName;
    }

    /**
     * Getter method for property <tt>failRouter<tt>.
     *
     * @return property value of failRouter
     */
    public ProcessRouter getFailRouter() {
        return failRouter;
    }

    /**
     * Setter method for property <tt>failRouter<tt>.
     *
     * @param failRouter value to be assigned to property failRouter
     */
    public void setFailRouter(ProcessRouter failRouter) {
        this.failRouter = failRouter;
    }

    /**
     * Getter method for property <tt>actionList<tt>.
     *
     * @return property value of actionList
     */
    public List<Action> getActionList() {
        return actionList;
    }

    /**
     * Setter method for property <tt>actionList<tt>.
     *
     * @param actionList value to be assigned to property actionList
     */
    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    /**
     * Getter method for property <tt>nextProcessId<tt>.
     *
     * @return property value of nextProcessId
     */
    public String getNextProcessId() {
        return nextProcessId;
    }

    /**
     * Setter method for property <tt>nextProcessId<tt>.
     *
     * @param nextProcessId value to be assigned to property nextProcessId
     */
    public void setNextProcessId(String nextProcessId) {
        this.nextProcessId = nextProcessId;
    }

    /**
     * Getter method for property <tt>nextProcess<tt>.
     *
     * @return property value of nextProcess
     */
    public Process getNextProcess() {
        return nextProcess;
    }

    /**
     * Setter method for property <tt>nextProcess<tt>.
     *
     * @param nextProcess value to be assigned to property nextProcess
     */
    public void setNextProcess(Process nextProcess) {
        this.nextProcess = nextProcess;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("desc", desc)
                .add("sourceStatus", sourceStatus)
                .add("destStatus", destStatus)
                .add("routerName", routerName)
                .add("failRouterName", failRouterName)
                .add("actionList", actionList)
                .add("nextProcessId", nextProcessId)
                .add("nextProcess", nextProcess)
                .toString();
    }
}