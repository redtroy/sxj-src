package com.sxj.statemachine.fsm.impl;

import com.google.common.collect.Maps;
import com.sxj.statemachine.component.SquirrelProvider;
import com.sxj.statemachine.fsm.Converter;
import com.sxj.statemachine.fsm.ConverterProvider;
import com.sxj.statemachine.fsm.GeneralConverter;

import java.util.Map;

public class ConverterProviderImpl implements ConverterProvider {
    
    private Map<Class<?>, Converter<?>> converterRegistry = Maps.newHashMap();
    
    @Override
    public void register(Class<?> clazz, Class<? extends Converter<?>> converterClass) {
        Converter<?> converter = SquirrelProvider.getInstance().newInstance(converterClass);
        register(clazz, converter);
    }
    
    @Override
    public void register(Class<?> clazz, Converter<?> converter) {
        converterRegistry.put(clazz, converter);
    }
    
    @Override
    public void unregister(Class<?> clazz) {
        converterRegistry.remove(clazz);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T> Converter<T> getConverter(Class<T> clazz) {
        Converter<T> converter = (Converter<T>)converterRegistry.get(clazz);
        if(converter==null) {
            converter = new GeneralConverter(clazz);
        }
        return converter;
    }

    @Override
    public void clearRegistry() {
        converterRegistry.clear();
    }
}
