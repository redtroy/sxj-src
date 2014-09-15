package com.sxj.supervisor.service.impl.rfid.base;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.supervisor.dao.rfid.base.IRfidSupplierDao;
import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.model.rfid.base.RfidSupplierQuery;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;
import com.sxj.util.exception.ServiceException;

@Service
public class RfidSupplierServiceImpl implements IRfidSupplierService {

	@Autowired
	private IRfidSupplierDao supplierDao;

	@Override
	public List<RfidSupplierEntity> querySupplier(RfidSupplierQuery query)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RfidSupplierEntity getSupplier(String id) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			supplierDao.getRfidSupplier(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void modifySupplier(RfidSupplierEntity Supplier)
			throws ServiceException {
		// TODO Auto-generated method stub
		try {
			supplierDao.updateRfidSupplier(Supplier);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
