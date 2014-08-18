package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractImgEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ContractItemHisEntity;

public class ContractModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 980424780684626879L;
	
	private ContractEntity contract;
	
	private List<ContractItemEntity> itemList;
	
	private List<BatchItemModel> batchList;
	
	private List<ContractItemHisEntity> modifyItemList;
	
	private List<BatchItemModel> modifyBatchList;
	
	private List<ContractImgEntity> hisImageList;
	
	private List<StateLogModel> stateLogList;

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

	public List<BatchItemModel> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<BatchItemModel> batchList) {
		this.batchList = batchList;
	}

	public List<ContractItemHisEntity> getModifyItemList() {
		return modifyItemList;
	}

	public void setModifyItemList(List<ContractItemHisEntity> modifyItemList) {
		this.modifyItemList = modifyItemList;
	}

	public List<BatchItemModel> getModifyBatchList() {
		return modifyBatchList;
	}

	public void setModifyBatchList(List<BatchItemModel> modifyBatchList) {
		this.modifyBatchList = modifyBatchList;
	}

	public List<ContractImgEntity> getHisImageList() {
		return hisImageList;
	}

	public void setHisImageList(List<ContractImgEntity> hisImageList) {
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
