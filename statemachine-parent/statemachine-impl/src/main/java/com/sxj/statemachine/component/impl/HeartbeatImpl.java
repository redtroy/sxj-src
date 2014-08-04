package com.sxj.statemachine.component.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.common.base.Preconditions;
import com.sxj.statemachine.component.Heartbeat;
import com.sxj.statemachine.component.SquirrelComponent;

public class HeartbeatImpl implements Heartbeat, SquirrelComponent {
    
    private final Stack<List<Runnable>> stack = new Stack<List<Runnable>>();
    
    @Override
    public void begin() {
        List<Runnable> beat = new ArrayList<Runnable>();
        stack.push(beat);
    }

    @Override
    public void execute() {
        List<Runnable> beat = stack.pop();
        for (Runnable r : beat) {
            r.run();
        }
    }

    @Override
    public void defer(Runnable command) {
        Preconditions.checkNotNull(command);
        stack.peek().add(command);
    }
}
