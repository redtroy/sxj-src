package com.sxj.supervisor.entity.pay;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.supervisor.dao.contract.IContractPayDao;
import com.sxj.supervisor.enu.contract.PayStageEnum;

/**
 * 支付单
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IContractPayDao.class)
@Table(name = "M_CONTRACT_PAY")
public class PayRecordEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1316548893613242787L;

	/**
	 * ID
	 */
	@Id(column = "ID")
	private String id;

	/**
	 * 会员ID
	 */
	@Column(name = "MEMBER_NO")
	private String memberNo;

	/**
	 * 支付单号
	 */
	@Column(name = "PAY_NO")
	private String payNo;

	/**
	 * 合同号
	 */
	@Column(name = "CONTRACT_NO")
	private String contractNo;

	/**
	 * 批次号
	 */
	@Column(name = "BATCH_NO")
	private String batchNo;

	/**
	 * RFID编号
	 */
	@Column(name = "RFID_NO")
	private String rfidNo;

	/**
	 * 支付金额
	 */
	@Column(name = "PAY_AMOUNT")
	private Long payAmount;

	/**
	 * 支付日期
	 */
	@Column(name = "PAY_DATE")
	private Date payDate;

	/**
	 * 支付内容
	 */
	@Column(name = "CONTENT")
	private String content;

	/**
	 * 状态
	 */
	@Column(name = "STATE")
	private PayStageEnum state;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
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

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public PayStageEnum getState() {
		return state;
	}

	public void setState(PayStageEnum state) {
		this.state = state;
	}

}
