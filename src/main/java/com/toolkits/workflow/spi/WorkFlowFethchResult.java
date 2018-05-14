/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.spi;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作流配置获取结果
 * 结果有两种情况
 *
 * 1. 返回的结果为本地 resource 路径，表示WorkFlow 配置的地址，为一个列表
 * 2. 返回的结果为配置文件的String list，这种情况外部应用可能通过db或者其他外部引用获取，
 * 
 * @author XiaokaiSun
 * @version $Id: WorkFlowFethchResult.java, v 0.1 2017-11-24 20:47 XiaokaiSun Exp $$
 */
public class WorkFlowFethchResult {

    /** 返回结果是否表示文件的本地路径，默认为本地resource路径 */
    private Boolean      isLocalPath = true;

    /** 返回结果列表 */
    private List<String> resultList  = new ArrayList<>();

    /**
     * Getter method for property <tt>isLocalPath<tt>.
     *
     * @return property value of isLocalPath
     */
    public Boolean isLocalPath() {
        return isLocalPath;
    }

    /**
     * Setter method for property <tt>isLocalPath<tt>.
     *
     * @param localPath value to be assigned to property isLocalPath
     */
    public void setLocalPath(Boolean localPath) {
        isLocalPath = localPath;
    }

    /**
     * Getter method for property <tt>resultList<tt>.
     *
     * @return property value of resultList
     */
    public List<String> getResultList() {
        return resultList;
    }

    /**
     * Setter method for property <tt>resultList<tt>.
     *
     * @param resultList value to be assigned to property resultList
     */
    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }
}