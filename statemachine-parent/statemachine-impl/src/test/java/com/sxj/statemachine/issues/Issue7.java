package com.sxj.statemachine.issues;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sxj.statemachine.fsm.AnonymousAction;
import com.sxj.statemachine.fsm.StateMachineBuilder;
import com.sxj.statemachine.fsm.StateMachineBuilderFactory;
import com.sxj.statemachine.fsm.annotation.ContextInsensitive;
import com.sxj.statemachine.fsm.impl.AbstractStateMachine;

public class Issue7 {
    @Test 
    public void performsTransactionOnEventFromAction() {
        FSM fsm = createFSM();
        fsm.fire(Event.GoWild);
        assertEquals(State.GoneWild, fsm.getCurrentState());
        fsm.fire(Event.CalmDown);
        assertEquals(State.Calm, fsm.getCurrentState());
    }

    private static FSM createFSM() {
        StateMachineBuilder<FSM, State, Event, Void> builder =
                StateMachineBuilderFactory.create(FSM.class, State.class, Event.class, Void.class);

        builder.externalTransition().from(State.Calm).to(State.GoingWild).on(Event.GoWild);
        builder.externalTransition().from(State.GoingWild).to(State.GoneWild).on(Event.GoneWild);
        builder.externalTransition().from(State.GoneWild).to(State.Calm).on(Event.CalmDown);

        builder.onEntry(State.GoingWild).perform(new AnonymousAction<FSM, State, Event, Void>() {
            @Override
            public void execute(State from, State to, Event event, Void context, FSM stateMachine) {
                // let's assume we are ready to go wild already and just fire event
                stateMachine.fire(Event.GoneWild);
            }
        });
        builder.onEntry(State.GoneWild).perform(new AnonymousAction<FSM, State, Event, Void>() {
            @Override
            public void execute(State from, State to, Event event, Void context, FSM stateMachine) {
                // throw new RuntimeException("Oops!");
            }
        });
        return builder.newStateMachine(State.Calm);
    }

    @ContextInsensitive
    private static class FSM extends AbstractStateMachine<FSM, State, Event, Void> {
    }

    private static enum State {
        GoneWild,
        GoingWild,
        Calm
    }

    private static enum Event {
        GoneWild,
        GoWild,
        CalmDown
    }
}
