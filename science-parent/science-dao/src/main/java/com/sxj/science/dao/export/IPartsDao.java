package com.sxj.science.dao.export;

import java.util.List;
import java.util.Map;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.PartsEntity;

public interface IPartsDao
{
    @Insert
    public void addParts(PartsEntity doc);
    
    @BatchInsert
    public void addPartsList(List<PartsEntity> doc);
    
    public void deleteParts(String[] docIds);
    
    @Update
    public void updateParts(PartsEntity doc);
    
    @Get
    public PartsEntity getParts(String id);

    public List<PartsEntity> getPartsByDocIds(Map<String, Object> paramMap);
}
