package com.sxj.supervisor.model.record;

import java.io.Serializable;
import java.util.Date;

public class RecordQuery implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7160738924586342611L;

	private String recrodNo;
	
	private String memberId;
	
	private String applyId;
	
	private Integer contractType;
	
	private String contractNo;
	
	private Integer recordType;
	
	private Integer state;
	
	private String startApplyDate;
	
	private String endApplyDate;
	
	private String startAcceptDate;
	
	private String endAcceptDate;

	public String getRecrodNo() {
		return recrodNo;
	}

	public void setRecrodNo(String recrodNo) {
		this.recrodNo = recrodNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStartApplyDate() {
		return startApplyDate;
	}

	public void setStartApplyDate(String startApplyDate) {
		this.startApplyDate = startApplyDate;
	}

	public String getEndApplyDate() {
		return endApplyDate;
	}

	public void setEndApplyDate(String endApplyDate) {
		this.endApplyDate = endApplyDate;
	}

	public String getStartAcceptDate() {
		return startAcceptDate;
	}

	public void setStartAcceptDate(String startAcceptDate) {
		this.startAcceptDate = startAcceptDate;
	}

	public String getEndAcceptDate() {
		return endAcceptDate;
	}

	public void setEndAcceptDate(String endAcceptDate) {
		this.endAcceptDate = endAcceptDate;
	}

	
}
