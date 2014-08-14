package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractImgDao;

@Entity(mapper = IContractImgDao.class)
@Table(name = "CONTRACT_IMG")
public class ContractImgEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2287549772311785884L;

	/**
	 * 标识
	**/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	/**
	 * 合同Id
	**/
	@Column(name = "CONTRACT_ID")
	private String contractId;
	
	/**
	 * 图片Path
	**/
	@Column(name = "IMG_PATH")
	private String imgPath;
	
	/**
	 * 合同明细ID
	**/
	@Column(name = "HIS_ITEM_ID")
	private String hisItemId;
	
	/**
	 * 合同批次ID
	**/
	@Column(name = "HIS_BATCH_ID")
	private String hisBatchId;

	public String getHisItemId() {
		return hisItemId;
	}

	public void setHisItemId(String hisItemId) {
		this.hisItemId = hisItemId;
	}

	public String getHisBatchId() {
		return hisBatchId;
	}

	public void setHisBatchId(String hisBatchId) {
		this.hisBatchId = hisBatchId;
	}

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

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
