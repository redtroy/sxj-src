package com.sxj.supervisor.model.open;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class ContractNo  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5883732008777018231L;
	
	
	private String contractNo;


	public String getContractNo() {
		return contractNo;
	}


	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
}
