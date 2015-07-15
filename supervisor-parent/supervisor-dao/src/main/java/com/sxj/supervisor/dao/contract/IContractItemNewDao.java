package com.sxj.supervisor.dao.contract;

import java.util.List;
import java.util.Map;

import com.sxj.mybatis.orm.annotations.BatchDelete;
import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.supervisor.entity.contract.ContractItemNewEntity;

public interface IContractItemNewDao
{
    /**
     * 查询条目
     * @param contractNo
     * @return
     */
    public List<ContractItemNewEntity> queryContractItem(String contractNo,String recordNo,Integer state); 
    
    /**
     * 添加条目
     * @param contractItem
     */
    @BatchInsert
    public void addContractItem(ContractItemNewEntity contractItem);
    
    /**
     * 修改条目
     * @param contractItem
     */
    @BatchUpdate
    public void updateContractItem(ContractItemNewEntity contractItem);
    
    /**
     * 批量删除条目
     * @param ids
     */
    @BatchDelete
    public void deleteContractItem(String[] ids);
    
    /**
     * 新增合同条目
     *
     * @param    items
    **/
    public void addItem(Map<String, Object> map);
}
