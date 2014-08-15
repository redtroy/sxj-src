package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 批次条目变更实体
 * @author Administrator
 *
 */
public class ContractBatchHisEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5666599729742676725L;

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
	private String batchItems;

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

	public String getBatchItems() {
		return batchItems;
	}

	public void setBatchItems(String batchItems) {
		this.batchItems = batchItems;
	}
}
