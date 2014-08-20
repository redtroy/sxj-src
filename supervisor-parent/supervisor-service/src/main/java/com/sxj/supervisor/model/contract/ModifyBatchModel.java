package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.contract.ModifyBatchEntity;

/**
 * 合同变更批次信息
 * @author Ann
 *
 */
public class ModifyBatchModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1420459713047138620L;

	/**
	 * 变更合同批次信息
	 */
	private ModifyBatchEntity modifyBatch;

	/**
	 * 批次条目JSON
	 **/
	private List<BatchItemModel> modifyBatchItems;

	public ModifyBatchEntity getModifyBatch() {
		return modifyBatch;
	}

	public void setModifyBatch(ModifyBatchEntity modifyBatch) {
		this.modifyBatch = modifyBatch;
	}

	public List<BatchItemModel> getModifyBatchItems() {
		return modifyBatchItems;
	}

	public void setModifyBatchItems(List<BatchItemModel> modifyBatchItems) {
		this.modifyBatchItems = modifyBatchItems;
	}

}
