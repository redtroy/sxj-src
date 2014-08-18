package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.List;

public class ContractBatchModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2009473407892728810L;

	/**
	 * 主键
	**/
	private String id;
	
	/**
	 * 合同编号
	**/
	private String contractId;
	
	/**
	 * RFID号
	**/
	private String rfidNo;
	
	/**
	 * 金额
	**/
	private Long amount;
	
	/**
	 * 批次条目JSON
	**/
	private List<BatchItemModel> batchItems;
	
	private String recordNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public List<BatchItemModel> getBatchItems() {
		return batchItems;
	}

	public void setBatchItems(List<BatchItemModel> batchItems) {
		this.batchItems = batchItems;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
}
