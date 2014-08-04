package com.sxj.statemachine.fsm.builder;

import com.sxj.statemachine.fsm.StateMachine;

public interface DeferBoundActionFrom<T extends StateMachine<T, S, E, C>, S, E, C> {
    
    /**
     * Build transition target state and return to clause builder
     * @param stateId id of state
     * @return To clause builder
     */
    DeferBoundActionTo<T, S, E, C> to(S stateId);
    
    DeferBoundActionTo<T, S, E, C> toAny();
    
}
