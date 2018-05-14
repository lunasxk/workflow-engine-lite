/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.Exception;

/**
 * 流程引擎业务异常
 * @author XiaokaiSun
 * @version $Id: BusinessActionException.java, v 0.1 2017-11-23 20:56 XiaokaiSun Exp $$
 */
public class BusinessActionException extends WorkFlowException {

    /** serialVersionUID */
    private static final long serialVersionUID = 6784831744271813925L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param errorCode
     * @param message
     */
    public BusinessActionException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param errorCode
     * @param message
     * @param throwable
     */
    public BusinessActionException(String errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }

    /**
     * 枚举值作为入参的构造函数
     *
     * @param errorCodeEnum
     */
    public BusinessActionException(ProcessBaseEnum errorCodeEnum) {
        super(errorCodeEnum);
    }

    /**
     * 构造函数
     *
     * @param errorCodeEnum
     * @param throwable
     */
    public BusinessActionException(ProcessBaseEnum errorCodeEnum, Throwable throwable) {
        super(errorCodeEnum, throwable);
    }

    /**
     * 枚举值作为入参的构造函数
     *
     * @param errorCodeEnum 异常码枚举
     * @param bizMsg        错误业务信息
     */
    public BusinessActionException(ProcessBaseEnum errorCodeEnum, String bizMsg) {
        super(errorCodeEnum, bizMsg);
    }
}