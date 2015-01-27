package com.sxj.supervisor.service.statemachine;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.statemachine.StateMachineImpl;
import com.sxj.statemachine.exceptions.StateMachineException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(defaultRollback = false)
public class StateMachineTest
{
    @Autowired
    @Qualifier("fsm")
    StateMachineImpl<DemoStates> fsm;
    
    @Autowired
    ApplicationContext context;
    
    @Autowired
    StateMachineConfig config;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    @Transactional
    public void test() throws StateMachineException
    {
        //        MemberServiceImpl bean = context.getBean(MemberServiceImpl.class);
        //        DemoService bean2 = context.getBean(DemoService.class);
        //        StateMachineConfig bean3 = context.getBean(StateMachineConfig.class);
        //        //        bean2.exitB(null);
        //        bean.addMember(null);
        //        fsm.fire("AtoB", null);
        config.exitB(null);
        fsm.setCurrentState(DemoStates.B);
        fsm.fire("BtoC", "8RUlcJhqdAH2c60o1qNG3kCbJvGP40AT");
        System.out.println(fsm.getCurrentState());
        org.junit.Assert.assertEquals(DemoStates.C, fsm.getCurrentState());
    }
}
