package com.sxj.supervisor.website.login;

import java.io.Serializable;

public class LoginToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5280890825159932198L;

	private String sessioId;

	private String memberNo;

	private String memberName;

	private String password;

	public String getSessioId() {
		return sessioId;
	}

	public void setSessioId(String sessioId) {
		this.sessioId = sessioId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
