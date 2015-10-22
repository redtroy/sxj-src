package com.sxj.science.dao.export;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.GlassEntity;

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
}
