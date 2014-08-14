package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;

public interface IContractBatchDao {
	/**
	 * 添加批次信息
	 *
	 * @param    batchs
	**/
	@Insert
	public void addBatchs(ContractBatchEntity[] batchs);
	
	/**
	 * 获取批次信息
	 *
	 * @param    id
	**/
	@Get
	public ContractBatchEntity getBatch(String id);
	
	/**
	 * 查询批次列表
	 *
	 * @param    contractId
	**/
	public List<ContractBatchEntity> queryBacths(String contractId);
	
	@Delete
	public void deleteBatchs(String contractId);
}
