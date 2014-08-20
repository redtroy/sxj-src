package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 合同补损条目信息
 * 
 * @author Ann
 *
 */
public class ReplenishContractEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4330078071545852301L;

	/**
	 * 主键
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	/**
	 * 合同ID
	 */
	@Column(name = "CONTRACT_ID")
	private String contractId;

	/**
	 * 备案号
	 */
	@Column(name = "RECORD_NO")
	private String recordNo;

	/**
	 * 补损批次RFID
	 */
	@Column(name = "BATCH_RFID_NO")
	private String batchRfidNo;
	/**
	 * RFID订单号
	 */
	@Column(name = "RFID_ORDER_NO")
	private String rfidOrderNo;

	/**
	 * 扫描件路径
	 */
	@Column(name = "IMG_PATH")
	private String imgPath;

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

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getBatchRfidNo() {
		return batchRfidNo;
	}

	public void setBatchRfidNo(String batchRfidNo) {
		this.batchRfidNo = batchRfidNo;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getRfidOrderNo() {
		return rfidOrderNo;
	}

	public void setRfidOrderNo(String rfidOrderNo) {
		this.rfidOrderNo = rfidOrderNo;
	}

}
