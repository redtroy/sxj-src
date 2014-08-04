package com.sxj.statemachine.component.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.sxj.statemachine.component.CompositePostProcessor;
import com.sxj.statemachine.component.SquirrelPostProcessor;

public class CompositePostProcessorImpl<T> implements CompositePostProcessor<T> {
    
    List<SquirrelPostProcessor<? super T>> processors;
    
    public CompositePostProcessorImpl(SquirrelPostProcessor<? super T>...processors) {
        this.processors = Lists.newArrayList(processors);
    }
    
    public CompositePostProcessorImpl() {
        this.processors = Lists.newArrayList();
    }
    
    @Override
    public void compose(SquirrelPostProcessor<? super T> processor) {
        if(!processors.contains(processor)) {
            processors.add(processor);
        }
    }
    
    @Override
    public void decompose(SquirrelPostProcessor<? super T> processor) {
        processors.remove(processor);
    }
    
    @Override
    public void clear() {
        processors.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void postProcess(T component) {
        Object[] processArray = processors.toArray();
        for(Object processor : processArray) {
            ((SquirrelPostProcessor<? super T>)processor).postProcess(component);
        }
    }
}
