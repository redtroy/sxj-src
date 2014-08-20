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
 * 变更扫描件
 * @author Ann
 *
 */
@Entity(mapper = IContractBatchDao.class)
@Table(name = "M_CONTRACT_IMG_HIS")
public class ContractImgHisEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7818294410415140004L;

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
	 * 变更备案号
	**/
	@Column(name = "RECORD_NO")
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
