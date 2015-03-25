package com.sxj.supervisor.service.tasks;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.gather.AlEntity;

public interface IAlGather extends Serializable
{
    
    public void gather();
    
    public List<AlEntity> query();
}
