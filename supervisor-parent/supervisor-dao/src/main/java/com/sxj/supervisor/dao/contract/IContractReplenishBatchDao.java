package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
import com.sxj.util.persistent.QueryCondition;
/**
 * 补损合同批次Dao
 * @author Ann
 *
 */
public interface IContractReplenishBatchDao {

	/**
	 * 新增补损
	 * @param replenishBatchEntity
	 */
	@BatchInsert
	public void addReplenishBatch(List<ReplenishBatchEntity> replenishBatchEntity);
	
	/**
	 * 更改补损
	 * @param replenishBatchEntity
	 */
	@BatchUpdate
	public void updateReplenishBatch(List<ReplenishBatchEntity> replenishBatchEntity);
	/**
	 * 查询补损合同
	 * @param replenishId
	 * @return
	 */
	public List<ReplenishBatchEntity> queryReplenishBatch(QueryCondition<ReplenishBatchEntity> query);
}
