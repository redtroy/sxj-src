package com.sxj.supervisor.entity.rfid.logistics;

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
import com.sxj.supervisor.dao.rfid.logistics.ILogisticsRfidDao;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;

/**
 * 物流认证标签
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = ILogisticsRfidDao.class)
@Table(name = "R_LOGISTICS_RFID")
public class LogisticsRfidEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 797319101390939750L;

	/**
	 * ID
	 */
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * RFID编号
	 */
	@Column(name = "RFID_NO")
	@Sn(pattern = "000000", step = 1, table = "T_SN", stubValue = "AAAA", stub = "F_SN_NAME", sn = "F_SN_NUMBER")
	private String rfidNo;

	@Column(name = "GENERATE_KEY")
	private Long generateKey;

	/**
	 * 采购单号
	 */
	@Column(name = "PURCHASE_NO")
	private String purchaseNo;

	/**
	 * RFID类型
	 */
	@Column(name = "TYPE")
	private RfidTypeEnum type;

	/**
	 * 申请会员号
	 */
	@Column(name = "MEMBER_NO")
	private String memberNo;

	/**
	 * 申请会员名称
	 */
	@Column(name = "MEMBER_NAME")
	private String memberName;

	/**
	 * 采购合同号
	 */
	@Column(name = "CONTRACT_NO")
	private String contractNo;

	/**
	 * 执行批次号
	 */
	@Column(name = "BATCH_NO")
	private String batchNo;

	/**
	 * 导入日期
	 */
	@Column(name = "IMPORT_DATE")
	private Date importDate;

	/**
	 * 关联补损单
	 */
	@Column(name = "REPLENISH_NO")
	private String replenishNo;

	/**
	 * RFID状态
	 */
	@Column(name = "RFID_STATE")
	private RfidStateEnum rfidState;

	/**
	 * 进度状态
	 */
	@Column(name = "PROGRESS_STATE")
	private LabelStateEnum progressState;

	/**
	 * 执行日志
	 */
	@Column(name = "LOG")
	private String log;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getReplenishNo() {
		return replenishNo;
	}

	public void setReplenishNo(String replenishNo) {
		this.replenishNo = replenishNo;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public RfidTypeEnum getType() {
		return type;
	}

	public void setType(RfidTypeEnum type) {
		this.type = type;
	}

	public RfidStateEnum getRfidState() {
		return rfidState;
	}

	public void setRfidState(RfidStateEnum rfidState) {
		this.rfidState = rfidState;
	}

	public Long getGenerateKey() {
		return generateKey;
	}

	public void setGenerateKey(Long generateKey) {
		this.generateKey = generateKey;
	}

	public LabelStateEnum getProgressState() {
		return progressState;
	}

	public void setProgressState(LabelStateEnum progressState) {
		this.progressState = progressState;
	}

}
