package com.toolkits.workflow.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.toolkits.workflow.Exception.WorkFlowException;
import com.toolkits.workflow.WorkFlowController;
import com.toolkits.workflow.model.AbstractInstruction;
import com.toolkits.workflow.model.Instruction;

/**
 * 
 * @author XiaokaiSun
 * @version $Id: WorkFlowEngineImplTest.java, v 0.1 2017-11-28 14:44 XiaokaiSun Exp $$
 */
@RunWith(MockitoJUnitRunner.class)
public class WorkFlowEngineImplTest {

    @Mock
    private WorkFlowController workFlowController;

    @InjectMocks
    private WorkFlowEngineImpl workFlowEngine;

    /**
     * 入参为 null
     */
    @Test(expected = WorkFlowException.class)
    public void testNullInput() {
        workFlowEngine.launch(null);
    }

    @Test
    public void testNormalInput() {
        Instruction instruction = genInstruction();
        workFlowEngine.launch(instruction);
    }

    /**
     * 生成instruction
     * @return
     */
    private Instruction genInstruction() {
        Instruction rtn = new AbstractInstruction();

        rtn.setSourceStatus("NULL");
        rtn.setDestStatus("AC");
        rtn.setWorkFlowId("demo");

        return rtn;
    }

}