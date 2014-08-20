package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.contract.ModifyContractEntity;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;

/**
 * 合同变更信息模型
 * @author Ann
 *
 */
public class ContractModifyModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7823733137419860094L;

	/**
	 * 合同变更信息主体
	 */
	private ModifyContractEntity modifyContract;

	/**
	 * 合同变更产品明细
	 */
	private List<ModifyItemEntity> modifyItemList;

	/**
	 * 合同变更批次明细
	 */
	private List<ModifyBatchModel> modifyBatchList;

	public ModifyContractEntity getModifyContract() {
		return modifyContract;
	}

	public void setModifyContract(ModifyContractEntity modifyContract) {
		this.modifyContract = modifyContract;
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
