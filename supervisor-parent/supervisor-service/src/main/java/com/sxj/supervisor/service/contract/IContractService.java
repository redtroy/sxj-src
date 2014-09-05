package com.sxj.supervisor.service.contract;

import java.util.List;

import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractModifyModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.StateLogModel;

public interface IContractService {
	/**
	 * 新增合同
	 *
	 * @param    contract
	 * @param    itemList
	 * @param    batchList
	**/
	public void addContract(ContractEntity contract, List<ContractItemEntity> itemList,String recordId);
	
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
	 * 根据合同号获取合同
	 *
	 * @param    id
	**/
	public ContractModel getContractByContractNo(String contractNo);
	
	/**
	 * 查询合同列表
	 *
	 * @param    query
	**/
	public List<ContractModel> queryContracts(ContractQuery query);
	
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
	public void changeContract(String contractId, ContractModifyModel model, String recordNo,List<ContractItemEntity> itemList);
	
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

	/**
	 * 根据合同号获取详情
	 * @param contractNo
	 * @return
	 */
	public ContractModel getContractModelByContractNo(String contractNo);
	
	
}
