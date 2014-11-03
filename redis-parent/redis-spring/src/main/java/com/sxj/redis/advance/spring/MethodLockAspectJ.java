package com.sxj.redis.advance.spring;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sxj.redis.advance.RedisConcurrent;
import com.sxj.redis.advance.core.RLock;
import com.sxj.redis.advance.spring.annotation.Lock;

@Aspect
@Component
public class MethodLockAspectJ
{
    @Autowired
    private RedisConcurrent redisConcurrent;
    
    @Pointcut("@annotation(com.sxj.redis.advance.spring.annotation.Lock)")
    public void methodLockPointCut()
    {
        
    }
    
    @Around("methodLockPointCut()")
    public Object methodLockHold(ProceedingJoinPoint joinPoint)
            throws Throwable
    {
        RLock lock = null;
        Object result = null;
        try
        {
            //            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Method method = findLockMethod(joinPoint.getTarget().getClass(),
                    methodName,
                    arguments);
            
            Lock annotation = method.getAnnotation(Lock.class);
            String name = annotation.name();
            int timeOut = annotation.timeOut();
            //            String lockName = getLockName(targetName,
            //                    methodName,
            //                    arguments,
            //                    name);
            //            System.out.println("key--" + lockName);
            lock = redisConcurrent.getLock(name);
            lock.lock(timeOut, TimeUnit.MILLISECONDS);
            result = joinPoint.proceed();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            if (lock != null)
                lock.unlock();
        }
        return result;
    }
    
    private Method findLockMethod(Class<?> clazz, String methodName,
            Object[] arguments) throws NoSuchMethodException, SecurityException
    {
        Class<?>[] parameterTypes = new Class<?>[arguments.length];
        for (int i = 0; i < arguments.length; i++)
        {
            parameterTypes[i] = arguments[i].getClass();
        }
        Method method = clazz.getMethod(methodName, parameterTypes);
        if (method.isAnnotationPresent(Lock.class))
            return method;
        return null;
    }
    
    private String getLockName(String targetName, String methodName,
            Object[] arguments, String name)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(name)
                .append("_")
                .append(targetName)
                .append(".")
                .append(methodName);
        if ((arguments != null) && (arguments.length != 0))
        {
            for (int i = 0; i < arguments.length; i++)
            {
                if (arguments[i] instanceof Date)
                {
                    sb.append(".").append(((Date) arguments[i]).getTime());
                }
                else
                {
                    sb.append(".").append(arguments[i]);
                }
            }
        }
        return sb.toString();
    }
    
    public void setRedisConcurrent(RedisConcurrent redisConcurrent)
    {
        System.out.println("ddddddddddddddddddd");
        this.redisConcurrent = redisConcurrent;
    }
    
}
