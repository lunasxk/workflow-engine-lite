/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.integrationtest.spi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.toolkits.workflow.spi.WorkFlowFethchResult;
import com.toolkits.workflow.spi.WorkFlowTemplateFetcher;

/**
 *
 * @author XiaokaiSun
 * @version $Id: WorkFlowTemplateFetcherImpl.java, v 0.1 2017-12-28 16:32 XiaokaiSun Exp $$
 */
@Component("workFlowTemplateFetcher")
public class WorkFlowTemplateFetcherImpl implements WorkFlowTemplateFetcher {

    /**
     * WorkFlowFethchResult
     * 获取全量流程配置文件或内容，具体结果返回情况可见 WorkFlowFethchResult
     *
     * @return
     */
    @Override
    public WorkFlowFethchResult getAllWorkFlowConfig() {
        WorkFlowFethchResult rtn = new WorkFlowFethchResult();
        List<String> templatePath = new ArrayList<>(1);
        templatePath.add("classpath:template/demo-template.xml");
        rtn.setResultList(templatePath);
        rtn.setLocalPath(true);

        return rtn;
    }
}