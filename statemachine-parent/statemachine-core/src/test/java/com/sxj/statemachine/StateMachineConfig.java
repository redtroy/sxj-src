package com.sxj.statemachine;

import com.sxj.statemachine.annotations.StateMachine;
import com.sxj.statemachine.annotations.Transition;
import com.sxj.statemachine.annotations.Transitions;

@StateMachine
public class StateMachineConfig
{
    @Transitions({ @Transition(event = "A-B", source = "A", target = "B") })
    public void noop(TransitionInfo info)
    {
        System.out.println("#tx: " + info);
    }
}
