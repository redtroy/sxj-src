package com.sxj.supervisor.model.member;

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

	/**
	 * 认证时间
	 */
	private String startAuthorDate;

	private String endAuthorDate;

	private Integer checkState;

	private Integer memberTypeB;

	private String isDelMes;

	private String isDelMesPerfect;

	private String isDelChangeImage;

	private String flag;

	private String changeImageFlag;

	private Integer sort;

	private Integer filterStr;
	/**
	 * 资质等级
	 */
	private String levelId;

	private String levelName;

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

	public String getStartAuthorDate() {
		return startAuthorDate;
	}

	public void setStartAuthorDate(String startAuthorDate) {
		this.startAuthorDate = startAuthorDate;
	}

	public String getEndAuthorDate() {
		return endAuthorDate;
	}

	public void setEndAuthorDate(String endAuthorDate) {
		this.endAuthorDate = endAuthorDate;
	}

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	public Integer getMemberTypeB() {
		return memberTypeB;
	}

	public void setMemberTypeB(Integer memberTypeB) {
		this.memberTypeB = memberTypeB;
	}

	public String getIsDelMes() {
		return isDelMes;
	}

	public void setIsDelMes(String isDelMes) {
		this.isDelMes = isDelMes;
	}

	public String getIsDelMesPerfect() {
		return isDelMesPerfect;
	}

	public void setIsDelMesPerfect(String isDelMesPerfect) {
		this.isDelMesPerfect = isDelMesPerfect;
	}

	public String getIsDelChangeImage() {
		return isDelChangeImage;
	}

	public void setIsDelChangeImage(String isDelChangeImage) {
		this.isDelChangeImage = isDelChangeImage;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getChangeImageFlag() {
		return changeImageFlag;
	}

	public void setChangeImageFlag(String changeImageFlag) {
		this.changeImageFlag = changeImageFlag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getFilterStr() {
		return filterStr;
	}

	public void setFilterStr(Integer filterStr) {
		this.filterStr = filterStr;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
