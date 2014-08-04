package com.sxj.component.impl;

import com.google.common.base.Predicate;
import com.sxj.component.Observable;
import com.sxj.component.SquirrelProvider;
import com.sxj.event.ListenerMethod;
import com.sxj.event.PolymEventDispatcher;
import com.sxj.event.SquirrelEvent;
import com.sxj.util.ReflectUtils;

import java.lang.reflect.Method;

public abstract class AbstractSubject implements Observable {

    private boolean notifiable = true;

    private PolymEventDispatcher eventDispatcher;

    @Override
    public boolean isNotifiable() {
        return notifiable;
    }

    @Override
    public void setNotifiable(boolean notifiable) {
        this.notifiable = notifiable;
    }

    @Override
    public void addListener(Class<?> eventType, Object listener, Method method) {
        if (eventDispatcher == null) {
            eventDispatcher = SquirrelProvider.getInstance().newInstance(PolymEventDispatcher.class);
        }
        eventDispatcher.register(eventType, listener, method);
    }

    @Override
    public void addListener(Class<?> eventType, Object listener, String methodName) {
        Method method = ReflectUtils.getFirstMethodOfName(listener.getClass(), methodName);
        addListener(eventType, listener, method);
    }

    public void removeListener(Predicate<ListenerMethod> predicate) {
        if (eventDispatcher != null) {
            eventDispatcher.unregister(predicate);
        }
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener, Method method) {
        if (eventDispatcher != null) {
            eventDispatcher.unregister(eventType, listener, method);
        }
    }

    @Override
    public int getListenerSize() {
        return eventDispatcher!=null ? eventDispatcher.getListenerSize() : 0;
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener, String methodName) {
        Method method = ReflectUtils.getFirstMethodOfName(listener.getClass(), methodName);
        removeListener(eventType, listener, method);
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener) {
        if (eventDispatcher != null) {
            eventDispatcher.unregister(eventType, listener);
        }
    }

    @Override
    public void removeAllListeners() {
        if (eventDispatcher != null)
            eventDispatcher.unregisterAll();
    }

    @Override
    public void fireEvent(SquirrelEvent event) {
        if (eventDispatcher != null && isNotifiable()) {
            eventDispatcher.fireEvent(event);
        }
    }
}
