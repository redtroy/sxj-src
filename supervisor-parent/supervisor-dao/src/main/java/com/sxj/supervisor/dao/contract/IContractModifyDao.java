package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.BatchUpdate;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ModifyBatchEntity;
import com.sxj.supervisor.entity.contract.ModifyContractEntity;
import com.sxj.util.persistent.QueryCondition;


/**
 * 变更合同Dao
 * @author Ann
 *
 */
public interface IContractModifyDao {

	/**
	 * 新增变更合同
	 * @param modifyContractEntity
	 */
	@BatchInsert
	public void addModify(ModifyContractEntity modifyContractEntity);
	/**
	 * 查询变更合同
	 * @param query
	 * @return
	 */
	public  List<ModifyContractEntity> queryModify(QueryCondition<ModifyBatchEntity> query); 
	/**
	 * 更新变更合同
	 * @param modifyContractEntity
	 */
	@BatchUpdate
	public void updateModify(List<ModifyContractEntity> modifyContractEntity);
}
