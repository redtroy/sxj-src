package com.sxj.supervisor.model.system;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class LogQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 214831343313389844L;

	private String accountNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

}
