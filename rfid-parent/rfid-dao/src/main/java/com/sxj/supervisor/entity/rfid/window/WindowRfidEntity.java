package com.sxj.supervisor.entity.rfid.window;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.rfid.window.IWindowRfidDao;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;

/**
 * 门窗RFID管理
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IWindowRfidDao.class)
@Table(name = "R_WINDOW_RFID")
public class WindowRfidEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1734629786698988694L;

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
	 * 采购单号
	 */
	@Column(name = "PURCHASE_NO")
	private String purchaseNo;

	/**
	 * 招标合同号
	 */
	@Column(name = "CONTRACT_NO")
	private String contractNo;

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
	 * 窗型代号
	 */
	@Column(name = "WINDOW_TYPE")
	private WindowTypeEnum windowType;
	/**
	 * 玻璃RFID
	 */
	@Column(name = "GLASS_RFID")
	private String glassRfid;

	/**
	 * 型材RFID
	 */
	@Column(name = "PROFILE_RFID")
	private String profileRfid;

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
	private LabelProgressEnum progressState;

	/**
	 * 执行日志
	 */
	@Column(name = "LOG")
	private String log;

	private List<Object> logList = new ArrayList<Object>();

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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public WindowTypeEnum getWindowType() {
		return windowType;
	}

	public void setWindowType(WindowTypeEnum windowType) {
		this.windowType = windowType;
	}

	public String getGlassRfid() {
		return glassRfid;
	}

	public void setGlassRfid(String glassRfid) {
		this.glassRfid = glassRfid;
	}

	public String getProfileRfid() {
		return profileRfid;
	}

	public void setProfileRfid(String profileRfid) {
		this.profileRfid = profileRfid;
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

	public RfidStateEnum getRfidState() {
		return rfidState;
	}

	public void setRfidState(RfidStateEnum rfidState) {
		this.rfidState = rfidState;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Long getGenerateKey() {
		return generateKey;
	}

	public void setGenerateKey(Long generateKey) {
		this.generateKey = generateKey;
	}

	public LabelProgressEnum getProgressState() {
		return progressState;
	}

	public void setProgressState(LabelProgressEnum progressState) {
		this.progressState = progressState;
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

	public List<Object> getLogList() throws Exception {
		if (getLog() == null) {
			return logList;
		} else {
			logList = JsonMapper.nonEmptyMapper().getMapper()
					.readValue(getLog(), new TypeReference<List<Object>>() {
					});
			return logList;
		}

	}

	public void setLogList(Object log) throws Exception {
		getLogList().add(log);
		String json = JsonMapper.nonEmptyMapper().toJson(getLogList());
		setLog(json);

	}
}
