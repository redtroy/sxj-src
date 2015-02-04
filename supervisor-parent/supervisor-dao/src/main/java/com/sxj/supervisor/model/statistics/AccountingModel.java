package com.sxj.supervisor.model.statistics;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.enu.record.ContractTypeEnum;

public class AccountingModel extends Pagable implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1740563758638710001L;

	private String id;

	private String contractNo;

	private String recordNo;

	private String memberNameA;

	private String memberNameB;

	private String contractType;

	private ContractTypeEnum type;

	private Date signDate;

	private Double amount;

	private Double payAmount;

	private Double noPayAmount;

	private Float speed;

	private Integer state;

	private Double amountItem;
	private Double amountModifyItem;
	private Double amountBatch;
	private Double amountModifyBatch;

	/**
	 * 合同进度
	 */
	private Integer progress = 0;

	private String payNo;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

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

	public Double getAmountItem() {
		return amountItem;
	}

	public void setAmountItem(Double amountItem) {
		this.amountItem = amountItem;
	}

	public Double getAmountModifyItem() {
		return amountModifyItem;
	}

	public void setAmountModifyItem(Double amountModifyItem) {
		this.amountModifyItem = amountModifyItem;
	}

	public Double getAmountBatch() {
		return amountBatch;
	}

	public void setAmountBatch(Double amountBatch) {
		this.amountBatch = amountBatch;
	}

	public Double getAmountModifyBatch() {
		return amountModifyBatch;
	}

	public void setAmountModifyBatch(Double amountModifyBatch) {
		this.amountModifyBatch = amountModifyBatch;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public ContractTypeEnum getType() {
		return type;
	}

	public void setType(ContractTypeEnum type) {
		this.type = type;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
