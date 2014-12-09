package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
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
	@Sn(step = 1, appendStubValue = false, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValueProperty = "noType")
	private Long batchNo;

	private String noType;
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

	/**
	 * 补损状态
	 */
	@Column(name = "REPLENISH_STATE")
	private Integer replenishState;

	 /**支付标识状态
     */
    @Column(name = "PAY_STATE")
    private Integer payState;
	
	
    /**出库标识状态
     */
    @Column(name = "WAREHOUSE_STATE")
    private Integer warehouseState;
    
    /**
	 *支付单号
	 */
	@Column(name = "PAY_NO")
	private String payNo;
    
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

	public Long getBatchNo() {

		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
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

	public String getNoType() {
		return noType;
	}

	public void setNoType(String noType) {
		this.noType = noType;
	}

	public Integer getReplenishState() {
		return replenishState;
	}

	public void setReplenishState(Integer replenishState) {
		this.replenishState = replenishState;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Integer getWarehouseState() {
		return warehouseState;
	}

	public void setWarehouseState(Integer warehouseState) {
		this.warehouseState = warehouseState;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
}
