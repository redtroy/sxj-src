package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
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
	@Insert
	public void addReplenishBatch(ReplenishBatchEntity[] replenishBatchEntity);
	
	/**
	 * 更改补损
	 * @param replenishBatchEntity
	 */
	@Update
	public void updateReplenishBatch(List<ReplenishBatchEntity> replenishBatchEntity);
	/**
	 * 查询不损合同
	 * @param replenishId
	 * @return
	 */
	public List<ReplenishBatchEntity> queryReplenishBatch(String replenishId);
}
