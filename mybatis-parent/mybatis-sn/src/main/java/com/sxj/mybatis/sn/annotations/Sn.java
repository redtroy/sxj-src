package com.sxj.mybatis.sn.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sxj.mybatis.dialect.Dialect;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sn
{
    Dialect.Type dialect() default Dialect.Type.MYSQL;
    
    int step() default 1;
    
    String table() default "T_SN";
    
    String stub() default "";
    
    String stubValue() default "DEFAULT";
    
    String sn() default "ID";
    
    String pattern() default "0000";
}