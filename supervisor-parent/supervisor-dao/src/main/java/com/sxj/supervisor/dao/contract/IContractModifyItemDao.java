package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;

public interface IContractModifyItemDao
{
    /**
     * 新增合同条目
     *
     * @param    items
    **/
    @BatchInsert
    public void addItems(List<ModifyItemEntity> items);
    
    /**
     *  通过合同ID查询条目列表
     *
     * @param    contractId
    **/
    public List<ModifyItemEntity> queryItems(String modifyId);
    
    public List<ModifyItemEntity> queryItemsByModifyIds(String... modifyIds);
    
    /**
     * 刪除條目
     * @param contractId
     */
    @Delete
    public void deleteItems(String contractId);
    
    /**
     * 更新條目
     * @param items
     */
    @BatchUpdate
    public void updateItems(List<ModifyItemEntity> items);
}
