package com.sxj.supervisor.website.quartz;

import java.io.Serializable;

import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

public class SupervisorMethodInvokingJobDetailFactoryBean extends
        MethodInvokingJobDetailFactoryBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void prepare() throws ClassNotFoundException, NoSuchMethodException
    {
        super.prepare();
    }
    
}
