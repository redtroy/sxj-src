package com.sxj.supervisor.model.rfid.base;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;
/**
 * RFID 动态
 * @author Ann
 *
 */
public class LogModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7798252563282819437L;

	
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
