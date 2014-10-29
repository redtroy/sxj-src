package com.sxj.supervisor.model.rfid.statistics;

import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;

public class AccountingModel extends Pagable {

	private String contractNo;

	private String recordNo;

	private String memberNameA;

	private String memberNameB;

	private String contractType;

	private Date signDate;

	private Double amount;

	private Double payAmount;

	private Double noPayAmount;

	private Float speed;

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

	public String getMemberNameA() {
		return memberNameA;
	}

	public void setMemberNameA(String memberNameA) {
		this.memberNameA = memberNameA;
	}

	public String getMemberNameB() {
		return memberNameB;
	}

	public void setMemberNameB(String memberNameB) {
		this.memberNameB = memberNameB;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getNoPayAmount() {
		return noPayAmount;
	}

	public void setNoPayAmount(Double noPayAmount) {
		this.noPayAmount = noPayAmount;
	}

	public Float getSpeed() {
		return speed;
	}

	public void setSpeed(Float speed) {
		this.speed = speed;
	}

}
