package com.sxj.finance.dao.finance;

import java.sql.SQLException;
import java.util.List;

import com.sxj.finance.entity.FinanceEntity;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;

public interface FinanceDao
{
    
    /**
     * 根据条件查询 前台
     * 
     * @return
     */
    public List<FinanceEntity> queryWebSite(QueryCondition<FinanceEntity> query)
            throws SQLException;
    
    /**
     * 根据条件查询 后台
     * 
     * @return
     */
    public List<FinanceEntity> queryManage(QueryCondition<FinanceEntity> query)
            throws SQLException;
    
    /**
     * 根据ID更新
     */
    @Update
    public void update(FinanceEntity fe) throws SQLException;
    
    /**
     * 新增
     */
    @Insert
    public void add(FinanceEntity fe) throws SQLException;
    
    /**
     * 根据ID查询
     */
    @Get
    public FinanceEntity getFinanceEntityById(String id) throws SQLException;
}
