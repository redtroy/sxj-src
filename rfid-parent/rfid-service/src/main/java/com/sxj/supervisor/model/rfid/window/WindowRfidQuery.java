package com.sxj.supervisor.model.rfid.window;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class WindowRfidQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 582736904982577633L;
	/**
	 * RFID编号
	 */
	private String rfidNo;

	/**
	 * 最小RFID编号
	 */
	private String minRfidNo;

	/**
	 * 最大RFID编号
	 */
	private String maxRfidNo;

	/**
	 * 采购单号
	 */
	private String purchaseNo;

	/**
	 * 申请会员号
	 */
	private String memberNo;

	/**
	 * 采购合同号
	 */
	private String contractNo;

	/**
	 * 窗型代号
	 */
	private String windowType;

	/**
	 * RFID
	 */
	private String rfid;

	/**
	 * 玻璃RFID
	 */
	private String glassRfid;

	/**
	 * 型材RFID
	 */
	private String profileRfid;

	/**
	 * 导入日期开始
	 */
	private String startImportDate;
	/**
	 * 导入日期结束
	 */
	private String endImportDate;

	/**
	 * RFID状态
	 */
	private Integer rfidState;

	/**
	 * 进度状态
	 */
	private Integer progressState;

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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getWindowType() {
		return windowType;
	}

	public void setWindowType(String windowType) {
		this.windowType = windowType;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

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

	public Integer getRfidState() {
		return rfidState;
	}

	public void setRfidState(Integer rfidState) {
		this.rfidState = rfidState;
	}

	public Integer getProgressState() {
		return progressState;
	}

	public void setProgressState(Integer progressState) {
		this.progressState = progressState;
	}

	public String getGlassRfid() {
		return glassRfid;
	}

	public void setGlassRfid(String glassRfid) {
		this.glassRfid = glassRfid;
	}

	public String getProfileRfid() {
		return profileRfid;
	}

	public void setProfileRfid(String profileRfid) {
		this.profileRfid = profileRfid;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMinRfidNo() {
		return minRfidNo;
	}

	public void setMinRfidNo(String minRfidNo) {
		this.minRfidNo = minRfidNo;
	}

	public String getMaxRfidNo() {
		return maxRfidNo;
	}

	public void setMaxRfidNo(String maxRfidNo) {
		this.maxRfidNo = maxRfidNo;
	}

}
