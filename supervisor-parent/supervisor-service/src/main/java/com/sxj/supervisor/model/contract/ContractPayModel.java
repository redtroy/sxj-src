package com.sxj.supervisor.model.contract;

import java.io.Serializable;

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
	 * 会员类型，区分会员是甲方还是乙方
	 */
	private String memberType;

	private String memberName_A;

	private String memberName_B;

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
	 * 实际支付金额
	 */
	private Long payReal;

	/**
	 * 支付开始日期
	 */
	private String startPayDate;

	/**
	 * 支付结束日期
	 */
	private String endPayDate;

	private String content;

	/**
	 * 状态
	 */
	private String state;

	/**
	 * 支付内容状态
	 * 
	 */
	private String PayContentState;

	private String type;

	private String payMode;

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getPayContentState() {
		return PayContentState;
	}

	public String getMemberName_A() {
		return memberName_A;
	}

	public void setMemberName_A(String memberName_A) {
		this.memberName_A = memberName_A;
	}

	public String getMemberName_B() {
		return memberName_B;
	}

	public void setMemberName_B(String memberName_B) {
		this.memberName_B = memberName_B;
	}

	public void setPayContentState(String payContentState) {
		PayContentState = payContentState;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public Long getPayReal() {
		return payReal;
	}

	public void setPayReal(Long payReal) {
		this.payReal = payReal;
	}

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

	public String getStartPayDate() {
		return startPayDate;
	}

	public void setStartPayDate(String startPayDate) {
		this.startPayDate = startPayDate;
	}

	public String getEndPayDate() {
		return endPayDate;
	}

	public void setEndPayDate(String endPayDate) {
		this.endPayDate = endPayDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
