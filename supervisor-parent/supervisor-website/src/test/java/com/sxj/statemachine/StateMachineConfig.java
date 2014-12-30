package com.sxj.statemachine;

import com.sxj.statemachine.annotations.OnEnter;
import com.sxj.statemachine.annotations.OnExit;
import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;

@StateMachine(stateType = DemoStates.class, startState = "A", finalStates = {
        "C", "D" })
@Transitions({ @Transition(source = "A", event = "AtoB", target = "B") })
public class StateMachineConfig
{
    
    @Transitions({ @Transition(source = "B", event = "BtoC", target = "C") })
    public void noop(TransitionInfo event)
    {
        System.out.println("tx@:" + event.getEvent());
    }
    
    @OnEnter(value = "B")
    public EventInfo enterB(TransitionInfo event)
    {
        System.out.println("Entered B state");
        return null;
    }
    
    @OnExit("A")
    public Boolean exitA(TransitionInfo event)
    {
        System.out.println("Exited A state");
        return true;
    }
}
