package com.sxj.statemachine.interfaces;

import java.util.List;
import java.util.Set;

import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.exceptions.StateMachineException;

/**
 * Allows defining the state machine. Read the README file for further
 * explanations about the finite state machine.
 * 
 * <p>
 * There are many reasons why this pattern is very useful. Keeping the right
 * state for an object can be tedious and error-prone if not using the right
 * mechanism specially in highly concurrent systems.
 * 
 * <p>
 * Constraints and invariants.
 * <ul>
 * <li>We are forced to declare explicitly states and events. We could try to
 * use a less explicit model, but we want to avoid typing errors and maintenance
 * ones.</li>
 * 
 * <li>We are forced to declare transitions. A transition is formed by 'source
 * state','target state' and 'event' tuple. The event provokes a transition to
 * happen from a source state to a target state.</li>
 * 
 * <li>Each transition is executed in 3 steps that allows us to keep a good
 * control of the actions to perform in each step: exiting the state, the
 * transition itself and entering a state. </li>
 * 
 * <li>Once defining any kind of transition phase, we need to define the controller
 * that will take responsibility for executing the required code. Check {@link EnterStateController},
 * {@link TransitionController} and {@link ExitStateController} for further details</li>
 * 
 * <li>We keep the definition of the state machine separated from the execution,
 *     which happens in the {@link IStateMachine} object.</li>
 *     
 * <li>You can create a state machine from a {@link StateMachineDefinition} invoking
 *     {@link com.sxj.statemachine.StateMachineBuilder#newReentrant(StateMachineDefinition)} or
 *     {@link com.sxj.statemachine.StateMachineBuilder#newNonReentrant(StateMachineDefinition)}.
 *     Check {@link com.sxj.statemachine.interfaces.StateMachineStrategy} to understand the difference
 *     between both.</li>
 * 
 * <p>
 * Invariant.
 * <ul>
 * <li>We only allow one transition at a time within the lock.</li>
 * <li>Transitions from other threads with be blocked by the state machine lock.
 * </li>
 * <li>Transitions from the same thread will throw an exception as we need to
 * avoid recurrent transitions that might end up in an error state unless they come
 * from the {@link OnEnter} phase</li>
 * </ul>
 */
public interface StateMachineDefinition<S extends Enum<?>>
{
    /**
     * Is it an already define state?
     */
    public boolean isState(S state);
    
    /**
     * Is the one and only state?
     */
    public boolean isStartState(S state);
    
    /**
     * Is an Final state?
     */
    public boolean isFinalState(S state);
    
    /**
     * Returns a copy of the list of states
     */
    public List<S> getStates();
    
    /**
     * Returns the list of states that have been marked as Final ones
     */
    public List<S> getFinalStates();
    
    /**
     * Returns the start state of the state machine. There can only be one
     */
    public S getStartState();
    
    /**
     * Is it an already defined event?
     */
    public boolean isEvent(String event);
    
    /**
     * Returns a copy of the list of all the events
     */
    public Set<String> getEvents();
    
    /**
     * Returns a copy of all the events that could be applied to
     * <code>state</code>
     */
    public List<String> getApplicableEvents(String state);
    
    /**
     * Returns the state we reach for the specified source state and event
     */
    public S getTargetState(String source, String event)
            throws StateMachineException;
    
    public void setType(Class<S> type);
}
