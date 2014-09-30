package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 批次DAO
 * @author Ann
 *
 */
public interface IContractBatchDao
{
    /**
     * 添加批次信息
     *
     * @param    batchs
    **/
    @BatchInsert
    public void addBatchs(List<ContractBatchEntity> batchs);
    
    /**
     * 获取批次信息
     *
     * @param    id
    **/
    @Get
    public ContractBatchEntity getBatch(String id);
    
    /**
     * 查询批次列表
     *
     * @param    contractId
    **/
    public List<ContractBatchEntity> queryBacths(QueryCondition<ContractBatchEntity> query);
    
    @Delete
    public void deleteBatchs(String contractId);
    
    /**
     * 批量修改批次信息
     *
     * @param    batchs
    **/
    @BatchUpdate
    public void updateBatchs(List<ContractBatchEntity> batchs);
    
    /**
     * 修改批次信息
     *
     * @param    batchs
    **/
    @Update
    public void updateBatch(ContractBatchEntity batch);
}
