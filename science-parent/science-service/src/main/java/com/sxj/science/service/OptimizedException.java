package com.sxj.science.service;

public class OptimizedException extends RuntimeException
{
    
    public OptimizedException()
    {
        super();
    }
    
    public OptimizedException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public OptimizedException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public OptimizedException(String message)
    {
        super(message);
    }
    
    public OptimizedException(Throwable cause)
    {
        super(cause);
    }
    
}
