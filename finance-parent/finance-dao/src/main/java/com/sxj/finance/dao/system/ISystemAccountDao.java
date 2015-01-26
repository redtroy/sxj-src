package com.sxj.finance.dao.system;

import java.sql.SQLException;
import java.util.List;

import com.sxj.finance.entity.system.SystemAccountEntity;
import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;

public interface ISystemAccountDao
{
    
    @Insert
    public void addSystemAccount(SystemAccountEntity entity)
            throws SQLException;
    
    @Update
    public void updateSystemAccount(SystemAccountEntity entity)
            throws SQLException;
    
    @Get
    public SystemAccountEntity getSystemAccount(String id) throws SQLException;
    
    @Delete
    public void deleteSystemAccount(String id) throws SQLException;
    
    public List<SystemAccountEntity> querySystemAccount(
            QueryCondition<SystemAccountEntity> query) throws SQLException;
    
}
