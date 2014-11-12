package com.sxj.supervisor.entity.rfid.sale;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.rfid.sale.IRfidSaleStatisticalDao;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;

/**
 * RFID销售管理
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IRfidSaleStatisticalDao.class)
@Table(name = "R_RFID_SALE")
public class RfidSaleStatisticalEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8520704830409785937L;

	/**
	 * ID
	 */
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

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
	@Column(name = "RFID_TYPE")
	private RfidTypeEnum rfidType;

	/**
	 * 销售价格
	 */
	@Column(name = "PRICE")
	private Double price;

	/**
	 * 销售数量
	 */
	@Column(name = "COUNT")
	private Long count;

	/**
	 * 销售日期
	 */
	@Column(name = "SALE_DATE")
	private Date saleDate;

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

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public RfidTypeEnum getRfidType() {
		return rfidType;
	}

	public void setRfidType(RfidTypeEnum rfidType) {
		this.rfidType = rfidType;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
