package com.sxj.supervisor.service.rfid.statistics;

import java.util.List;

import com.sxj.supervisor.model.rfid.statistics.StatisticsModel;
import com.sxj.util.exception.ServiceException;

public interface IStatisticsService {

	public List<StatisticsModel> statistics(String startDate, String endDate)
			throws ServiceException;

}
