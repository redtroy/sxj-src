package com.sxj.statemachine;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import com.sxj.statemachine.impl.StringStateMachine;
import com.sxj.statemachine.interfaces.StateMachine;
import com.sxj.statemachine.interfaces.TransitionHandler;

/**
 * @author Ben Fagin
 * @version 2013-07-08
 */
public class String_T
{
    
    @Test
    public void testStringState()
    {
        final AtomicReference<String> cur = new AtomicReference<String>(null);
        
        StateMachine<String> sm = new StringStateMachine();
        
        sm.addTransition(null, "hello");
        sm.addTransition("hello", "world", new TransitionHandler<String>()
        {
            public void onTransition(String from, String to)
            {
                cur.set(to);
            }
        });
        sm.addTransition("world", "goodbye ");
        sm.addTransition("GOODBYE", null);
        
        sm.transition("hello");
        assertEquals(null, cur.get());
        
        sm.transition("world");
        assertEquals("world", cur.get());
        
        sm.transition("goodbye");
    }
}
