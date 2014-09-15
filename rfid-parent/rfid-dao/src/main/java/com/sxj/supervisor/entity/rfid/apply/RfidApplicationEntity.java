package com.sxj.supervisor.entity.rfid.apply;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * RFID申请单
 * 
 * @author dujinxin
 *
 */
@Entity
@Table(name = "R_RFID_APPLICATION")
public class RfidApplicationEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 722815321767317494L;

	/**
	 * id
	 */
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * RFID申请单编号
	 */
	@Column(name = "APPLY_NO")
	@Sn(pattern = "000000", step = 1, table = "T_SN", stubValue = "RF", stub = "F_SN_NAME", sn = "F_SN_NUMBER")
	private String applyNo;

	/**
	 * 申请会员ID
	 */
	@Column(name = "MEMBER_NO")
	private String memberNo;

	/**
	 * 申请会员名称
	 */
	@Column(name = "MEMBER_NAME")
	private String memberName;

	/**
	 * RFID类型
	 */
	@Column(name = "RFID_TYPE")
	private Enum rfidType;

	/**
	 * 招标合同号
	 */
	@Column(name = "CONTRACT_NO")
	private String contractNo;

	/**
	 * 申请数量
	 */
	@Column(name = "COUNT")
	private Long count;

	/**
	 * 申请日期
	 */
	@Column(name = "APPLY_DATE")
	private Date applyDate;
	
	 /**
	  * 支付状态
	  */
	@Column(name = "PAY_STATE")
	private Enum payState;
	
	/**
	 * 收货状态
	 */
	@Column(name = "RECEIPT_STATE")
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
