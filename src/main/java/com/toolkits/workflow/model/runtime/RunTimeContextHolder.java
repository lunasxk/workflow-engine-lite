/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model.runtime;

/**
 *
 * @author XiaokaiSun
 * @version $Id: RunTimeContextHolder.java, v 0.1 2017-11-24 14:48 XiaokaiSun Exp $$
 */
public class RunTimeContextHolder {

    /** 线程变量 */
    private final static ThreadLocal<WorkFlowRunTimeContext> threadLocal = new ThreadLocal<WorkFlowRunTimeContext>();

    /**
     * 创建 WorkFlowRunTimeContext
     * @return
     */
    public static WorkFlowRunTimeContext createWorkFlowRunTimeContext() {
        WorkFlowRunTimeContext runTimeContext = new WorkFlowRunTimeContext();
        threadLocal.set(runTimeContext);
        return runTimeContext;
    }

    /**
     * 获取WorkFlowRunTimeContext
     * @return
     */
    public static WorkFlowRunTimeContext getRunTimeContext() {
        return threadLocal.get();
    }

    /**
     * 清除环境变量
     */
    public static void cleanAll() {
        threadLocal.remove();
    }

}