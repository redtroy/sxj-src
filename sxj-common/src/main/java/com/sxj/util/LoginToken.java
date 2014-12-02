package com.sxj.util;

import java.io.Serializable;

public class LoginToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5280890825159932198L;

	private String memberNo;

	private String memberName;

	private String password;

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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getMemberNo());
		sb.append(",");
		sb.append(getMemberName());
		sb.append(",");
		sb.append(getPassword());
		return sb.toString();
	}

}
