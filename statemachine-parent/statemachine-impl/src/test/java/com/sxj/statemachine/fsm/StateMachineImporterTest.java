package com.sxj.statemachine.fsm;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sxj.statemachine.exception.SquirrelRuntimeException;
import com.sxj.statemachine.fsm.StateMachineBuilderFactory;
import com.sxj.statemachine.fsm.UntypedAnonymousAction;
import com.sxj.statemachine.fsm.UntypedStateMachine;
import com.sxj.statemachine.fsm.UntypedStateMachineBuilder;
import com.sxj.statemachine.fsm.UntypedStateMachineImporter;
import com.sxj.statemachine.fsm.annotation.StateMachineParameters;
import com.sxj.statemachine.fsm.annotation.Transit;
import com.sxj.statemachine.fsm.annotation.Transitions;
import com.sxj.statemachine.fsm.impl.AbstractUntypedStateMachine;

public class StateMachineImporterTest {
    
    enum TestEvent {
        toA, toB, toC, toD
    }
    
    @Transitions({
        @Transit(from="a", to="b", on="toB")
    })
    @StateMachineParameters(stateType=String.class, eventType=TestEvent.class, contextType=Integer.class)
    class ImportParameterizedActionCase extends AbstractUntypedStateMachine {
    }
    
    @Test(expected=SquirrelRuntimeException.class)
    @SuppressWarnings("unused")
    public void testImportParameterizedActionFail() {
        final Integer param1 = 10;
        final String param2 = "Hello World!";
        UntypedStateMachineBuilder builder = 
                StateMachineBuilderFactory.create(ImportParameterizedActionCase.class);
        builder.externalTransition().from("a").to("b").on(TestEvent.toB).perform(new UntypedAnonymousAction() {
            @Override
            public void execute(Object from, Object to, Object event, Object context, 
                    UntypedStateMachine stateMachine) {
            }
        });
        ImportParameterizedActionCase sample = builder.newUntypedStateMachine("a");
        
        UntypedStateMachineBuilder importedBuilder = 
                new UntypedStateMachineImporter().importDefinition(sample.exportXMLDefinition(false));
    }
    
    @Test(expected=SquirrelRuntimeException.class)
    @SuppressWarnings("unused")
    public void testImportParameterizedActionSuccess() {
        final Integer param1 = 10;
        final String param2 = "Hello World!";
        UntypedStateMachineBuilder builder = 
                StateMachineBuilderFactory.create(ImportParameterizedActionCase.class);
        builder.externalTransition().from("a").to("b").on(TestEvent.toB).perform(new UntypedAnonymousAction() {
            {
                // user need to make sure there always no-args constructor exits
            }
            @Override
            public void execute(Object from, Object to, Object event, Object context, 
                    UntypedStateMachine stateMachine) {
            }
        });
        ImportParameterizedActionCase sample = builder.newUntypedStateMachine("a");
        
        UntypedStateMachineBuilder importedBuilder = 
                new UntypedStateMachineImporter().importDefinition(sample.exportXMLDefinition(false));
        ImportParameterizedActionCase importedSample = importedBuilder.newAnyStateMachine("a");
        importedSample.fire(TestEvent.toB);
        assertTrue(importedSample.getCurrentState().equals("a"));
    }

}
