/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.Exception;


/**
 * 流程引擎错误码定义
 * @author XiaokaiSun
 * @version $Id: WorkFlowErrorCode.java, v 0.1 2017-11-24 10:10 XiaokaiSun Exp $$
 */
public enum WorkFlowErrorCode implements ProcessBaseEnum {

    /** 用于应用跳过当前状跳转process的后续Action， 继续执行正常的router */
    PROCESS_CONTINUE_EXCEPTION("W000", "流程继续异常"),

    /** 交易指令转换异常 */
    INSTRUCTION_CAST_EXCEPTION("W001", "交易指令转换异常"),

    /** 输入流程控制指令为null异常 */
    INSTRUCTION_NULL_EXCEPTION("W002", "输入流程控制指令为null"),

    /** 系统异常 */
    SYSTEM_ERROR("W003", "系统异常"),

    /** 流程引擎初始化异常 */
    WORKFLOW_INIT_FAIL("W004", "流程引擎初始化异常"),

    /** 流程不存在 */
    PROCESS_NOT_EXIST_ERROR("W005", "流程不存在"),

    /** 获取bean实例异常 */
    FETCH_BEAN_ERROR("W006", "根据beanName获取bean实例异常"),

    /** 找不到spi实现的bean */
    SPI_IMPL_INJECT_ERROR("W007", "SPI实现与组件注入异常"),

    /** 流程配置获取null异常 */
    WORKFLOW_CONFIG_FETCH_ERROR("W008", "流程配置为空异常"),

    /** 流程配置缺失异常 */
    WORKFLOW_CONFIG_MISS_ERROR("W009", "流程配置缺失异常"),

    /** 流程配置加载异常 */
    WORKFLOW_CONFIG_LOAD_ERROR("W010", "流程配置加载异常"),

    /** 流程配置解析异常 */
    WORKFLOW_CONFIG_PARSE_ERROR("W011", "流程配置解析异常"),

    /** 流程指令无法找到对应流程配置 */
    INSTRUCTION_CONFIG_MISMATCH_ERROR("W012", "流程指令无法找到对应流程配置"),

    ;

    /** 错误码 */
    private String errorCode;

    /** 错误描述 */
    private String message;

    /**
     * 构造函数
     * @param errorCode
     * @param message
     */
    WorkFlowErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 获取枚举码
     *
     * @return
     */
    @Override
    public String getCode() {
        return errorCode;
    }

    /**
     * 获取枚举描述
     *
     * @return
     */
    @Override
    public String getMsg() {
        return message;
    }

    /**
     * 生成log形式的记录
     *
     * @return
     */
    @Override
    public String toLog() {
        final StringBuilder sb = new StringBuilder("WorkFlowErrorCode{");
        sb.append("errorCode='").append(errorCode).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }

}