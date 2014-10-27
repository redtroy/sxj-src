package com.sxj.supervisor.model.record;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class RecordQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7160738924586342611L;

	private String recordNo;

	private String memberId;

	private String applyId;

	private Integer contractType;

	private String contractNo;

	private String confirmState;

	private Integer recordType;

	private Integer state;

	private String memberIdA;

	private String memberIdB;

	private Boolean delState = false;

	private String startApplyDate;

	private String endApplyDate;

	private String startAcceptDate;

	private String endAcceptDate;

	private String contractPepole;

	private String sort;// 排序

	private String sortColumn;// 排序字段

	private Integer flag;

	private String isDelMes;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(String confirmState) {
		this.confirmState = confirmState;
	}

	public String getContractPepole() {
		return contractPepole;
	}

	public void setContractPepole(String contractPepole) {
		this.contractPepole = contractPepole;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public Boolean getDelState() {
		return delState;
	}

	public void setDelState(Boolean delState) {
		this.delState = delState;
	}

	public String getMemberIdA() {
		return memberIdA;
	}

	public void setMemberIdA(String memberIdA) {
		this.memberIdA = memberIdA;
	}

	public String getMemberIdB() {
		return memberIdB;
	}

	public void setMemberIdB(String memberIdB) {
		this.memberIdB = memberIdB;
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

	public String getIsDelMes() {
		return isDelMes;
	}

	public void setIsDelMes(String isDelMes) {
		this.isDelMes = isDelMes;
	}

}
