package com.sxj.supervisor.model.open;

import java.io.Serializable;

public class WinTypeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8082087243548593895L;

	private String winType;
	
	private String rfidNo;
	
	private String contratcNo;

	public String getWinType() {
		return winType;
	}

	public void setWinType(String winType) {
		this.winType = winType;
	}

	public String getRfidNo() {
		return rfidNo;
	}

	public void setRfidNo(String rfidNo) {
		this.rfidNo = rfidNo;
	}

	public String getContratcNo() {
		return contratcNo;
	}

	public void setContratcNo(String contratcNo) {
		this.contratcNo = contratcNo;
	}
}
