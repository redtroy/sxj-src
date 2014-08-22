package com.sxj.supervisor.model.member;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;

public class AccountQuery extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5168931378789020273L;

	private String memberNo;
	private String accountId;
	private String accountName;
	private Integer state;
	private Date startDate;
	private Date endDate;
	private String roleId;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
