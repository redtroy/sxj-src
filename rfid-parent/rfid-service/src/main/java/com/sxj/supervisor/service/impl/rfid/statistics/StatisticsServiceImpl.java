package com.sxj.supervisor.service.impl.rfid.statistics;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.model.rfid.statistics.StatisticsModel;
import com.sxj.supervisor.service.rfid.statistics.IStatisticsService;
import com.sxj.util.exception.ServiceException;

@Service
@Transactional
public class StatisticsServiceImpl implements IStatisticsService {

	//private I
	
	@Override
	public List<StatisticsModel> statistics(String startDate, String endDate)
			throws ServiceException {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
