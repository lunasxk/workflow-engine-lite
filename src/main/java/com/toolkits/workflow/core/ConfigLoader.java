/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.toolkits.workflow.Exception.WorkFlowErrorCode;
import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.model.config.Action;
import com.toolkits.workflow.model.config.Process;
import com.toolkits.workflow.model.config.WorkFlowConfig;

/**
 * 配置加载器
 * @author XiaokaiSun
 * @version $Id: ConfigLoader.java, v 0.1 2017-11-24 18:04 XiaokaiSun Exp $$
 */

public class ConfigLoader {

    /** 资源加载器 */
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * 从对应路径加载配置文件，生成配置类
     * @param resourcePath
     * @return
     * @throws WorkFlowException
     */
    public static WorkFlowConfig loadByPath(String resourcePath) throws WorkFlowException {

        WorkFlowConfig rtn = null;
        try {
            XStream xStream = genXstream();
            InputStream inputStream = getInputStream(resourcePath);
            rtn = (WorkFlowConfig) xStream.fromXML(inputStream);
        } catch (WorkFlowException e) {
            throw e;
        } catch (Exception e) {
            throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_CONFIG_PARSE_ERROR, e);
        }

        return rtn;
    }

    /**
     * 基于配置文件内容，生成配置类
     * @param configContent
     * @return
     * @throws WorkFlowException
     */
    public static WorkFlowConfig loadByContent(String configContent) throws WorkFlowException {
        WorkFlowConfig rtn = null;
        try {
            XStream xStream = genXstream();
            rtn = (WorkFlowConfig) xStream.fromXML(configContent);
        } catch (WorkFlowException e) {
            throw e;
        } catch (Exception e) {
            throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_CONFIG_PARSE_ERROR, e);
        }

        return rtn;
    }

    /**
     * 加载流程配置文件
     * @param filePath
     * @return
     */
    public static InputStream getInputStream(String filePath) {
        try {
            Resource resource = resourceLoader.getResource(filePath);
            InputStream rtn = resource.getInputStream();

            if (rtn == null) {
                throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_CONFIG_LOAD_ERROR, filePath);
            }
            return rtn;
        } catch (IOException e) {

            throw new WorkFlowException(WorkFlowErrorCode.WORKFLOW_CONFIG_LOAD_ERROR, e);
        }
    }

    /**
     * 生成xstream对象，定义java对象与xml的字段映射关系
     * @return
     */
    public static XStream genXstream() {
        XStream configStream = new XStream(new StaxDriver());
        configStream.registerConverter(new MapEntryConverter());

        configStream.alias("workFlowConfig", WorkFlowConfig.class);
        configStream.alias("propeties", Map.class);
        configStream.aliasAttribute(WorkFlowConfig.class, "id", "id");
        configStream.aliasAttribute(WorkFlowConfig.class, "desc", "desc");
        configStream.aliasField("process-list", WorkFlowConfig.class, "processList");
        configStream.aliasField("propeties", WorkFlowConfig.class, "propeties");
        configStream.aliasField("isInitSuccess", WorkFlowConfig.class, "isInitSuccess");
        configStream.omitField(WorkFlowConfig.class, "processMap");

        configStream.alias("process", Process.class);
        configStream.aliasAttribute(Process.class, "id", "id");
        configStream.aliasAttribute(Process.class, "desc", "desc");
        configStream.aliasAttribute(Process.class, "sourceStatus", "sourceStatus");
        configStream.aliasAttribute(Process.class, "destStatus", "destStatus");
        configStream.aliasAttribute(Process.class, "routerName", "router");
        configStream.aliasAttribute(Process.class, "failRouterName", "failrouter");
        configStream.aliasAttribute(Process.class, "nextProcessId", "next");
        configStream.aliasField("action-list", Process.class, "actionList");
        configStream.omitField(Process.class, "router");
        configStream.omitField(Process.class, "failRouter");

        configStream.alias("action", Action.class);
        configStream.aliasAttribute(Action.class, "sequence", "seq");
        configStream.aliasAttribute(Action.class, "refBeanName", "ref");
        configStream.aliasAttribute(Action.class, "desc", "desc");
        configStream.omitField(Action.class, "businessAction");

        return configStream;
    }

    /**
     * xstream 自定义Converter
     */
    public static class MapEntryConverter implements Converter {

        @Override
        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        @Override
        public void marshal(Object value, HierarchicalStreamWriter writer,
                            MarshallingContext context) {

            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                Object val = entry.getValue();
                if (null != val) {
                    writer.setValue(val.toString());
                }
                writer.endNode();
            }

        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

            Map<String, String> map = new HashMap<String, String>();

            while (reader.hasMoreChildren()) {
                reader.moveDown();

                String key = reader.getNodeName(); // nodeName aka element's name
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }
    }
}