package com.sxj.supervisor.service.tasks;

import java.io.Serializable;

import com.sxj.supervisor.entity.gather.WindDoorEntity;

public interface IWindDoorService extends Serializable
{
    
    /**
     * 门窗采集
     */
    public void WindDoorGather();
    
    /**
     * 获取信息
     */
    public WindDoorEntity getInfoById(String id);
}
