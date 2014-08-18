package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.Date;

public class ContractQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3963424020853310164L;

	/**
	 * 合同号
	**/
	private String contractNo;
	
	/**
	 * 备案号
	**/
	private String recordNo;
	
	/**
	 * 签订者会员ID
	**/
	private String memberId;
	
	/**
	 * 合同类型
	**/
	private Integer contractType;
	
	/**
	 * 关联招标合同号
	**/
	private String refContractNo;
	
	/**
	 * 开始签订日期
	**/
	private Date startCreateDate;
	
	/**
	 * 结束签订日期
	**/
	private Date endCreateDate;
	
	/**
	 * 开始备案日期
	**/
	private Date startRecordDate;
	
	/**
	 * 结束备案日期
	**/
	private Date endRecordDate;
	
	/**
	 * 确认状态
	**/
	private Integer confirmState;
	
	/**
	 * 合同状态
	**/
	private Integer state;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public String getRefContractNo() {
		return refContractNo;
	}

	public void setRefContractNo(String refContractNo) {
		this.refContractNo = refContractNo;
	}

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Date getStartRecordDate() {
		return startRecordDate;
	}

	public void setStartRecordDate(Date startRecordDate) {
		this.startRecordDate = startRecordDate;
	}

	public Date getEndRecordDate() {
		return endRecordDate;
	}

	public void setEndRecordDate(Date endRecordDate) {
		this.endRecordDate = endRecordDate;
	}

	public Integer getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(Integer confirmState) {
		this.confirmState = confirmState;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
