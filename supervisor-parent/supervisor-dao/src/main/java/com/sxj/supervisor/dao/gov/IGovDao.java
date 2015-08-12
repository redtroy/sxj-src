package com.sxj.supervisor.dao.gov;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.gov.GovEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 政务信息DAO 
 * @author nishaotang
 *
 */
public interface IGovDao
{
    /**
     * 添加政务信息
     * @param Gov
     */
    @Insert
    public void addGov(GovEntity gov);
    
    /**
     * 更新政务信息
     * @param Gov
     */
    @Update
    public void updateGov(GovEntity gov);
    
    /**
     * 删除政务信息
     * @param id
     */
    @Delete
    public void delGov(String id);
    
    /**
     * 查询政务信息
     * 
     * @param id
     * @return
     */
    @Get
    public GovEntity getGov(String id);
    
    /**
     * 政务信息高级查询
     * 
     * @param query
     * @return
     */
    public List<GovEntity> query(QueryCondition<GovEntity> query);
    
}
