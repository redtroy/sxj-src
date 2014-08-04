package com.sxj.statemachine.fsm.annotation;

import com.sxj.statemachine.fsm.HistoryType;
import com.sxj.statemachine.fsm.StateCompositeType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
public @interface State {
    String parent() default "";
    String name();
    String alias() default "";
    String entryCallMethod() default "";
    String exitCallMethod() default "";
    boolean initialState() default false;
    boolean isFinal() default false;
    HistoryType historyType() default HistoryType.NONE;
    StateCompositeType compositeType() default StateCompositeType.SEQUENTIAL;
}
