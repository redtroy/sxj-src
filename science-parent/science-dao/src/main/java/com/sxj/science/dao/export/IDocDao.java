package com.sxj.science.dao.export;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sxj.mybatis.orm.annotations.BatchDelete;
import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.DocEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IDocDao
{
    @Insert
    public void addDoc(DocEntity doc) throws SQLException;
    
    @BatchInsert
    public void addDocList(List<DocEntity> doc) throws SQLException;
    
    @BatchDelete
    public void deleteDoc(String[] ids) throws SQLException;
    
    @Delete
    public void deleteDocById(String id) throws SQLException;
    
    @Update
    public void updateDoc(DocEntity doc) throws SQLException;
    
    @Get
    public DocEntity getDoc(String id) throws SQLException;
    
    public List<DocEntity> query(QueryCondition<DocEntity> query)
            throws SQLException;
    
    public List<DocEntity> getDocByItemIds(Map<String, Object> paramMap);
}
