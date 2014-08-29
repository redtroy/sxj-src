package com.sxj.supervisor.website.login;

import java.io.Serializable;

public class SupervisorPrincipal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7928061127084410598L;

	private String memberNo;

	private String accountNo;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

}
