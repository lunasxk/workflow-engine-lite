package com.toolkits.workflow.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.toolkits.workflow.model.config.WorkFlowConfig;

/**
 * @author XiaokaiSun
 * @version $Id: ConfigLoaderTest.java, v 0.1 2017-11-27 16:54 XiaokaiSun Exp $$
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfigLoaderTest {

    @Test
    public void testNormalLoader(){
        WorkFlowConfig workFlowConfig = ConfigLoader.loadByContent(genXmlNormal());
        Assert.assertNotNull(workFlowConfig);
    }

    @Test(expected = Exception.class)
    public void testErrorLoader(){
        WorkFlowConfig workFlowConfig = ConfigLoader.loadByContent(genXmlError());

    }


    /**
     * 生成xml数据
     * @return
     */
    private String genXmlNormal(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<workFlowConfig id=\"demo\" desc=\"demo流程定义\">\n" +
                "\n" +
                "    <propeties>\n" +
                "        <key>value</key>\n" +
                "    </propeties>\n" +
                "\n" +
                "    <process-list>\n" +
                "        <process id=\"PRO-NULL-AC\" sourceStatus=\"NULL\" destStatus=\"AC\" router=\"正常业务路由器\" failrouter=\"业务失败路由\"\n" +
                "                 desc=\"请求受理流程\">\n" +
                "            <action-list>\n" +
                "                <action seq=\"1\" ref=\"actionBeanName\" desc=\"操作描述1\"/>\n" +
                "                <action seq=\"2\" ref=\"actionBeanName\" desc=\"操作描述2\"/>\n" +
                "                <action seq=\"3\" ref=\"actionBeanName\" desc=\"操作描述3\"/>\n" +
                "                <action seq=\"4\" ref=\"actionBeanName\" desc=\"操作描述4\"/>\n" +
                "                <action seq=\"5\" ref=\"actionBeanName\" desc=\"操作描述5\"/>\n" +
                "            </action-list>\n" +
                "        </process>\n" +
                "\n" +
                "        <process id=\"PRO-AC-SM\" sourceStatus=\"AC\" destStatus=\"SM\" next=\"下一个Process\" failrouter=\"业务失败路由\" desc=\"请求受理流程\">\n" +
                "            <action-list>\n" +
                "                <action seq=\"1\" ref=\"actionBeanName\" desc=\"操作描述1\"/>\n" +
                "                <action seq=\"2\" ref=\"updateDbAction\" isInTrans=\"true\" desc=\"操作描述2\"/>\n" +
                "            </action-list>\n" +
                "        </process>\n" +
                "    </process-list>\n" +
                "</workFlowConfig>";
    }

    /**
     * 生成xml数据
     * @return
     */
    private String genXmlError(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<workFlowConfigs id=\"demo\" desc=\"demo流程定义\">\n" +
                "\n" +
                "    <propetiess>\n" +
                "        <key>value</key>\n" +
                "    </propeties>\n" +
                "\n" +
                "    <process-list>\n" +
                "        <process id=\"PRO-NULL-AC\" sourceStatus=\"NULL\" destStatus=\"AC\" router=\"正常业务路由器\" failrouter=\"业务失败路由\"\n" +
                "                 desc=\"请求受理流程\">\n" +
                "            <action-list>\n" +
                "                <action seq=\"1\" ref=\"actionBeanName\" desc=\"操作描述1\"/>\n" +
                "                <action seq=\"2\" ref=\"actionBeanName\" desc=\"操作描述2\"/>\n" +
                "                <action seq=\"3\" ref=\"actionBeanName\" desc=\"操作描述3\"/>\n" +
                "                <action seq=\"4\" ref=\"actionBeanName\" desc=\"操作描述4\"/>\n" +
                "                <action seq=\"5\" ref=\"actionBeanName\" desc=\"操作描述5\"/>\n" +
                "            </action-list>\n" +
                "        </process>\n" +
                "\n" +
                "        <process id=\"PRO-AC-SM\" sourceStatus=\"AC\" destStatus=\"SM\" router=\"正常业务路由器\" failrouter=\"业务失败路由\" desc=\"请求受理流程\">\n" +
                "            <action-list>\n" +
                "                <action seq=\"1\" ref=\"actionBeanName\" desc=\"操作描述1\"/>\n" +
                "                <action seq=\"2\" ref=\"updateDbAction\" isInTrans=\"true\" desc=\"操作描述2\"/>\n" +
                "            </action-list>\n" +
                "        </process>\n" +
                "    </process-list>\n" +
                "</workFlowConfigs>";
    }


}