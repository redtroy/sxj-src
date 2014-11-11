package com.sxj.supervisor.service.impl.rfid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.IRfidKeyDao;
import com.sxj.supervisor.entity.rfid.RfidKeyEntity;
import com.sxj.supervisor.service.rfid.IRfidKeyService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class RfidKeyServiceImpl implements IRfidKeyService {

	@Autowired
	private IRfidKeyDao dao;

	@Override
	@Transactional(timeout = 30)
	public Long getKey() throws ServiceException {
		try {
			RfidKeyEntity entity = new RfidKeyEntity();
			entity.setName("rfidNo");
			dao.getKey(entity);
			return entity.getId();
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
