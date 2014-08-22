package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.contract.ContractItemEntity;

/**
 *  合同明细Dao
 * @author Administrator
 *
 */
public interface IContractItemDao {
	/**
	 * 新增合同条目
	 *
	 * @param    items
	**/
	@Insert
	public void addItem(ContractItemEntity items);
	
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
	@Delete
	public void deleteItems(String contractId);
}
