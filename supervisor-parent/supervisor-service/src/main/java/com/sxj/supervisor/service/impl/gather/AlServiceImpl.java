package com.sxj.supervisor.service.impl.gather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.supervisor.dao.gather.AlDao;
import com.sxj.supervisor.entity.gather.AlEntity;
import com.sxj.supervisor.service.gather.IAlService;
import com.sxj.util.exception.ServiceException;

@Service
public class AlServiceImpl implements IAlService {

	@Autowired
	private AlDao ad;

	@Override
	public void addAl(AlEntity al) throws ServiceException {
		try {
			ad.addAl(al);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
