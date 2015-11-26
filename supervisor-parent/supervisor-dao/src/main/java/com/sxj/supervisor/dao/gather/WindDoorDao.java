package com.sxj.supervisor.dao.gather;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.util.persistent.QueryCondition;

public interface WindDoorDao
{
    
    /**
     * 新增门窗采集信息
     */
    @BatchInsert
    public void addWindDoor(List<WindDoorEntity> list);
    
    @Insert
    public void save(WindDoorEntity entity);
    
    /**
     * 最新数据的排序查询
     */
    public List<WindDoorEntity> getMaxWindDoor();
    
    /**
     * 根据ID查询详情
     */
    @Get
    public WindDoorEntity getInfoById(String id);
    
    /**'
     * 查询列表
     * @param query
     * @return
     */
    public List<WindDoorEntity> queryList(QueryCondition<WindDoorEntity> query);
    
    /**
     * 更新状态
     * @param wd
     */
    @Update
    public void updateWind(WindDoorEntity wd);
    
    public WindDoorEntity getByOid(String oid);
}
