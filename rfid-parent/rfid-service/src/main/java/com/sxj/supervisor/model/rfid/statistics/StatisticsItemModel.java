package com.sxj.supervisor.model.rfid.statistics;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StatisticsItemModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4237596274156476050L;

	private List<Date> dateList;

	private List<Long> countList;

	public List<Date> getDateList() {
		return dateList;
	}

	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	public List<Long> getCountList() {
		return countList;
	}

	public void setCountList(List<Long> countList) {
		this.countList = countList;
	}

}
