package com.sxj.statemachine.fsm.builder;

import com.sxj.statemachine.fsm.StateMachine;

/**
 * Local transition builder which is used to build a local transition.
 * 
 * @author Henry.He
 *
 * @param <T> type of State Machine
 * @param <S> type of State
 * @param <E> type of Event
 * @param <C> type of Context
 */
public interface LocalTransitionBuilder<T extends StateMachine<T, S, E, C>, S, E, C> extends ExternalTransitionBuilder<T, S, E, C> {
}
