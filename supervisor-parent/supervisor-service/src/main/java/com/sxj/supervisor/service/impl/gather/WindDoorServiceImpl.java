package com.sxj.supervisor.service.impl.gather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.supervisor.dao.gather.WindDoorDao;
import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.service.gather.IWindDoorService;
import com.sxj.util.exception.ServiceException;

@Service
public class WindDoorServiceImpl implements IWindDoorService {

	@Autowired
	private WindDoorDao wdd;

	@Override
	public void addWindDoor(WindDoorEntity wd) throws ServiceException {
		try {
			wdd.addWindDoor(wd);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
