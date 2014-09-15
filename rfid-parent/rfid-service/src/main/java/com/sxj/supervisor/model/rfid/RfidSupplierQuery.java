package com.sxj.supervisor.model.rfid;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class RfidSupplierQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7797926967653752486L;

	private String id;

	private String supplierNo;

	private String name;

	private String address;

	private String contactName;

	private String contactTel;

	private String telNum;

	private Long doorsPrice;

	private Long batchPrice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public Long getDoorsPrice() {
		return doorsPrice;
	}

	public void setDoorsPrice(Long doorsPrice) {
		this.doorsPrice = doorsPrice;
	}

	public Long getBatchPrice() {
		return batchPrice;
	}

	public void setBatchPrice(Long batchPrice) {
		this.batchPrice = batchPrice;
	}

}
