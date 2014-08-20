package com.sxj.supervisor.model.member;

import java.io.Serializable;
import java.util.Date;

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

	private Date startDate;

	private Date endDate;

	public Pagable getPage() {
		return page;
	}

	public void setPage(Pagable page) {
		this.page = page;
	}

	private Pagable page;

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

}
