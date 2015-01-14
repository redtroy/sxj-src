package com.sxj.supervisor.model.open;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class Contract  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5883732008777018231L;
	
	
	private String contractNo;
	
	private String rfid;


	public String getContractNo() {
		return contractNo;
	}


	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}


	public String getRfid() {
		return rfid;
	}


	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
}
