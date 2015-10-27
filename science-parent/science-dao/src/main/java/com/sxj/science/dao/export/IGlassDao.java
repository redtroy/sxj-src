package com.sxj.science.dao.export;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.GlassEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IGlassDao
{
    @Insert
    public void addGlass(GlassEntity doc);
    
    @BatchInsert
    public void addGlassList(List<GlassEntity> glassList);
    
    @Update
    public void updateGlass(GlassEntity doc);
    
    @Get
    public GlassEntity getGlass(String id);
    
    public void deleteGlass(String[] docIds);
    
    public void deleteGlassByDocId(String docId);
    
    public List<GlassEntity> query(QueryCondition<GlassEntity> query)
            throws SQLException;
}
