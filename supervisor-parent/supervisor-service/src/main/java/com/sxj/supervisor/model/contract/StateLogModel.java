package com.sxj.supervisor.model.contract;

import java.io.Serializable;

public class StateLogModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8223133461217484753L;

	private java.util.Date modifyDate;
	
	private Integer state;
	
	private String stateTitle;
	
	public java.util.Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStateTitle() {
		return stateTitle;
	}
	public void setStateTitle(String stateTitle) {
		this.stateTitle = stateTitle;
	}
}
