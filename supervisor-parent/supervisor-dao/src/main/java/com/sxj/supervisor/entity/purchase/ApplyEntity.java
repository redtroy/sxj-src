package com.sxj.supervisor.entity.purchase;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.purchase.IApplyDao;

/**
 * 私享家采购申报表
 * 
 * @author anshaoshuai
 *
 */
@Entity(mapper = IApplyDao.class)
@Table(name = "M_SXJ_APPLY")
public class ApplyEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3121321314122042038L;

	@Id(column = "ID")
	private String id;

	/**
	 * 采购申报单编号
	 */
	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;

	/**
	 * 采购公司名称
	 */
	@Column(name = "COMPANY")
	private String company;

	/**
	 * (1代表普通玻璃 2代表深加工玻璃 3代表型材 4代表配件)
	 */
	@Column(name = "APPLY_TYPE")
	private Integer applyType;

	/**
	 * 申报的数量
	 */
	@Column(name = "APPLY_NUM")
	private Integer applyNum;

	/**
	 * 申报的价格
	 */
	@Column(name = "PRICE")
	private String price;

	/**
	 * 申报时间
	 */
	@Column(name = "APPLY_TIME")
	private Date applyTime;

	/**
	 * (1代表审核中 2代表审核通过 3代表审核不通过)
	 */
	@Column(name = "APPLY_STATUS")
	private Integer applyStatus;


	/**
	 * 备案合同扫描件存放路径
	 */
	@Column(name = "SCAN_NUMBER")
	private String scanNumber;
	/**
	 * 采购设置记录编号
	 */
	@Column(name = "SET_NUMBER")
	private String setNumber;

	/**
	 * 会员编号
	 */
	@Column(name = "MEMBER_NO")
	private String memberNo;

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

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getApplyType() {
		return applyType;
	}

	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}

	public Integer getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(Integer applyNum) {
		this.applyNum = applyNum;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getScanNumber() {
		return scanNumber;
	}

	public void setScanNumber(String scanNumber) {
		this.scanNumber = scanNumber;
	}

	public String getSetNumber() {
		return setNumber;
	}

	public void setSetNumber(String setNumber) {
		this.setNumber = setNumber;
	}

}
