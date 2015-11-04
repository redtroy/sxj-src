package com.sxj.science.dao.system;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.science.entity.system.AreaEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IAreaDao
{
    
    @BatchInsert
    public void addAreas(List<AreaEntity> areas) throws SQLException;
    
    @Delete
    public void deleteArea(String id) throws SQLException;
    
    @Get
    public AreaEntity getArea(String id) throws SQLException;
    
    public void deleteChildrenArea(String parentId) throws SQLException;
    
    public List<AreaEntity> getAreas(QueryCondition<AreaEntity> query)
            throws SQLException;

    public List<AreaEntity> getAreaById(QueryCondition<AreaEntity> query);
    
}
