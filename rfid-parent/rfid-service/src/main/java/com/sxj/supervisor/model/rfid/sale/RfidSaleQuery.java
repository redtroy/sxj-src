package com.sxj.supervisor.model.rfid.sale;

import java.io.Serializable;

import com.sxj.supervisor.enu.rfid.RfidTypeEnum;

public class RfidSaleQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8467614976681137068L;
	
	private String startDate;
	
	private String endDate;
	
	private RfidTypeEnum rfidType;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public RfidTypeEnum getRfidType() {
		return rfidType;
	}

	public void setRfidType(RfidTypeEnum rfidType) {
		this.rfidType = rfidType;
	}
	
	

}
