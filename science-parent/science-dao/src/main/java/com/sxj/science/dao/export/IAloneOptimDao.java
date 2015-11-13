package com.sxj.science.dao.export;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.AloneOptimEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IAloneOptimDao
{
    
    @Insert
    public void addAloneOptim(AloneOptimEntity optiom) throws SQLException;
    
    public List<AloneOptimEntity> query(QueryCondition<AloneOptimEntity> query)
            throws SQLException;
    
    @Get
    public AloneOptimEntity getAloneOptim(String id) throws SQLException;
    
    @Delete
    public void deleteAloneOptim(String id) throws SQLException;
    
    @Update
    public void updateAloneOptim(AloneOptimEntity query);
    
    public void deleteAloneOptimByProject(String projectId) throws SQLException;
    
}
