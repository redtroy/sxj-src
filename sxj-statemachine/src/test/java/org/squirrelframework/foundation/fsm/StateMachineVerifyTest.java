package org.squirrelframework.foundation.fsm;

import org.junit.Assert;
import org.junit.Test;

import com.sxj.fsm.StateMachineBuilder;
import com.sxj.fsm.StateMachineBuilderFactory;
import com.sxj.fsm.TransitionType;
import com.sxj.fsm.annotation.State;
import com.sxj.fsm.annotation.States;
import com.sxj.fsm.annotation.Transit;
import com.sxj.fsm.annotation.Transitions;
import com.sxj.fsm.impl.AbstractStateMachine;

public class StateMachineVerifyTest extends AbstractStateMachineTest {
    
    @Transitions({
        @Transit(from="A", to="B", on="ToB", callMethod="test", type=TransitionType.INTERNAL)
    })
    private static class InvalidInternalTransitionStateMachine extends AbstractStateMachine<InvalidInternalTransitionStateMachine, TestState, TestEvent, Integer> {
        @SuppressWarnings("unused")
        public void test(TestState from, TestState to, TestEvent event, Integer context) {
        }
    }
    
    @Transitions({
        @Transit(from="A", to="B", on="ToB", callMethod="test"),
        @Transit(from="A", to="B", on="ToB", callMethod="test", type=TransitionType.LOCAL),
    })
    private static class ConflictTransitionStateMachine extends AbstractStateMachine<ConflictTransitionStateMachine, TestState, TestEvent, Integer> {
        @SuppressWarnings("unused")
        public void test(TestState from, TestState to, TestEvent event, Integer context) {
        }
    }
    
    @States({@State(name="A", isFinal=true),
            @State(name="B", parent="A")
    })
    @Transitions({
        @Transit(from="A", to="B", on="ToB", callMethod="test")
    })
    private static class InvalidFinalStateMachine extends AbstractStateMachine<InvalidFinalStateMachine, TestState, TestEvent, Integer> {
        @SuppressWarnings("unused")
        public void test(TestState from, TestState to, TestEvent event, Integer context) {
        }
    }
    
    @Test
    public void testInvalidFinalState() {
        StateMachineBuilder<InvalidFinalStateMachine, TestState, TestEvent, Integer> builder = 
                StateMachineBuilderFactory.create(InvalidFinalStateMachine.class, TestState.class, 
                TestEvent.class, Integer.class);
        try {
            builder.newStateMachine(TestState.A);
        } catch(RuntimeException e) {
            System.out.println("Expected error: "+e.getMessage());
            return;
        }
        Assert.fail();
    }
    
    @Test
    public void testInvalidInternalTransition() {
        StateMachineBuilder<InvalidInternalTransitionStateMachine, TestState, TestEvent, Integer> builder = 
                StateMachineBuilderFactory.create(InvalidInternalTransitionStateMachine.class, TestState.class, 
                TestEvent.class, Integer.class);
        try {
            builder.newStateMachine(TestState.A);
        } catch(RuntimeException e) {
            System.out.println("Expected error: "+e.getMessage());
            return;
        }
        Assert.fail();
    }
    
    @Test
    public void testVerifyConflictTransition() {
        StateMachineBuilder<ConflictTransitionStateMachine, TestState, TestEvent, Integer> builder = 
                StateMachineBuilderFactory.create(ConflictTransitionStateMachine.class, TestState.class, 
                TestEvent.class, Integer.class);
        try {
            builder.newStateMachine(TestState.A);
        } catch(RuntimeException e) {
            System.out.println("Expected error: "+e.getMessage());
            return;
        }
        Assert.fail();
    }
}
