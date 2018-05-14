/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model.runtime;

import com.toolkits.workflow.model.BusinessAction;
import com.toolkits.workflow.model.ProcessRouter;

/**
 * 流程引擎动态上下文
 * @author XiaokaiSun
 * @version $Id: ProcessRunTimeContext.java, v 0.1 2017-11-24 14:31 XiaokaiSun Exp $$
 */
public class WorkFlowRunTimeContext {

    private String         workFlowId;

    /** 子流程Id */
    private String         proceccId;

    /** 子流程描述 */
    private String         processDesc;

    /** 失败路由流程 */
    private String         failRouter;

    /** 失败router bean */
    private ProcessRouter  failRouterBean;

    /** 正常路由器 */
    private String         router;

    /** 正常路由bean */
    private ProcessRouter  routerBean;

    /** -------------action上下文信息-------------- */

    /** action sequence */
    private int            actionSeq;

    /** 具体的action bean */
    private BusinessAction businessAction;

    /** action描述 */
    private String         actionDesc;

    /** 是否是最后一个Action */
    private Boolean        isLastAction     = false;

    /** -------------process 上下文信息-------------- */

    /** process是否执行成功 */
    private Boolean        isProcessSuccess = false;

    /** 是否执行异常 */
    private Boolean        isError          = false;

    /** 业务异常的异常码 */
    private String         errorCode;

    /** 错误堆栈 */
    private Exception      exception;

    /**
     * 重置流程引擎运行时的上下文信息
     */
    public void resetProcess() {
        this.isProcessSuccess = false;
        this.isError = false;
        this.errorCode = null;
        this.exception = null;
    }

    /**
     * Getter method for property <tt>workFlowId<tt>.
     *
     * @return property value of workFlowId
     */
    public String getWorkFlowId() {
        return workFlowId;
    }

    /**
     * Setter method for property <tt>workFlowId<tt>.
     *
     * @param workFlowId value to be assigned to property workFlowId
     */
    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    /**
     * Getter method for property <tt>proceccId<tt>.
     *
     * @return property value of proceccId
     */
    public String getProceccId() {
        return proceccId;
    }

    /**
     * Setter method for property <tt>proceccId<tt>.
     *
     * @param proceccId value to be assigned to property proceccId
     */
    public void setProceccId(String proceccId) {
        this.proceccId = proceccId;
    }

    /**
     * Getter method for property <tt>processDesc<tt>.
     *
     * @return property value of processDesc
     */
    public String getProcessDesc() {
        return processDesc;
    }

    /**
     * Setter method for property <tt>processDesc<tt>.
     *
     * @param processDesc value to be assigned to property processDesc
     */
    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }

    /**
     * Getter method for property <tt>failRouter<tt>.
     *
     * @return property value of failRouter
     */
    public String getFailRouter() {
        return failRouter;
    }

    /**
     * Setter method for property <tt>failRouter<tt>.
     *
     * @param failRouter value to be assigned to property failRouter
     */
    public void setFailRouter(String failRouter) {
        this.failRouter = failRouter;
    }

    /**
     * Getter method for property <tt>failRouterBean<tt>.
     *
     * @return property value of failRouterBean
     */
    public ProcessRouter getFailRouterBean() {
        return failRouterBean;
    }

    /**
     * Setter method for property <tt>failRouterBean<tt>.
     *
     * @param failRouterBean value to be assigned to property failRouterBean
     */
    public void setFailRouterBean(ProcessRouter failRouterBean) {
        this.failRouterBean = failRouterBean;
    }

    /**
     * Getter method for property <tt>router<tt>.
     *
     * @return property value of router
     */
    public String getRouter() {
        return router;
    }

    /**
     * Setter method for property <tt>router<tt>.
     *
     * @param router value to be assigned to property router
     */
    public void setRouter(String router) {
        this.router = router;
    }

    /**
     * Getter method for property <tt>routerBean<tt>.
     *
     * @return property value of routerBean
     */
    public ProcessRouter getRouterBean() {
        return routerBean;
    }

    /**
     * Setter method for property <tt>routerBean<tt>.
     *
     * @param routerBean value to be assigned to property routerBean
     */
    public void setRouterBean(ProcessRouter routerBean) {
        this.routerBean = routerBean;
    }

    /**
     * Getter method for property <tt>actionSeq<tt>.
     *
     * @return property value of actionSeq
     */
    public int getActionSeq() {
        return actionSeq;
    }

    /**
     * Setter method for property <tt>actionSeq<tt>.
     *
     * @param actionSeq value to be assigned to property actionSeq
     */
    public void setActionSeq(int actionSeq) {
        this.actionSeq = actionSeq;
    }


    /**
     * Getter method for property <tt>businessAction<tt>.
     *
     * @return property value of businessAction
     */
    public BusinessAction getBusinessAction() {
        return businessAction;
    }

    /**
     * Setter method for property <tt>businessAction<tt>.
     *
     * @param businessAction value to be assigned to property businessAction
     */
    public void setBusinessAction(BusinessAction businessAction) {
        this.businessAction = businessAction;
    }

    /**
     * Getter method for property <tt>actionDesc<tt>.
     *
     * @return property value of actionDesc
     */
    public String getActionDesc() {
        return actionDesc;
    }

    /**
     * Setter method for property <tt>actionDesc<tt>.
     *
     * @param actionDesc value to be assigned to property actionDesc
     */
    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    /**
     * Getter method for property <tt>isLastAction<tt>.
     *
     * @return property value of isLastAction
     */
    public Boolean getLastAction() {
        return isLastAction;
    }

    /**
     * Setter method for property <tt>isLastAction<tt>.
     *
     * @param lastAction value to be assigned to property isLastAction
     */
    public void setLastAction(Boolean lastAction) {
        isLastAction = lastAction;
    }

    /**
     * Getter method for property <tt>isProcessSuccess<tt>.
     *
     * @return property value of isProcessSuccess
     */
    public Boolean getProcessSuccess() {
        return isProcessSuccess;
    }

    /**
     * Setter method for property <tt>isProcessSuccess<tt>.
     *
     * @param processSuccess value to be assigned to property isProcessSuccess
     */
    public void setProcessSuccess(Boolean processSuccess) {
        isProcessSuccess = processSuccess;
    }

    /**
     * Getter method for property <tt>isError<tt>.
     *
     * @return property value of isError
     */
    public Boolean getError() {
        return isError;
    }

    /**
     * Setter method for property <tt>isError<tt>.
     *
     * @param error value to be assigned to property isError
     */
    public void setError(Boolean error) {
        isError = error;
    }

    /**
     * Getter method for property <tt>errorCode<tt>.
     *
     * @return property value of errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Setter method for property <tt>errorCode<tt>.
     *
     * @param errorCode value to be assigned to property errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>exception<tt>.
     *
     * @return property value of exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Setter method for property <tt>exception<tt>.
     *
     * @param exception value to be assigned to property exception
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WorkFlowRunTimeContext{");
        sb.append("workFlowId='").append(workFlowId).append('\'');
        sb.append(", proceccId='").append(proceccId).append('\'');
        sb.append(", processDesc='").append(processDesc).append('\'');
        sb.append(", failRouter='").append(failRouter).append('\'');
        sb.append(", failRouterBean=").append(failRouterBean);
        sb.append(", router='").append(router).append('\'');
        sb.append(", routerBean=").append(routerBean);
        sb.append(", actionSeq=").append(actionSeq);
        sb.append(", businessAction=").append(businessAction);
        sb.append(", actionDesc='").append(actionDesc).append('\'');
        sb.append(", isLastAction=").append(isLastAction);
        sb.append(", isProcessSuccess=").append(isProcessSuccess);
        sb.append(", isError=").append(isError);
        sb.append(", errorCode='").append(errorCode).append('\'');
        sb.append(", exception=").append(exception);
        sb.append('}');
        return sb.toString();
    }
}