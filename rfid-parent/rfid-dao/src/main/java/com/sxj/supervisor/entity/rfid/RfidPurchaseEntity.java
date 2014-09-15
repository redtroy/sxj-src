package com.sxj.supervisor.entity.rfid;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;

public class RfidPurchaseEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5185244023917957270L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 采购单编号
	 */
	private String purchaseNo;

	/**
	 * 供应商编号
	 */
	private String supplierNo;

	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * RFID类型
	 */
	private Enum rfidType;

	/**
	 * 数量
	 */
	private Long count;

	/**
	 * 单价
	 */
	private Long price;

	/**
	 * 金额
	 */
	private Long amount;

	/**
	 * 采购日期
	 */
	private Date purchaseDate;

	/**
	 * 招标合同号
	 */
	private String contractNo;

	/**
	 * 申请单编号
	 */
	private String applyNo;

	/**
	 * 导入状态
	 */
	private Enum importState;
	/**
	 * 支付状态
	 */
	private Enum payState;

	/**
	 * 收货状态
	 */
	private Enum receiptState;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Enum getRfidType() {
		return rfidType;
	}

	public void setRfidType(Enum rfidType) {
		this.rfidType = rfidType;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Enum getImportState() {
		return importState;
	}

	public void setImportState(Enum importState) {
		this.importState = importState;
	}

	public Enum getPayState() {
		return payState;
	}

	public void setPayState(Enum payState) {
		this.payState = payState;
	}

	public Enum getReceiptState() {
		return receiptState;
	}

	public void setReceiptState(Enum receiptState) {
		this.receiptState = receiptState;
	}

}
