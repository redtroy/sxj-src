package com.sxj.supervisor.website.quartz;

import java.io.Serializable;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sxj.supervisor.service.tasks.IAlGather;

public class AlGatherQuartz extends QuartzJobBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8418105261854739481L;
    
    @Autowired
    private IAlGather ag;
    
    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException
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
