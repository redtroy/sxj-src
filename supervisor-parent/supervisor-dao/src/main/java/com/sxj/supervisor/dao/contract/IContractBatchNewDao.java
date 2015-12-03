package com.sxj.supervisor.dao.contract;

import java.util.List;
import java.util.Map;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractBatchNewEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 批次DAO
 * 
 * @author Ann
 *
 */
public interface IContractBatchNewDao
{
    /**
     * 添加批次信息
     *
     * @param batchs
     **/
    @BatchInsert
    public void addBatchs(List<ContractBatchNewEntity> batchs);
    
    /**
     * 获取批次信息
     *
     * @param id
     **/
    @Get
    public ContractBatchNewEntity getBatch(String id);
    
    /**
     * 查询批次列表
     *
     * @param contractId
     **/
    public List<ContractBatchNewEntity> queryBacths(String contractNo,String recordNo,Integer state);
    
    /**
     * 删除批次
     * 
     * @param id
     */
    @Delete
    public void deleteBatchs(String id);
    
    /**
     * 批量修改批次信息
     *
     * @param batchs
     **/
    @BatchUpdate
    public void updateBatchs(List<ContractBatchNewEntity> batchs);
    
    /**
     * 修改批次信息
     *
     * @param batchs
     **/
    @Update
    public void updateBatch(ContractBatchNewEntity batch);
    
}
