package com.sxj.supervisor.model.rfid.ref;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.enu.rfid.ref.AssociationTypesEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.RfidTypeEnum;

public class LogisticsRefQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4241985427147288845L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * RFID关联申请号
	 */
	private String rfidRefNo;

	/**
	 * RFID编号
	 */
	private String rfidNo;

	/**
	 * 申请人
	 */
	private String memberNo;

	/**
	 * 申请人名称
	 */
	private String memberName;

	/**
	 * RFID类型
	 */
	private RfidTypeEnum rfidType;

	/**
	 * 关联类型
	 */
	private AssociationTypesEnum type;

	/**
	 * 批次
	 */
	private String batchNo;

	/**
	 * 关联开始申请时间
	 */
	private String starApplyDate;

	/**
	 * 关联结束申请时间
	 */
	private String endApplyDate;

	/**
	 * 被补损RFID
	 */
	private String replenishRfid;

	/**
	 * 采购合同号
	 */
	private String contractNo;

	/**
	 * 审核状态
	 * 
	 * @return
	 */
	private AuditStateEnum state;

	/**
	 * 逻辑删除标记
	 */
	private Boolean delstate = false;

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

	public RfidTypeEnum getRfidType() {
		return rfidType;
	}

	public void setRfidType(RfidTypeEnum rfidType) {
		this.rfidType = rfidType;
	}

	public AssociationTypesEnum getType() {
		return type;
	}

	public void setType(AssociationTypesEnum type) {
		this.type = type;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getStarApplyDate() {
		return starApplyDate;
	}

	public void setStarApplyDate(String starApplyDate) {
		this.starApplyDate = starApplyDate;
	}

	public String getEndApplyDate() {
		return endApplyDate;
	}

	public void setEndApplyDate(String endApplyDate) {
		this.endApplyDate = endApplyDate;
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

	public AuditStateEnum getState() {
		return state;
	}

	public void setState(AuditStateEnum state) {
		this.state = state;
	}

	public Boolean getDelstate() {
		return delstate;
	}

	public void setDelstate(Boolean delstate) {
		this.delstate = delstate;
	}

}
