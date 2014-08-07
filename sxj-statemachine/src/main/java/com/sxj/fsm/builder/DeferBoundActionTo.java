package com.sxj.fsm.builder;

import com.sxj.fsm.StateMachine;

public interface DeferBoundActionTo<T extends StateMachine<T, S, E, C>, S, E, C> {

    /**
     * Build transition event
     * @param event transition event
     * @return On clause builder
     */
    On<T, S, E, C> on(E event);
    
    On<T, S, E, C> onAny();
    
}
