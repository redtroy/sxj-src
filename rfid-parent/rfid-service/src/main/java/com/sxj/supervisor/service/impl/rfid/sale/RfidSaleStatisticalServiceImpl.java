package com.sxj.supervisor.service.impl.rfid.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.sale.IRfidSaleStatisticalDao;
import com.sxj.supervisor.entity.rfid.sale.RfidSaleStatisticalEntity;
import com.sxj.supervisor.service.rfid.sale.IRfidSaleStatisticalService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class RfidSaleStatisticalServiceImpl implements
		IRfidSaleStatisticalService {

	@Autowired
	private IRfidSaleStatisticalDao dao;

	@Override
	public void add(RfidSaleStatisticalEntity entity) throws ServiceException {
		try {
			dao.insert(entity);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("新增销售记录错误", e);
		}

	}

	@Override
	public List<RfidSaleStatisticalEntity> queryList() throws ServiceException {
		try {
			return dao.queryList(null);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("查询销售记录", e);
		}
	}

}
