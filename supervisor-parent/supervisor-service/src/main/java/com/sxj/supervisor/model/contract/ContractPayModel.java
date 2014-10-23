package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;

public class ContractPayModel extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6437430614715862144L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 会员ID
	 */
	private String memberNo;

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
	 * 支付开始日期
	 */
	private Date startPayDate;

	/**
	 * 支付结束日期
	 */
	private Date endPayDate;

	private String content;

	/**
	 * 状态
	 */
	private Integer state;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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

	public Date getStartPayDate() {
		return startPayDate;
	}

	public void setStartPayDate(Date startPayDate) {
		this.startPayDate = startPayDate;
	}

	public Date getEndPayDate() {
		return endPayDate;
	}

	public void setEndPayDate(Date endPayDate) {
		this.endPayDate = endPayDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
