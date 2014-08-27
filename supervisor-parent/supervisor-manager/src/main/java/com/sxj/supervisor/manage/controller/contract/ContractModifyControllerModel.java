package com.sxj.supervisor.manage.controller.contract;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ModifyContractEntity;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;
import com.sxj.supervisor.model.contract.ModifyBatchModel;

public class ContractModifyControllerModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1007366964021085218L;
	
	ModifyContractEntity modifyContract;
	
	String contractId;
	
	List<ContractItemEntity> itemList;
	
	String recordNo;
	
	List<ModifyItemEntity> modifyItemList;
	
	List<ModifyBatchModel> modifyBatchList;
	

	public ModifyContractEntity getModifyContract() {
		return modifyContract;
	}

	public void setModifyContract(ModifyContractEntity modifyContract) {
		this.modifyContract = modifyContract;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public List<ContractItemEntity> getItemList() {
		return itemList;
	}

	public void setItemList(List<ContractItemEntity> itemList) {
		this.itemList = itemList;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public List<ModifyItemEntity> getModifyItemList() {
		return modifyItemList;
	}

	public void setModifyItemList(List<ModifyItemEntity> modifyItemList) {
		this.modifyItemList = modifyItemList;
	}

	public List<ModifyBatchModel> getModifyBatchList() {
		return modifyBatchList;
	}

	public void setModifyBatchList(List<ModifyBatchModel> modifyBatchList) {
		this.modifyBatchList = modifyBatchList;
	}

}
