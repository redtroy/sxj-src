package com.sxj.supervisor.dao.gather;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.gather.AlEntity;

public interface AlDao
{
    
    /**
     * 插入新数据
     */
    @Insert
    public void addAl(AlEntity al);
    
    /**
     * 获取最新30条数据
     */
    public List<AlEntity> getAl();
    
    public int deleteByDates(List<AlEntity> entities);
    
    @BatchInsert
    public int addAlEntities(List<AlEntity> entities);
}
