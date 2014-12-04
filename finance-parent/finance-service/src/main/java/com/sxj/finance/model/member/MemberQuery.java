package com.sxj.finance.model.member;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class MemberQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 903091959802150138L;

	private String memberNo;

	private String memberName;

	private String contacts;

	private String contactsPhone;

	private String area;

	private String bLicenseNo;

	private String energyNo;

	private Integer memberType;

	private Integer memberState;

	private String startDate;

	private String endDate;

	private Integer checkState;

	private Integer memberTypeB;

	private String isDelMes;
	
	private String phoneNo;
	
	

	public Integer getMemberTypeB() {
		return memberTypeB;
	}

	public void setMemberTypeB(Integer memberTypeB) {
		this.memberTypeB = memberTypeB;
	}

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
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

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getbLicenseNo() {
		return bLicenseNo;
	}

	public void setbLicenseNo(String bLicenseNo) {
		this.bLicenseNo = bLicenseNo;
	}

	public String getEnergyNo() {
		return energyNo;
	}

	public void setEnergyNo(String energyNo) {
		this.energyNo = energyNo;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getMemberState() {
		return memberState;
	}

	public void setMemberState(Integer memberState) {
		this.memberState = memberState;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIsDelMes() {
		return isDelMes;
	}

	public void setIsDelMes(String isDelMes) {
		this.isDelMes = isDelMes;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

}
