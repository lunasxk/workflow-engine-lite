/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.model.config;

import com.toolkits.workflow.spi.ComponentFetcher;

/**
 * 初始化统一接口定义
 * @author XiaokaiSun
 * @version $Id: Initable.java, v 0.1 2017-11-24 16:03 XiaokaiSun Exp $$
 */
public interface Initable {

    /**
     * 初始化统一接口，
     * @param componentFetcher 获取对应Component的的bean
     */
    public void init(ComponentFetcher componentFetcher);
}