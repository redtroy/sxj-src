package com.sxj.supervisor.model.rfid.logistics;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class LogisticsRfidQuery extends Pagable implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 5870553555521406581L;
	/**
	 * 导入日期开始
	 */
	private String startImportDate;
	/**
	 * 导入日期结束
	 */
	private String endImportDate;

	/**
	 * RFID编号
	 */
	private String rfidNo;

	/**
	 * 采购单号
	 */
	private String purchaseNo;

	/**
	 * 申请单号
	 */
	private String applyNo;

	/**
	 * RFID类型
	 */
	private String type;

	/**
	 * 申请会员号
	 */
	private String memberNo;

	/**
	 * 申请会员名称
	 */
	private String memberName;

	/**
	 * 采购合同号
	 */
	private String contractNo;

	/**
	 * 执行批次号
	 */
	private String batchNo;

	/**
	 * 关联补损单
	 */
	private String replenishNo;

	/**
	 * RFID状态
	 */
	private String rfidState;

	/**
	 * 进度状态
	 */
	private String progressState;

	/**
	 * 执行日志
	 */
	private String log;

	public String getStartImportDate() {
		return startImportDate;
	}

	public void setStartImportDate(String startImportDate) {
		this.startImportDate = startImportDate;
	}

	public String getEndImportDate() {
		return endImportDate;
	}

	public void setEndImportDate(String endImportDate) {
		this.endImportDate = endImportDate;
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

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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

	public String getReplenishNo() {
		return replenishNo;
	}

	public void setReplenishNo(String replenishNo) {
		this.replenishNo = replenishNo;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRfidState() {
		return rfidState;
	}

	public void setRfidState(String rfidState) {
		this.rfidState = rfidState;
	}

	public String getProgressState() {
		return progressState;
	}

	public void setProgressState(String progressState) {
		this.progressState = progressState;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

}
