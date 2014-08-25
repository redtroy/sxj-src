package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IContractModifyItemDao {
	/**
	 * 新增合同条目
	 *
	 * @param    items
	**/
	@Insert
	public void addItems(ModifyItemEntity items);
	
	/**
	 *  通过合同ID查询条目列表
	 *
	 * @param    contractId
	**/
	public List<ModifyItemEntity> queryItems(String modifyId);
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
	@Update
	public void updateItems(List<ModifyItemEntity> items);
}
