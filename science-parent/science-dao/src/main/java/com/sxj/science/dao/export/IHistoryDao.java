package com.sxj.science.dao.export;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.HistoryEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IHistoryDao
{
    @Insert
    public void addHistory(HistoryEntity historyEntity) throws SQLException;
    
    @Update
    public void updateHistory(HistoryEntity historyEntity) throws SQLException;
    
    @Delete
    public void deleteHistory(String id) throws SQLException;
    
    @Get
    public ProjectEntity getHistory(String id) throws SQLException;

    public List<HistoryEntity> query(QueryCondition<HistoryEntity> condition);
}
