package com.sxj.supervisor.entity.rfid;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;

public class LogisticsRfidEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 797319101390939750L;

	private String id;

	private String rfidNo;

	private String purchaseNo;

	private Enum type;

	private String memberId;

	private String memberName;

	private String contractNo;

	private String batchNo;

	private Date importDate;

	private String replenishNo;

	private Enum rfidState;

	private Enum progressState;

	private String log;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Enum getType() {
		return type;
	}

	public void setType(Enum type) {
		this.type = type;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getReplenishNo() {
		return replenishNo;
	}

	public void setReplenishNo(String replenishNo) {
		this.replenishNo = replenishNo;
	}

	public Enum getRfidState() {
		return rfidState;
	}

	public void setRfidState(Enum rfidState) {
		this.rfidState = rfidState;
	}

	public Enum getProgressState() {
		return progressState;
	}

	public void setProgressState(Enum progressState) {
		this.progressState = progressState;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}
