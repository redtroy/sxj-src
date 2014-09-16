package com.sxj.supervisor.model.rfid.window;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.RfidStateEnum;

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
	 * 采购单号
	 */
	private String purchaseNo;

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
	private String rfidState;

	/**
	 * 进度状态
	 */
	private String progressState;

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

}
