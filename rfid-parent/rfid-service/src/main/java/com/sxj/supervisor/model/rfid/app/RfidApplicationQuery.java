package com.sxj.supervisor.model.rfid.app;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class RfidApplicationQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8169572254319014313L;

	private String id;

	/**
	 * RFID申请单编号
	 */
	private String applyNo;

	/**
	 * 申请会员ID
	 */
	private String memberNo;

	/**
	 * 申请会员名称
	 */
	private String memberName;

	/**
	 * RFID类型
	 */
	private Integer rfidType;

	/**
	 * 招标合同号
	 */
	private String contractNo;

	/**
	 * 申请数量
	 */
	private Long count;

	/**
	 * 申请开始日期
	 */
	private String starApplyDate;
	/**
	 * 申请结束日期
	 */
	private String endApplyDate;

	/**
	 * 支付状态
	 */
	private Integer payState;

	/**
	 * 收货状态
	 */
	private Integer receiptState;
	/**
	 * 逻辑删除标记
	 */
	private Boolean delstate = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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

	public Integer getRfidType() {
		return rfidType;
	}

	public void setRfidType(Integer rfidType) {
		this.rfidType = rfidType;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getStarApplyDate() {
		return starApplyDate;
	}

	public void setStarApplyDate(String starApplyDate) {
		this.starApplyDate = starApplyDate;
	}

	public String getEndApplyDate() {
		return endApplyDate;
	}

	public void setEndApplyDate(String endApplyDate) {
		this.endApplyDate = endApplyDate;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Integer getReceiptState() {
		return receiptState;
	}

	public void setReceiptState(Integer receiptState) {
		this.receiptState = receiptState;
	}

	public Boolean getDelstate() {
		return delstate;
	}

	public void setDelstate(Boolean delstate) {
		this.delstate = delstate;
	}

}
