package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ModifyBatchEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 批次变更管理Dao
 * @author Ann
 *
 */
public interface IContractModifyBatchDao {
	/**
	 * 添加批次信息
	 *
	 * @param    batchs
	**/
	@BatchInsert
	public void addBatchs(List<ModifyBatchEntity> batchs);
	
	/**
	 * 获取批次信息
	 *
	 * @param    id
	**/
	@Get
	public ModifyBatchEntity getBatch(String id);
	
	/**
	 * 查询批次列表
	 *
	 * @param    contractId
	**/
	public List<ModifyBatchEntity> queryBacths(QueryCondition<ModifyBatchEntity> query);
	/**
	 * 刪除批次
	 * @param contractId
	 */
	@Delete
	public void deleteBatchs(String contractId);
	/**
	 * 更新批次
	 * @param items
	 */
	@BatchUpdate
	public void updateItems(List<ModifyBatchEntity> items);
}
