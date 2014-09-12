package com.sxj.supervisor.entity.rfid;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;

/**
 * RFID申请单
 * 
 * @author dujinxin
 *
 */
public class RfidApplicationEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 722815321767317494L;

	/**
	 * id
	 */
	private String id;

	/**
	 * RFID申请单编号
	 */
	private String applyNo;

	/**
	 * 申请会员ID
	 */
	private String memberNo;

	/**
	 * 申请会员名称
	 */
	private String memberName;

	/**
	 * RFID类型
	 */
	private Enum rfidType;

	/**
	 * 招标合同号
	 */
	private String contractNo;

	/**
	 * 申请数量
	 */
	private Long count;

	/**
	 * 申请日期
	 */
	private Date applyDate;
	
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

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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

	public Enum getRfidType() {
		return rfidType;
	}

	public void setRfidType(Enum rfidType) {
		this.rfidType = rfidType;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
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
