package com.sxj.supervisor.dao.contract;

import java.util.List;
import java.util.Map;

import com.sxj.mybatis.orm.annotations.BatchDelete;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.supervisor.entity.contract.ContractItemEntity;

/**
 *  合同明细Dao
 * @author Administrator
 *
 */
public interface IContractItemDao
{
    /**
     * 新增合同条目
     *
     * @param    items
    **/
    public void addItem(Map<String, Object> map);
    
    /**
     *  通过合同ID查询条目列表
     *
     * @param    contractId
    **/
    public List<ContractItemEntity> queryItems(String contractId);
    
    /**
     * 删除条目
     * @param contractId
     */
    @BatchDelete
    public void deleteItems(String[] ids);
    
    /**
     * 更新合同条目
     *
     * @param    items
    **/
    @BatchUpdate
    public void updateItem(List<ContractItemEntity> items);
    
    @Get
    public ContractItemEntity getItems(String id);
}
