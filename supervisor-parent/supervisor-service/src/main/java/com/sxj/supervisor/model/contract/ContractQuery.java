package com.sxj.supervisor.model.contract;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class ContractQuery extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3963424020853310164L;

	/**
	 * 合同号
	 **/
	private String contractId;

	/**
	 * 合同号
	 **/
	private String contractNo;

	/**
	 * 动态感应关键字
	 */
	private String keyword;

	/**
	 * 备案号
	 **/
	private String recordNo;

	/**
	 * 签订者会员ID
	 **/
	private String memberId;

	/**
	 * 签订者会员名称
	 **/
	private String memberName;
	/**
	 * 合同类型
	 **/
	private String contractType;

	/**
	 * 关联招标合同号
	 **/
	private String refContractNo;

	/**
	 * 开始生成日期
	 **/
	private String startCreateDate;

	/**
	 * 结束生成日期
	 **/
	private String endCreateDate;

	/**
	 * 开始备案日期
	 **/
	private String startRecordDate;

	/**
	 * 结束备案日期
	 **/
	private String endRecordDate;

	/**
	 * 确认状态
	 **/
	private String confirmState;

	/**
	 * 合同状态
	 **/
	private String state;

	private String memberIdA;

	private String memberIdB;
	
	/**
	 * 工程地址
	 */
	private String engAddress;

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

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getRefContractNo() {
		return refContractNo;
	}

	public void setRefContractNo(String refContractNo) {
		this.refContractNo = refContractNo;
	}

	public String getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(String confirmState) {
		this.confirmState = confirmState;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getStartRecordDate() {
		return startRecordDate;
	}

	public void setStartRecordDate(String startRecordDate) {
		this.startRecordDate = startRecordDate;
	}

	public String getEndRecordDate() {
		return endRecordDate;
	}

	public void setEndRecordDate(String endRecordDate) {
		this.endRecordDate = endRecordDate;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getEngAddress() {
		return engAddress;
	}

	public void setEngAddress(String engAddress) {
		this.engAddress = engAddress;
	}

}
