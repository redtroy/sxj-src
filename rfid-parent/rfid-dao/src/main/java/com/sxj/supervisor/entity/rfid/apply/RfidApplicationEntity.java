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
import com.sxj.supervisor.dao.rfid.apply.IRfidApplicationDao;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.apply.PayStateEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;

/**
 * RFID申请单
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IRfidApplicationDao.class)
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
	@Sn(pattern = "0000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValueProperty = "dateNo")
	private String applyNo;

	private String dateNo;

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
	private RfidTypeEnum rfidType;

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
	private PayStateEnum payState;

	/**
	 * 收货状态
	 */
	@Column(name = "RECEIPT_STATE")
	private ReceiptStateEnum receiptState;

	/**
	 * 逻辑删除标记
	 */
	@Column(name = "DEL_STATE")
	private Boolean delstate = false;
	/**
	 * 已申请数量
	 */
	@Column(name = "HAS_NUMBER")
	private Long hasNumber;

	public Long getHasNumber() {
		// if (hasNumber == null) {
		// hasNumber = 0l;
		// }
		return hasNumber;
	}

	public void setHasNumber(Long hasNumber) {
		this.hasNumber = hasNumber;
	}

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

	public RfidTypeEnum getRfidType() {
		return rfidType;
	}

	public void setRfidType(RfidTypeEnum rfidType) {
		this.rfidType = rfidType;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Long getCount() {
		// if (count == null) {
		// count = 0l;
		// }
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

	public PayStateEnum getPayState() {
		return payState;
	}

	public void setPayState(PayStateEnum payState) {
		this.payState = payState;
	}

	public ReceiptStateEnum getReceiptState() {
		return receiptState;
	}

	public void setReceiptState(ReceiptStateEnum receiptState) {
		this.receiptState = receiptState;
	}

	public Boolean getDelstate() {
		return delstate;
	}

	public void setDelstate(Boolean delstate) {
		this.delstate = delstate;
	}

	public String getDateNo() {
		return dateNo;
	}

	public void setDateNo(String dateNo) {
		this.dateNo = dateNo;
	}

}
