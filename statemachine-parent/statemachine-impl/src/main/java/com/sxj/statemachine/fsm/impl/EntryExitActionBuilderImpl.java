package com.sxj.statemachine.fsm.impl;

import com.sxj.statemachine.component.SquirrelComponent;
import com.sxj.statemachine.fsm.Action;
import com.sxj.statemachine.fsm.MutableState;
import com.sxj.statemachine.fsm.StateMachine;
import com.sxj.statemachine.fsm.builder.EntryExitActionBuilder;

import java.util.List;

class EntryExitActionBuilderImpl<T extends StateMachine<T, S, E, C>, S, E, C> implements 
    EntryExitActionBuilder<T, S, E, C>, SquirrelComponent {
    
    private final boolean isEntryAction;
    
    private final MutableState<T, S, E, C> state;
    
    private final ExecutionContext executionContext;
    
    EntryExitActionBuilderImpl(MutableState<T, S, E, C> state, 
            boolean isEntryAction, ExecutionContext executionContext) {
        this.state = state;
        this.isEntryAction = isEntryAction;
        this.executionContext = executionContext;
    }

    @Override
    public void perform(Action<T, S, E, C> action) {
        if(isEntryAction) {
            state.addEntryAction(action);
        } else {
            state.addExitAction(action);
        }
    }

    @Override
    public void perform(List<? extends Action<T, S, E, C>> actions) {
        if(isEntryAction) {
            state.addEntryActions(actions);
        } else {
            state.addExitActions(actions);
        }
    }

    @Override
    public void evalMvel(String expression) {
        Action<T, S, E, C> action = FSM.newMvelAction(expression, executionContext);
        perform(action);
    }

    @Override
    public void callMethod(String methodName) {
        Action<T, S, E, C> action = FSM.newMethodCallActionProxy(methodName, executionContext);
        perform(action);
    }
}
