package com.sxj.statemachine.fsm.builder;

import com.sxj.statemachine.fsm.StateMachine;

public interface DeferBoundActionBuilder<T extends StateMachine<T, S, E, C>, S, E, C> {
    
    public DeferBoundActionFrom<T, S, E, C> fromAny();
    
    public DeferBoundActionFrom<T, S, E, C> from(S from);

}
