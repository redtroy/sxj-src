package com.sxj.supervisor.model.open;

import java.io.Serializable;
import java.util.List;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.model.contract.BatchItemModel;

public class Bacth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6372666478348126292L;

	private BatchNo batch;
	
	private List<BatchItemModel> batchItems;

	public BatchNo getBatch() {
		return batch;
	}

	public void setBatch(BatchNo batch) {
		this.batch = batch;
	}

	public List<BatchItemModel> getBatchItems() {
		return batchItems;
	}

	public void setBatchItems(List<BatchItemModel> batchItems) {
		this.batchItems = batchItems;
	}
	
}
