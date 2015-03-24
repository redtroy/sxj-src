package com.sxj.supervisor.website.quartz;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.supervisor.service.tasks.IAlGather;

public class AlGatherQuartz implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8418105261854739481L;
    
    @Autowired
    private IAlGather ag;
    
    public void gather()
    {
        try
        {
            ag.gather();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
