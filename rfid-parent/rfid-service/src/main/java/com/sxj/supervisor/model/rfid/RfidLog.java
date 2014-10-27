package com.sxj.supervisor.model.rfid;

import java.io.Serializable;

public class RfidLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8476956461963902070L;

	private String date;

	private String state;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
