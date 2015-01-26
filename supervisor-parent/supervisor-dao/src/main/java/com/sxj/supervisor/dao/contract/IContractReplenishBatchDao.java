package com.sxj.supervisor.dao.contract;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 补损合同批次Dao
 * 
 * @author Ann
 *
 */
public interface IContractReplenishBatchDao
{
    
    /**
     * 获取补损
     * 
     * @param replenishBatchEntity
     */
    @Get
    public ReplenishBatchEntity getBatch(String id) throws SQLException;;
    
    /**
     * 更改补损
     * 
     * @param replenishBatchEntity
     */
    @Update
    public void updateBatch(ReplenishBatchEntity replenishBatchEntity)
            throws SQLException;;
    
    /**
     * 新增补损
     * 
     * @param replenishBatchEntity
     */
    @BatchInsert
    public void addReplenishBatch(
            List<ReplenishBatchEntity> replenishBatchEntity);
    
    /**
     * 更改补损
     * 
     * @param replenishBatchEntity
     */
    @BatchUpdate
    public void updateReplenishBatch(
            List<ReplenishBatchEntity> replenishBatchEntity);
    
    /**
     * 查询补损合同
     * 
     * @param replenishId
     * @return
     */
    public List<ReplenishBatchEntity> queryReplenishBatch(
            QueryCondition<ReplenishBatchEntity> query);
    
    public List<ReplenishBatchEntity> getReplenishBatch(String contractNo);
}
