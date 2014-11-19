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
import com.sxj.supervisor.dao.rfid.ref.ILogisticsRefDao;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.ref.AssociationTypesEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;

/**
 * 物流RFID关联申请
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = ILogisticsRefDao.class)
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
	@Sn(pattern = "0000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValueProperty = "dateNo")
	private String rfidRefNo;

	private String dateNo;

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
	private RfidTypeEnum rfidType;

	/**
	 * 关联类型
	 */
	@Column(name = "TYPE")
	private AssociationTypesEnum type;

	/**
	 * 批次
	 */
	@Column(name = "BATCH_NO")
	private String batchNo;

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

	/**
	 * 审核状态
	 * 
	 * @return
	 */
	@Column(name = "STATE")
	private AuditStateEnum state;

	/**
	 * 逻辑删除标记
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

	public AuditStateEnum getState() {
		return state;
	}

	public void setState(AuditStateEnum state) {
		this.state = state;
	}

	public String getDateNo() {
		return dateNo;
	}

	public void setDateNo(String dateNo) {
		this.dateNo = dateNo;
	}

}
