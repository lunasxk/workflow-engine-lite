/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model;

import com.toolkits.workflow.Exception.BusinessActionException;

/**
 * 业务处理Action-所有的业务逻辑都在此接口中实现
 * @author XiaokaiSun
 * @version $Id: BusinessAction.java, v 0.1 2017-11-24 15:16 XiaokaiSun Exp $$
 */
public interface BusinessAction {
    /**
     * 执行Action操作
     *
     * @param context
     * @throws BusinessActionException
     */
    public void process(WorkFlowContext context) throws BusinessActionException;
}