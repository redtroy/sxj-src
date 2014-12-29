package com.sxj.statemachine.interfaces;

import com.sxj.statemachine.exceptions.StateMachineException;

public interface IStateMachine
{
    /**
     * Returns the current state of the state machine
     */
    public String getCurrentState();
    
    /**
     * Returns the state machine definition
     */
    public StateMachineDefinition getDefinition();
    
    /**
     * Consumes an event following the selected strategy.
     */
    public void fire(String event, Object object) throws StateMachineException;
}
