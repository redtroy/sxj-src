package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractBatchDao;

/**
 * 批次条目实体
 * @author Administrator
 *
 */
@Entity(mapper = IContractBatchDao.class)
@Table(name = "M_CONTRACT_BATCH")
public class ContractBatchEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -918867108456859467L;

	/**
	 * 主键
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	/**
	 * 合同编号
	 **/
	@Column(name = "CONTRACT_ID")
	private String contractId;

	/**
	 * RFID号
	 **/
	@Column(name = "RFID_NO")
	private String rfidNo;

	/**
	 * 金额
	 **/
	@Column(name = "AMOUNT")
	private Long amount;

	/**
	 * 批次条目JSON
	 **/
	@Column(name = "BATCH_ITEMS")
	private String batchItems;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getBatchItems() {
		return batchItems;
	}

	public void setBatchItems(String batchItems) {
		this.batchItems = batchItems;
	}

}
