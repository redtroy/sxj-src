package com.sxj.supervisor.entity.contract;

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
import com.sxj.supervisor.dao.contract.IContractDao;

/**
 * 合同实体
 * @author Administrator
 *
 */
@Entity(mapper = IContractDao.class)
@Table(name = "M_CONTRACT")
public class ContractEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1859300557618319342L;

	/**
	 * 主键ID
	**/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	/**
	 * 合同号
	**/
	@Column(name = "CONTRACT_NO")
	@Sn(pattern = "000000", step = 1, table = "T_SN", stubValue = "CT", stub = "F_SN_NAME", sn = "F_SN_NUMBER")
	private String contractNo;
	
	/**
	 * 签订地址
	**/
	@Column(name = "ADDRESS")
	private String address;
	
	/**
	 * 甲方
	**/
	@Column(name = "MEMBER_ID_A")
	private String memberIdA;
	
	/**
	 * 乙方
	**/
	@Column(name = "MEMBER_ID_B")
	private String memberIdB;
	
	/**
	 * 工程名称
	**/
	@Column(name = "ENG_NAME")
	private String engName;
	
	/**
	 * 工程地址
	**/
	@Column(name = "ENG_ADDRESS")
	private String engAddress;
	
	/**
	 * 合同备案号
	**/
	@Column(name = "RECORD_NO")
	private String recordNo;
	
	/**
	 * 签订日期
	**/
	@Column(name = "SIGNED_DATE")
	private Date signedDate;
	
	/**
	 * 合同期限
	**/
	@Column(name = "VALIDITY_DATE")
	private Date validityDate;
	
	/**
	 * 生效日期
	**/
	@Column(name = "START_DATE")
	private Date startDate;
	
	
	/**
	 * 生成时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	/**
	 * 备案时间
	 */
	@Column(name = "RECORD_DATE")
	private Date recordDate;
	
	/**
	 * 合同定金
	**/
	@Column(name = "DEPOSIT")
	private Long deposit;
	
	/**
	 * 备注
	**/
	@Column(name = "REMARKS")
	private String remarks;
	
	/**
	 * 交货地址
	**/
	@Column(name = "DELIVERY_ADDRESS")
	private String deliveryAddress;
	
	/**
	 * 状态
	**/
	@Column(name = "STATE")
	private Integer state;
	/**
	 * 确认状态状态
	**/
	@Column(name = "CONFIRM_STATE")
	private Integer confirmState;
	/**
	 * 合同类型
	**/
	@Column(name = "TYPE")
	private Integer type;
	
	/**
	 * 关联合同号
	**/
	@Column(name = "REF_CONTRACT_NO")
	private String refContractNo;
	
	/**
	 * 甲方名称
	**/
	@Column(name = "MEMBER_NAME_A")
	private String memberNameA;
	
	/**
	 * 乙方名称
	**/
	@Column(name = "MEMBER_NAME_B")
	private String memberNameB;
	
	/**
	 * 合同状态变更记录(JOSN)
	**/
	@Column(name = "STATE_LOG")
	private String stateLog;
	
	/**
	 * 总批次
	**/
	@Column(name = "BATCH_COUNT")
	private Integer batchCount;
	/**
	 * 当前更新备案号
	 */
	@Column(name = "NOW_RECORD_NO")
	private String nowRecordNo;
	/**
	 * 扫描件路径
	 */
	@Column(name = "IMG_PATH")
	private String imgPath;
	
	public Integer getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(Integer confirmState) {
		this.confirmState = confirmState;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(Integer batchCount) {
		this.batchCount = batchCount;
	}

	public String getNowRecordNo() {
		return nowRecordNo;
	}

	public void setNowRecordNo(String nowRecordNo) {
		this.nowRecordNo = nowRecordNo;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemberIdA() {
		return memberIdA;
	}

	public void setMemberIdA(String memberIdA) {
		this.memberIdA = memberIdA;
	}

	public String getMemberIdB() {
		return memberIdB;
	}

	public void setMemberIdB(String memberIdB) {
		this.memberIdB = memberIdB;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getEngAddress() {
		return engAddress;
	}

	public void setEngAddress(String engAddress) {
		this.engAddress = engAddress;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public Date getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getDeposit() {
		return deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRefContractNo() {
		return refContractNo;
	}

	public void setRefContractNo(String refContractNo) {
		this.refContractNo = refContractNo;
	}

	public String getMemberNameA() {
		return memberNameA;
	}

	public void setMemberNameA(String memberNameA) {
		this.memberNameA = memberNameA;
	}

	public String getMemberNameB() {
		return memberNameB;
	}

	public void setMemberNameB(String memberNameB) {
		this.memberNameB = memberNameB;
	}

	public String getStateLog() {
		return stateLog;
	}

	public void setStateLog(String stateLog) {
		this.stateLog = stateLog;
	}

}
