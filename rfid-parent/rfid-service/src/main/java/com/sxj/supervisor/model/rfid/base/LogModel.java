package com.sxj.supervisor.model.rfid.base;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;
/**
 * RFID 动态
 * @author Ann
 *
 */
public class LogModel extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7798252563282819437L;

	
	private String logTitle;
	
	private String logDate;

	public String getLogTitle() {
		return logTitle;
	}

	public void setLogTitle(String logTitle) {
		this.logTitle = logTitle;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	
}
