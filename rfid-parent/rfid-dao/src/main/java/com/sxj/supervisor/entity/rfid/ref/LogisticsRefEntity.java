package com.sxj.supervisor.entity.rfid.ref;

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
 * 物流RFID关联申请
 * 
 * @author dujinxin
 *
 */
@Entity
@Table(name = "R_LOGISTICE_REF")
public class LogisticsRefEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2653603292849169190L;

	/**
	 * ID
	 */
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * RFID关联申请号
	 */
	@Column(name = "RFID_REF_NO")
	@Sn(pattern = "000000", step = 1, table = "T_SN", stubValue = "GL", stub = "F_SN_NAME", sn = "F_SN_NUMBER")
	private String rfidRefNo;

	/**
	 * RFID编号
	 */
	@Column(name = "RFID_NO")
	private String rfidNo;

	/**
	 * 申请人
	 */
	@Column(name = "MEMBER_NO")
	private String memberNo;

	/**
	 * 申请人名称
	 */
	@Column(name = "MEMBER_NAME")
	private String memberName;
	
	/**
	 * RFID类型
	 */
	@Column(name = "RFID_TYPE")
	private Enum rfidType;

	/**
	 * 关联类型
	 */
	@Column(name = "TYPE")
	private Enum type;

	/**
	 * 批次
	 */
	@Column(name = "BATCH_ID")
	private String batchId;

	/**
	 * 关联申请时间
	 */
	@Column(name = "APPLY_DATE")
	private Date applyDate;

	/**
	 * 被补损RFID
	 */
	@Column(name = "REPLENISH_RFID")
	private String replenishRfid;

	/**
	 * 采购合同号
	 */
	@Column(name = "CONTRACT_NO")
	private String contractNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRfidRefNo() {
		return rfidRefNo;
	}

	public void setRfidRefNo(String rfidRefNo) {
		this.rfidRefNo = rfidRefNo;
	}

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
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

	public Enum getType() {
		return type;
	}

	public void setType(Enum type) {
		this.type = type;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getReplenishRfid() {
		return replenishRfid;
	}

	public void setReplenishRfid(String replenishRfid) {
		this.replenishRfid = replenishRfid;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


}
