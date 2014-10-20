package com.sxj.supervisor.entity.pay;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付单
 * 
 * @author dujinxin
 *
 */
public class PayRecordEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1316548893613242787L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 支付单号
	 */
	private String payNo;

	/**
	 * 合同号
	 */
	private String contractNo;

	/**
	 * 批次号
	 */
	private String batchNo;

	/**
	 * RFID编号
	 */
	private String rfidNo;

	/**
	 * 支付金额
	 */
	private Long payAmount;

	/**
	 * 支付日期
	 */
	private Date payDate;

	/**
	 * 状态
	 */
	private Enum state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
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

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Enum getState() {
		return state;
	}

	public void setState(Enum state) {
		this.state = state;
	}

}
