package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractModifyBatchDao;

/**
 * 批次条目变更实体
 * 
 * @author Administrator
 *
 */
@Entity(mapper = IContractModifyBatchDao.class)
@Table(name = "M_CONTRACT_MODIFY_BATCH")
public class ModifyBatchEntity extends Pagable implements Serializable {
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
	@Column(name = "MODIFY_ID")
	private String modifyId;

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
	/**
     * 标识状态
     */
    @Column(name = "UPDATE_STATE")
    private Integer updateState;
    
	public Integer getUpdateState() {
		return updateState;
	}

	public void setUpdateState(Integer updateState) {
		this.updateState = updateState;
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

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
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
