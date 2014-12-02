package com.sxj.finance.entity;

import java.io.Serializable;
import java.util.Date;

import com.sxj.finance.dao.finance.FinanceDao;
import com.sxj.finance.enu.finance.PayStageEnum;
import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

@Entity(mapper = FinanceDao.class)
@Table(name = "F_FINANCE")
public class FinanceEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

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
	 * 参考应付金额
	 */
	@Column(name = "PAY_AMOUNT")
	private Double payAmount;

	/**
	 * 融资金额
	 */
	@Column(name = "FINANCE_AMOUNT")
	private Double financeAmount;

	/**
	 * 放款金额
	 */
	@Column(name = "CREDIT_AMOUNT")
	private Double creditAmount;

	/**
	 * 支付内容
	 */
	@Column(name = "CONTENT")
	private String content;

	/**
	 * 付款状态
	 */
	@Column(name = "STATE")
	private PayStageEnum state;

	/**
	 * 申请融资时间
	 */
	@Column(name = "APPLY_DATE")
	private Date applyDate;

	/**
	 * 付款时间
	 */
	@Column(name = "PAY_DATE")
	private Date payDate;

	/**
	 * 受理时间
	 */
	@Column(name = "ACCEPT_DATE")
	private Date acceptDate;

	/**
	 * 搁置时间
	 */
	@Column(name = "SHELVE_DATE")
	private Date shelveDate;

	/**
	 * 创建时间
	 */
	@Column(name = "CREAT_DATE")
	private Date creatDate;

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

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(Double financeAmount) {
		this.financeAmount = financeAmount;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PayStageEnum getState() {
		return state;
	}

	public void setState(PayStageEnum state) {
		this.state = state;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Date getShelveDate() {
		return shelveDate;
	}

	public void setShelveDate(Date shelveDate) {
		this.shelveDate = shelveDate;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

}
