package com.sxj.supervisor.manage.controller.contract;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.contract.ReplenishContractEntity;
import com.sxj.supervisor.model.contract.ReplenishBatchModel;

public class ContractReplenishControllerModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6108507997026229157L;

	ReplenishContractEntity replenish;
	
	List<ReplenishBatchModel> batchList;
	
	String contractId;
	
	String recordNo;

	public ReplenishContractEntity getReplenish() {
		return replenish;
	}

	public void setReplenish(ReplenishContractEntity replenish) {
		this.replenish = replenish;
	}

	public List<ReplenishBatchModel> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<ReplenishBatchModel> batchList) {
		this.batchList = batchList;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

}
