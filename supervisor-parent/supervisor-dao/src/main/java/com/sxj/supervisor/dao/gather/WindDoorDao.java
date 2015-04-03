package com.sxj.supervisor.dao.gather;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.supervisor.entity.gather.WindDoorEntity;

public interface WindDoorDao
{
    
    /**
     * 新增门窗采集信息
     */
    @BatchInsert
    public void addWindDoor(List<WindDoorEntity> list);
    
    /**
     * 最新数据的排序查询
     */
    public List<WindDoorEntity> getMaxWindDoor();
    
    /**
     * 根据ID查询详情
     */
    @Get
    public WindDoorEntity getInfoById(String id);
}
