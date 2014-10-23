package com.sxj.supervisor.website.comet;

import java.util.concurrent.atomic.AtomicInteger;

import org.comet4j.core.CometEngine;

public abstract class MessageThread extends Thread
{
    
    private CometEngine engine;
    
    private boolean flat = true;
    
    private String param;
    
    private AtomicInteger counter = new AtomicInteger(0);
    
    public MessageThread(CometEngine engine)
    {
        super();
        this.engine = engine;
    }
    
    protected CometEngine getEngine()
    {
        return engine;
    }
    
    public boolean isFlat()
    {
        return flat;
    }
    
    public void setFlat(boolean flat)
    {
        this.flat = flat;
    }
    
    public String getParam()
    {
        return param;
    }
    
    public void setParam(String param)
    {
        this.param = param;
    }
    
    protected void descrCounter()
    {
        if (counter.get() > 0)
            counter.decrementAndGet();
    }
    
    protected void incrCounter()
    {
        counter.incrementAndGet();
    }
    
    protected AtomicInteger getCounter()
    {
        return counter;
    }
    
}
