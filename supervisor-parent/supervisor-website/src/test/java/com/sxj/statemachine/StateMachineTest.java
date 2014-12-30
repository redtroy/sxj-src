package com.sxj.statemachine;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sxj.statemachine.exceptions.StateMachineException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml" })
public class StateMachineTest
{
    @Autowired
    StateMachineImpl<DemoStates> fsm;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test() throws StateMachineException
    {
        fsm.fire("AtoB", null);
        org.junit.Assert.assertEquals(DemoStates.B, fsm.getCurrentState());
    }
    
}
