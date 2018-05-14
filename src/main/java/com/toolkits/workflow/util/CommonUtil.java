/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.util;

/**
 * 通用工具类
 * @author XiaokaiSun
 * @version $Id: CommonUtil.java, v 0.1 2017-11-24 16:09 XiaokaiSun Exp $$
 */
public class CommonUtil {

    public static final String PROCESS_ID_PREFIX = "PRO";

    /** key的分隔符 */
    public static final String KEY_SEP           = "-";

    /**
     *  根据SourceStatus和destStatus生成对于的processKey
     * @param source
     * @param dest
     * @return
     */
    public static String genProcessKeyByStatus(String source, String dest) {
        return new StringBuilder(PROCESS_ID_PREFIX).append(KEY_SEP).append(source.toUpperCase())
            .append(KEY_SEP).append(dest).toString();
    }
}