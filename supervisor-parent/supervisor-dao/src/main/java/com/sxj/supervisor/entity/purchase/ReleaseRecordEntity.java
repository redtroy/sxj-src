package com.sxj.supervisor.entity.purchase;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.purchase.IReleaseRecordDao;

@Entity(mapper = IReleaseRecordDao.class)
@Table(name = "M_RELEASE_RECORD")
public class ReleaseRecordEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -331596299588830618L;

	@Id(column = "ID")
	private String id;

	/**
	 * 采购设置记录编号
	 */
	@Column(name = "RECORD_NUMBER")
	private String recordNumber;

	/**
	 * 设置采购量的管理员ID
	 */
	@Column(name = "ADMIN_ID")
	private String adminId;

	/**
	 * (1代表普通玻璃 2代表深加工玻璃 3代表型材 4代表配件)
	 */
	@Column(name = "PURCHASE")
	private Integer purchase;

	/**
	 * 设置的采购数量
	 */
	@Column(name = "NUM")
	private Integer num;

	/**
	 * (1代表不报价 2代表报价区间)
	 */
	@Column(name = "PRICE_TYPE")
	private Integer priceType;

	/**
	 * 价格区间
	 */
	@Column(name = "PRICE_RANGE")
	private String priceRange;

	/**
	 * 时间
	 */
	@Column(name = "RELEASE_TIME")
	private Date releaseTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

}
