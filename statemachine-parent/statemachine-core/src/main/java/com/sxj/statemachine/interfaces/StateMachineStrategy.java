package com.sxj.statemachine.interfaces;

import com.sxj.statemachine.StateMachineImpl;
import com.sxj.statemachine.exceptions.StateMachineException;

public interface StateMachineStrategy
{
    /**
     * Checks that current event is allowed for the current state. That means that a
     * transition has been defined for this state machine.
     * 
     * If the transition has been defined, this method will perform:
     * 1 - execute the exit state phase
     * 2 - execute the transition phase
     * 3 - execute the enter state phase
     * 
     * Everything will happen with the state machine lock acquired, so careful with the
     * deadlocks.
     * 
     * @param event the event that we want to process.
     *  
     * @param object if we need an object to be passed to the controller with
     *        context meaning.
     * 
     * @throws EventNotDefinedException
     * @throws ReentrantTransitionNotAllowed
     * @throws TransitionNotDefinedException
     */
    public void processEvent(StateMachineImpl statemachine, String event,
            Object object) throws StateMachineException;
}
