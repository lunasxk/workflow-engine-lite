/**
 * xkaisun@gmail.com
 * Copyright (c) 2013-2017 All Rights Reserved.
 */

package com.toolkits.workflow.integrationtest.testcase;

import javax.annotation.Resource;

import org.junit.Test;

import com.toolkits.workflow.Exception.BusinessActionException;
import com.toolkits.workflow.WorkFlowEngine;
import com.toolkits.workflow.integrationtest.BaseTest;
import com.toolkits.workflow.integrationtest.action.TemplateAction;
import com.toolkits.workflow.integrationtest.model.ProcessInstruction;
import com.toolkits.workflow.integrationtest.model.Transaction;

/**
 *
 * @author XiaokaiSun
 * @version $Id: WorkFlowTest.java, v 0.1 2017-12-28 18:15 XiaokaiSun Exp $$
 */
public class WorkFlowTest extends BaseTest {

    @Resource(name = "workFlowEngine")
    private WorkFlowEngine workFlowEngine;

    @Test
    public void testWorkFlowSuccess() {

        TemplateAction businessAction = (TemplateAction) applicationContext
                .getBean("saveTransactionAction");
        businessAction.setNeedThrowEx(false);

        TemplateAction businessAction1 = (TemplateAction) applicationContext
                .getBean("processAction1");
        businessAction1.setNeedThrowEx(false);

        ProcessInstruction instruction = genInstruction();

        workFlowEngine.launch(instruction);
    }

    @Test(expected = BusinessActionException.class)
    public void testWorkFlowAcceptError() {
        TemplateAction businessAction = (TemplateAction) applicationContext
            .getBean("saveTransactionAction");
        businessAction.setNeedThrowEx(true);

        ProcessInstruction instruction = genInstruction();

        workFlowEngine.launch(instruction);

    }

    /**
     * 走失败的逻辑分支
     */
    @Test(expected = BusinessActionException.class)
    public void testWorkflowFailBranch(){
        TemplateAction businessAction = (TemplateAction) applicationContext
                .getBean("processAction1");
        businessAction.setNeedThrowEx(true);

        ProcessInstruction instruction = genInstruction();

        workFlowEngine.launch(instruction);
    }

    /**
     * 生成指令
     * @return
     */
    private ProcessInstruction genInstruction(){
        ProcessInstruction instruction = new ProcessInstruction();

        instruction.setWorkFlowId("demo-template");
        instruction.setSourceStatus("NULL");
        instruction.setDestStatus("AC");

        Transaction transaction = new Transaction();
        transaction.setTransactionId(12345L);
        transaction.setBuyerId(123456L);
        transaction.setGoodsName("商品名称");

        instruction.setTransaction(transaction);

        return instruction;
    }

}