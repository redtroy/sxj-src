package com.sxj.supervisor.entity.rfid.logistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.rfid.logistics.ILogisticsRfidDao;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;
import com.sxj.supervisor.model.rfid.RfidLog;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.StringUtils;

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
	private String rfidNo;

	@Column(name = "GENERATE_KEY")
	private Long generateKey;

	/**
	 * 申请单号
	 */
	@Column(name = "APPLY_NO")
	private String applyNo;

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
	 * 是否补损批次
	 */
	@Column(name = "IS_LOSS_BATCH")
	private Boolean isLossBatch;

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
	/**
	 * gid
	 */
	@Column(name = "GID")
	private String gid;
	private List<RfidLog> logList = new ArrayList<RfidLog>();

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

	public Boolean getIsLossBatch() {
		return isLossBatch;
	}

	public void setIsLossBatch(Boolean isLossBatch) {
		this.isLossBatch = isLossBatch;
	}

	public void setRfidState(RfidStateEnum rfidState) {
		this.rfidState = rfidState;
		if (RfidStateEnum.DAMAGED.equals(getRfidState())) {
			RfidLog rfidLog = new RfidLog();
			rfidLog.setId(getRfidState().getId());
			rfidLog.setState(getRfidState().getName());
			rfidLog.setDate(DateTimeUtils.getDateTime());
			setLogList(rfidLog);
		}
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
		if (getProgressState() != null) {
			RfidLog log = new RfidLog();
			log.setId(getProgressState().getId());
			log.setState(getProgressState().getName());
			log.setDate(DateTimeUtils.getDateTime());
			setLogList(log);
		}

	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public List<RfidLog> getLogList() {
		if (StringUtils.isEmpty(getLog())) {
			return logList;
		} else {
			logList = JsonMapper.nonEmptyMapper().fromJson(
					getLog(),
					new JsonMapper().contructCollectionType(ArrayList.class,
							RfidLog.class));
			if (logList == null) {
				logList = new ArrayList<RfidLog>();
			}
			return logList;
		}
	}

	public void removeLog(RfidLog log) {
		if (getLogList() != null) {
			for (Iterator<RfidLog> iterator = logList.iterator(); iterator
					.hasNext();) {
				RfidLog rfidLog = iterator.next();
				if (rfidLog == null) {
					continue;
				}
				if (rfidLog.getId() == log.getId()
						&& log.getState().equals(rfidLog.getState())) {
					iterator.remove();
				}
			}

		}
		String json = JsonMapper.nonEmptyMapper().toJson(logList);
		setLog(json);
	}

	private void setLogList(RfidLog log) {
		getLogList().add(log);
		String json = JsonMapper.nonEmptyMapper().toJson(logList);
		setLog(json);

	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.getId() != null) {
			sb.append(this.getId());
		}
		sb.append("|");
		if (this.getRfidNo() != null) {
			sb.append(this.getRfidNo());
		}
		sb.append("|");
		if (this.getGenerateKey() != null) {
			sb.append(this.getGenerateKey());
		}
		sb.append("|");
		if (this.getMemberName() != null) {
			sb.append(this.getMemberName());
		}
		sb.append("|");
		if (this.getMemberNo() != null) {
			sb.append(this.getMemberNo());
		}
		sb.append("|");
		if (this.getApplyNo() != null) {
			sb.append(this.getApplyNo());
		}
		sb.append("|");
		if (this.getPurchaseNo() != null) {
			sb.append(this.getPurchaseNo());
		}
		sb.append("|");
		if (this.getContractNo() != null) {
			sb.append(this.getContractNo());
		}
		sb.append("|");
		if (this.getType() != null) {
			sb.append(this.getType().getId());
		}
		sb.append("|");
		if (this.getImportDate() != null) {
			sb.append(DateTimeUtils.formatFullDate(this.getImportDate()));
		}
		sb.append("|");
		if (this.getBatchNo() != null) {
			sb.append(this.getBatchNo());
		}
		sb.append("|");
		if (this.getIsLossBatch() != null) {
			if (this.getIsLossBatch()) {
				sb.append(1);
			} else {
				sb.append(0);
			}
		}
		sb.append("|");
		if (this.getReplenishNo() != null) {
			sb.append(this.getReplenishNo());
		}
		sb.append("|");
		if (this.getRfidState() != null) {
			sb.append(this.getRfidState().getId());
		}
		sb.append("|");
		if (this.getLog() != null) {
			sb.append(this.getLog());
		}
		sb.append("|");
		if (this.getProgressState() != null) {
			sb.append(this.getProgressState().getId());
		}
		sb.append("|");
		if (this.getGid() != null) {
			sb.append(this.getGid());
		}
		return sb.toString();
	}

}
