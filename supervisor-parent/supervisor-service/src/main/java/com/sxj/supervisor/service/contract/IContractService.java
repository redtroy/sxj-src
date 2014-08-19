package com.sxj.supervisor.service.contract;

import java.util.List;

import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ContractItemHisEntity;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.StateLogModel;
import com.sxj.util.persistent.ResultList;

public interface IContractService {
	/**
	 * 新增合同
	 *
	 * @param    contract
	 * @param    itemList
	 * @param    batchList
	**/
	public void addContract(ContractEntity contract, List<ContractItemEntity> itemList, List<ContractBatchEntity> batchList);
	
	/**
	 * 修改合同
	 *
	 * @param    contract
	**/
	public void modifyContract(ContractModel contract);
	
	/**
	 * 获取合同
	 *
	 * @param    id
	**/
	public ContractModel getContract(String id);
	
	/**
	 * 查询合同列表
	 *
	 * @param    query
	**/
	public ResultList<ContractModel> queryContracts(ContractQuery query);
	
	/**
	 * 删除合同
	 *
	 * @param    id
	**/
	public void deleteContract(String id);
	
	/**
	 * 变更合同
	 *
	 * @param    contractId
	 * @param    itemList
	 * @param    batchList
	 * @param    recordNo
	**/
	public void changeContract(String contractId, List<ContractItemHisEntity> itemList, List<ContractBatchModel> batchList, String recordNo);
	
	/**
	 * 补损合同
	 *
	 * @param    contractId
	 * @param    batchList
	 * @param    recordNo
	**/
	public void suppContract(String contractId, List<ContractBatchModel> batchList, String recordNo);
	/**
	 * 更新状态
	 * @param contractId
	 * @param state
	 */
	public void modifyState(String contractId, Integer state);
	
	/**
	 * 新增合同状态变更记录
	 * @param stateLog
	 * @param contractId
	 */
	public void addStateLog(StateLogModel stateLog, String contractId);
	
	/**
	 * 变更确认状态
	 * @param contractId
	 * @param state
	 */
	public void modifyCheckState(String contractId, Integer state);
}
