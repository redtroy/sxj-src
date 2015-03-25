package com.sxj.supervisor.service.tasks;

import java.util.List;

import com.sxj.supervisor.entity.gather.AlEntity;

public interface IAlGather
{
    
    public void gather();
    
    public List<AlEntity> query();
}
