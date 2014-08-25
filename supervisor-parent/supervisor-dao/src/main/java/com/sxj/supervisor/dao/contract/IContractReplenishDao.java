package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;
import com.sxj.supervisor.entity.contract.ReplenishContractEntity;
import com.sxj.util.persistent.QueryCondition;
/**
 * 合同补损Dao
 * @author Ann
 *
 */
public interface IContractReplenishDao {

	/**
	 * 新增补损
	 * @param replenish
	 */
	@Insert
	public void addReplenish(ReplenishContractEntity replenish);
	
	/**
	 * 更新补损
	 * @param replenish
	 */
	@Update
	public void updateReplenish(List<ReplenishContractEntity> replenish);
	
	/**
	 * 条件查询补损信息
	 * @param query
	 * @return
	 */
	public List<ReplenishContractEntity> queryReplenish(QueryCondition<ReplenishContractEntity> query);
	
}
