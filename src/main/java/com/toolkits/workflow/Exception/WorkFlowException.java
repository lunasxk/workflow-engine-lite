/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.Exception;

/**
 * 流程引擎异常
 * @author XiaokaiSun
 * @version $Id: WorkFlowException.java, v 0.1 2017-11-23 20:36 XiaokaiSun Exp $$
 */
public class WorkFlowException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 4392851051182538730L;

    /** 错误码 */
    protected String          errorCode;

    /** 错误枚举代码 */
    protected ProcessBaseEnum errorCodeEnum;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public WorkFlowException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public WorkFlowException(String errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    /**
     * 枚举值作为入参的构造函数
     * @param errorCodeEnum
     */
    public WorkFlowException(ProcessBaseEnum errorCodeEnum) {
        super(errorCodeEnum.toLog());
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
    }

    /**
     * 枚举值作为入参的构造函数
     * @param errorCodeEnum 异常码枚举
     * @param bizMsg   错误业务信息
     */
    public WorkFlowException(ProcessBaseEnum errorCodeEnum, String bizMsg) {
        super(errorCodeEnum.toLog() + ":" + bizMsg);
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
    }

    /**
     * 构造函数
     * @param errorCodeEnum
     */
    public WorkFlowException(ProcessBaseEnum errorCodeEnum, Throwable throwable) {
        super(errorCodeEnum.toLog(), throwable);
        this.errorCodeEnum = errorCodeEnum;
        this.errorCode = errorCodeEnum.getCode();
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
     * Getter method for property <tt>errorCodeEnum<tt>.
     *
     * @return property value of errorCodeEnum
     */
    public ProcessBaseEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}