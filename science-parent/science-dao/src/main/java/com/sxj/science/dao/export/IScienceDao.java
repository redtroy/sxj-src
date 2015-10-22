package com.sxj.science.dao.export;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IScienceDao
{
    @Insert
    public void addScience(ScienceEntity doc);
    
    @BatchInsert
    public void addScienceList(List<ScienceEntity> doc);
    
    public void deleteScience(String[] docIds);
    
    @Update
    public void updateScience(ScienceEntity doc);
    
    @Get
    public ScienceEntity getScience(String id);
    
    public List<ScienceEntity> query(QueryCondition<ScienceEntity> query)
            throws SQLException;
}
