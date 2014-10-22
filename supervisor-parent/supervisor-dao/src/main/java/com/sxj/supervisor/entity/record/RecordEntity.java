package com.sxj.supervisor.entity.record;

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
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;

/**
 * 备案实体类
 * 
 * @author Administrator
 *
 */
@Entity(mapper = IRecordDao.class)
@Table(name = "M_CONTRACT_RECORD")
public class RecordEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5309323695383014154L;

	/**
	 * 主键ID
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * 备案号
	 **/
	@Column(name = "RECORD_NO")
	@Sn(pattern = "0000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER",stubValueProperty="dateNo")
	private String recordNo;

	private String dateNo;
	/**
	 * 标记
	 */
	@Column(name = "RECORD_FLAG")
	private RecordFlagEnum flag;

	/**
	 * 申请会员ID
	 **/
	@Column(name = "APPLY_ID")
	private String applyId;

	/**
	 * 申请会员名称
	 **/
	@Column(name = "APPLY_NAME")
	private String applyName;

	/**
	 * 甲方会员ID
	 **/
	@Column(name = "MEMBER_ID_A")
	private String memberIdA;

	/**
	 * 甲方会员名称
	 **/
	@Column(name = "MEMBER_NAME_A")
	private String memberNameA;

	/**
	 * 乙方会员ID
	 **/
	@Column(name = "MEMBER_ID_B")
	private String memberIdB;

	/**
	 * 乙方会员名称
	 **/
	@Column(name = "MEMBER_NAME_B")
	private String memberNameB;
	/**
	 * 备案类型
	 **/
	@Column(name = "TYPE")
	private RecordTypeEnum type;
	/**
	 * 备案确认状态
	 **/
	@Column(name = "CONFIRM_STATE")
	private RecordConfirmStateEnum confirmState;
	/**
	 * 备案扫描件
	 **/
	@Column(name = "IMG_PATH")
	private String imgPath;

	/**
	 * 备案状态
	 **/
	@Column(name = "STATE")
	private RecordStateEnum state;

	/**
	 * 合同类型
	 **/
	@Column(name = "CONTRACT_TYPE")
	private ContractTypeEnum contractType;

	/**
	 * 绑定合同号
	 **/
	@Column(name = "CONTRACT_NO")
	private String contractNo;

	/**
	 * 关联招标合同
	 **/
	@Column(name = "REF_CONTRACT_NO")
	private String refContractNo;

	/**
	 * 申请时间
	 **/
	@Column(name = "APPLY_DATE")
	private Date applyDate;

	/**
	 * 受理时间
	 **/
	@Column(name = "ACCEPT_DATE")
	private Date acceptDate;

	/**
	 * 删除标记
	 */
	@Column(name = "DEL_STATE")
	private Boolean delState = false;

	/**
	 * 补损RFID
	 */
	@Column(name = "RFID_NO")
	private String rfidNo;
	
	public String getDateNo() {
		return dateNo;
	}

	public void setDateNo(String dateNo) {
		this.dateNo = dateNo;
	}

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public RecordConfirmStateEnum getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(RecordConfirmStateEnum confirmState) {
		this.confirmState = confirmState;
	}

	public Boolean getDelState() {
		return delState;
	}

	public void setDelState(Boolean delState) {
		this.delState = delState;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getMemberIdA() {
		return memberIdA;
	}

	public void setMemberIdA(String memberIdA) {
		this.memberIdA = memberIdA;
	}

	public String getMemberNameA() {
		return memberNameA;
	}

	public void setMemberNameA(String memberNameA) {
		this.memberNameA = memberNameA;
	}

	public String getMemberIdB() {
		return memberIdB;
	}

	public void setMemberIdB(String memberIdB) {
		this.memberIdB = memberIdB;
	}

	public String getMemberNameB() {
		return memberNameB;
	}

	public void setMemberNameB(String memberNameB) {
		this.memberNameB = memberNameB;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public RecordTypeEnum getType() {
		return type;
	}

	public void setType(RecordTypeEnum type) {
		this.type = type;
	}

	public RecordStateEnum getState() {
		return state;
	}

	public void setState(RecordStateEnum state) {
		this.state = state;
	}

	public ContractTypeEnum getContractType() {
		return contractType;
	}

	public void setContractType(ContractTypeEnum contractType) {
		this.contractType = contractType;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getRefContractNo() {
		return refContractNo;
	}

	public void setRefContractNo(String refContractNo) {
		this.refContractNo = refContractNo;
	}

	public RecordFlagEnum getFlag() {
		return flag;
	}

	public void setFlag(RecordFlagEnum flag) {
		this.flag = flag;
	}

}
