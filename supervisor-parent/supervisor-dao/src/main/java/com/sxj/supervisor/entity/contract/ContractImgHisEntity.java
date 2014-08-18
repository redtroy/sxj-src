package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class ContractImgHisEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7818294410415140004L;

	/**
	 * 标识
	**/
	private String id;
	
	/**
	 * 合同Id
	**/
	private String contractId;
	
	/**
	 * 图片Path
	**/
	private String imgPath;
	
	/**
	 * 变更备案号
	**/
	private String recordNo;

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

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
}
