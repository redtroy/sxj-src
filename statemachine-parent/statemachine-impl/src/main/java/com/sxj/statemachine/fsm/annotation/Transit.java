package com.sxj.statemachine.fsm.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.sxj.statemachine.fsm.Condition;
import com.sxj.statemachine.fsm.Conditions;
import com.sxj.statemachine.fsm.TransitionPriority;
import com.sxj.statemachine.fsm.TransitionType;

@Target({ TYPE })
@Retention(RUNTIME)
public @interface Transit {
    String from();

    String to();
    
    String on();
    
    boolean isTargetFinal() default false;

    @SuppressWarnings("rawtypes")
    Class<? extends Condition> when() default Conditions.Always.class;
    
    String whenMvel() default "";

    TransitionType type() default TransitionType.EXTERNAL;

    String callMethod() default "";
    
    int priority() default TransitionPriority.NORMAL;
}
