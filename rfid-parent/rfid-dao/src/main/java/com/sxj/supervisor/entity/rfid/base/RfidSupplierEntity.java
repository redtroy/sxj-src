package com.sxj.supervisor.entity.rfid.base;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.rfid.base.IRfidSupplierDao;

/**
 * RFID供应商
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IRfidSupplierDao.class)
@Table(name = "R_RFID_SUPPLIER")
public class RfidSupplierEntity extends Pagable implements Serializable {

	private static final long serialVersionUID = -6361496754557084957L;

	/**
	 * ID
	 */
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * 供应商NO
	 */
	@Column(name = "SUPPLIER_NO")
	@Sn(pattern = "000000", step = 1, table = "T_SN", stubValue = "GY", stub = "F_SN_NAME", sn = "F_SN_NUMBER")
	private String supplierNo;

	/**
	 * 供应商名称
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * 地址
	 */
	@Column(name = "ADDRESS")
	private String address;

	/**
	 * 联系人姓名
	 */
	@Column(name = "CONTRACT_NAME")
	private String contactName;

	/**
	 * 联系电话
	 */
	@Column(name = "CONTRACT_TEL")
	private String contactTel;

	/**
	 * 固定电话
	 */
	@Column(name = "TEL_NAME")
	private String telNum;

	/**
	 * 门窗标签采购价
	 */
	@Column(name = "DOORS_PRICE")
	private Long doorsPrice;

	/**
	 * 批次标签采购价
	 */
	@Column(name = "BATCH_PRICE")
	private Long batchPrice;

	/**
	 * 逻辑删除
	 */
	@Column(name = "DEL_STATE")
	private Boolean delstate = false;

	public Boolean getDelstate() {
		return delstate;
	}

	public void setDelstate(Boolean delstate) {
		this.delstate = delstate;
	}

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
