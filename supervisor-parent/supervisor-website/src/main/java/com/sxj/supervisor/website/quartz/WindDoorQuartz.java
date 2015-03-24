package com.sxj.supervisor.website.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sxj.supervisor.service.tasks.IWindDoorService;

public class WindDoorQuartz extends QuartzJobBean
{
    
    @Autowired
    private IWindDoorService wds;
    
    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException
    {
        try
        {
            wds.WindDoorGather();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
