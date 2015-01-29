package com.sxj.supervisor.service.contract;

import com.sxj.supervisor.entity.contract.ContractEntity;

public interface IContractProcessService {

	/**
	 * 变更审核状态
	 * 
	 * @param contractId
	 * @param state
	 */
	public void modifyCheckState(ContractEntity contract);

	/**
	 * 变更确认状态
	 * 
	 * @param contractId
	 * @param state
	 */
	public void modifyConfirmState(ContractEntity contract);

	void addContractPay(String contractNo);

}
