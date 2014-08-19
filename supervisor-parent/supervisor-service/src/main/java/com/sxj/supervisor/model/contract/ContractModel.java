package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractBatchHisEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractImgHisEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ContractItemHisEntity;

public class ContractModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 980424780684626879L;
	
	private ContractEntity contract;
	
	private List<ContractItemEntity> itemList;
	
	private List<ContractBatchEntity> batchList;
	
	private List<ContractItemHisEntity> modifyItemList;
	
	private List<ContractBatchHisEntity> modifyBatchList;
	
	private List<ContractImgHisEntity> hisImageList;
	
	private List<StateLogModel> stateLogList;

	public List<ContractBatchHisEntity> getModifyBatchList() {
		return modifyBatchList;
	}

	public void setModifyBatchList(List<ContractBatchHisEntity> modifyBatchList) {
		this.modifyBatchList = modifyBatchList;
	}

	public List<ContractBatchEntity> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<ContractBatchEntity> batchList) {
		this.batchList = batchList;
	}


	public ContractEntity getContract() {
		return contract;
	}

	public void setContract(ContractEntity contract) {
		this.contract = contract;
	}

	public List<ContractItemEntity> getItemList() {
		return itemList;
	}

	public void setItemList(List<ContractItemEntity> itemList) {
		this.itemList = itemList;
	}


	public List<ContractItemHisEntity> getModifyItemList() {
		return modifyItemList;
	}

	public void setModifyItemList(List<ContractItemHisEntity> modifyItemList) {
		this.modifyItemList = modifyItemList;
	}


	public List<ContractImgHisEntity> getHisImageList() {
		return hisImageList;
	}

	public void setHisImageList(List<ContractImgHisEntity> hisImageList) {
		this.hisImageList = hisImageList;
	}

	public List<StateLogModel> getStateLogList() {
		return stateLogList;
	}

	public void setStateLogList(List<StateLogModel> stateLogList) {
		this.stateLogList = stateLogList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
