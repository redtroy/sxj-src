package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
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
	public void addItem(List<ContractItemEntity> items);
	
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
	
	/**
	 * 更新合同条目
	 *
	 * @param    items
	**/
	@Update
	public void updateItem(List<ContractItemEntity> items);
}
