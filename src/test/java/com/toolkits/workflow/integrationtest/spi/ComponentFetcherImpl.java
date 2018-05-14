/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.integrationtest.spi;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.toolkits.workflow.model.BusinessAction;
import com.toolkits.workflow.model.ProcessRouter;
import com.toolkits.workflow.spi.ComponentFetcher;

/**
 *
 * @author XiaokaiSun
 * @version $Id: ComponentFetcherImpl.java, v 0.1 2017-12-28 17:12 XiaokaiSun Exp $$
 */
@Component("componentFetcher")
public class ComponentFetcherImpl implements ComponentFetcher, ApplicationContextAware {

    /** spring上下文 */
    private ApplicationContext applicationContext;

    /**
     * 根据bean的名字获取对应的bean实例
     *
     * @param beanName bean名字
     * @return
     */
    @Override
    public BusinessAction getActionByName(String beanName) {
        return (BusinessAction) applicationContext.getBean(beanName);
    }

    /**
     * 根据 bean的名字获取对应的bean实例
     *
     * @param beanName bean名字
     * @return
     */
    @Override
    public ProcessRouter getRouterByName(String beanName) {
        return (ProcessRouter) applicationContext.getBean(beanName);
    }

    /**
     * 设置环境变量
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}