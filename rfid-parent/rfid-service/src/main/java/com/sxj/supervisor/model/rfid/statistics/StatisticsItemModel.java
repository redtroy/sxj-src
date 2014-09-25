package com.sxj.supervisor.model.rfid.statistics;

import java.io.Serializable;
import java.util.Date;

public class StatisticsItemModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4237596274156476050L;

	private Date date;

	private Long count;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
