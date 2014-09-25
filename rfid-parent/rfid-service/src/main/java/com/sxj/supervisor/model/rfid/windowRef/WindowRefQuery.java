package com.sxj.supervisor.model.rfid.windowRef;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class WindowRefQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1600746374179450627L;

	/**
	 * RFID关联申请号
	 */
	private String rfidRefNo;

	/**
	 * RFID编号区间
	 */
	private String rfidRange;

	/**
	 * 申请人
	 */
	private String memberNo;

	/**
	 * 申请人名称
	 */
	private String memberName;

	/**
	 * 关联类型
	 */
	private String type;

	/**
	 * 窗型代号
	 */
	private String windowsNo;


	/**
	 * 型材批次
	 */
	private String batchNo;

	private String state;
	/**
	 * 关联申请时间
	 */
	private String startDate;

	private String endDate;

	/**
	 * 被补损RFID
	 */
	private String replenishRfid;


	public String getRfidRefNo() {
		return rfidRefNo;
	}

	public void setRfidRefNo(String rfidRefNo) {
		this.rfidRefNo = rfidRefNo;
	}

	public String getRfidRange() {
		return rfidRange;
	}

	public void setRfidRange(String rfidRange) {
		this.rfidRange = rfidRange;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWindowsNo() {
		return windowsNo;
	}

	public void setWindowsNo(String windowsNo) {
		this.windowsNo = windowsNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReplenishRfid() {
		return replenishRfid;
	}

	public void setReplenishRfid(String replenishRfid) {
		this.replenishRfid = replenishRfid;
	}


	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
