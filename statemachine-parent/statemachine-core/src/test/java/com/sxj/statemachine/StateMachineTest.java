package com.sxj.statemachine;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import com.sxj.statemachine.exceptions.StateMachineException;
import com.sxj.statemachine.interfaces.IStateMachine;

public class StateMachineTest
{
    
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
        IStateMachine<DemoStates> machine = new StateMachineBuilder<DemoStates>().newNonReentrant(new StateMachineConfig());
        machine.fire("AtoB", null);
        machine.fire("BtoC", null);
        DemoStates currentState = machine.getCurrentState();
        System.out.println("==========" + currentState);
    }
    
}
