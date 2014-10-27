package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractReplenishBatchDao;

/**
 * 合同补损批次信息
 * 
 * @author Administrator
 *
 */
@Entity(mapper = IContractReplenishBatchDao.class)
@Table(name = "M_CONTRACT_REPLENISH_BATCH")
public class ReplenishBatchEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5666599729742676725L;

	/**
	 * 主键
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	/**
	 * 批次号
	 */
	@Column(name = "BATCH_NO")
	private String batchNo;

	/**
	 * 变更ID
	 **/
	@Column(name = "REPLENISH_ID")
	private String replenishId;

	/**
	 * RFID号
	 **/
	@Column(name = "RFID_NO")
	private String rfidNo;
	/**
	 * 新RFID号
	 **/
	@Column(name = "NEW_RFID_NO")
	private String newRfidNo;
	/**
	 * 金额
	 **/
	@Column(name = "AMOUNT")
	private Double amount;

	/**
	 * 批次条目JSON
	 **/
	@Column(name = "BATCH_ITEMS")
	private String batchItems;

	public String getNewRfidNo() {
		return newRfidNo;
	}

	public void setNewRfidNo(String newRfidNo) {
		this.newRfidNo = newRfidNo;
	}

	public String getReplenishId() {
		return replenishId;
	}

	public void setReplenishId(String replenishId) {
		this.replenishId = replenishId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBatchItems() {
		return batchItems;
	}

	public void setBatchItems(String batchItems) {
		this.batchItems = batchItems;
	}
}
