package com.sxj.supervisor.fsm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.statemachine.interfaces.IStateMachine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class FsmTest
{
    
    @Autowired
    @Qualifier("payStagefsm")
    private IStateMachine fsm;
    
    @Test
    public void testPayStageFsm()
    {
    
    }
    
}
