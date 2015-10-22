package com.sxj.science.dao.export;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchDelete;
import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.ItemEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IItemDao
{
    @Insert
    public void addItem(ItemEntity item) throws SQLException;
    
    @Update
    public void updateItem(ItemEntity item) throws SQLException;
    
    public List<ItemEntity> query(QueryCondition<ItemEntity> query)
            throws SQLException;
    
    @BatchDelete
    public void deleteItems(String[] itemId) throws SQLException;
    
    @Delete
    public void deleteItem(String itemId) throws SQLException;
    
    @Get
    public ItemEntity getItem(String id);
}
