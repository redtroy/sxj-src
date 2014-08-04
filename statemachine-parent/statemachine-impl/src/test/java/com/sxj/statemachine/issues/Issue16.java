package com.sxj.statemachine.issues;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Test;

import com.sxj.statemachine.fsm.StateMachineBuilderFactory;
import com.sxj.statemachine.fsm.UntypedStateMachine;
import com.sxj.statemachine.fsm.UntypedStateMachineBuilder;
import com.sxj.statemachine.fsm.StateMachine.TransitionDeclinedEvent;
import com.sxj.statemachine.fsm.StateMachine.TransitionDeclinedListener;
import com.sxj.statemachine.fsm.annotation.ContextInsensitive;
import com.sxj.statemachine.fsm.annotation.StateMachineParameters;
import com.sxj.statemachine.fsm.impl.AbstractUntypedStateMachine;

public class Issue16 {
    
    private static enum Issue16State {
        A, B
    }
    
    private static enum Issue16Event {
        A2B, A2A
    }
    
    @ContextInsensitive
    @StateMachineParameters(stateType=Issue16State.class, eventType=Issue16Event.class, contextType=Void.class)
    private static class Issue16StateMachine extends AbstractUntypedStateMachine {
    }
    
    @Test
    public void testIssue16() {
        final AtomicBoolean timedEventTriggered = new AtomicBoolean(false);
        final UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(Issue16StateMachine.class);
        builder.defineTimedState(Issue16State.A, 100, 0, Issue16Event.A2A, null);
        builder.transition().from(Issue16State.A).to(Issue16State.A).on(Issue16Event.A2A);
        builder.transition().from(Issue16State.A).to(Issue16State.B).on(Issue16Event.A2B);
        
        Issue16StateMachine fsm = builder.newUntypedStateMachine(Issue16State.A);
        fsm.addTransitionDeclinedListener(new TransitionDeclinedListener<UntypedStateMachine, Object, Object, Object>() {
            @Override
            public void transitionDeclined(TransitionDeclinedEvent<UntypedStateMachine, Object, Object, Object> event) {
                System.out.println("Transition from "+event.getSourceState()+" on event "+event.getCause()+" is declined.");
                timedEventTriggered.set(true);
            }
        });
        fsm.fire(Issue16Event.A2B);
        System.out.println("Current state is "+fsm.getCurrentState());
        
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
        
        if(timedEventTriggered.get()) {
            Assert.fail();
        }
    }

}
