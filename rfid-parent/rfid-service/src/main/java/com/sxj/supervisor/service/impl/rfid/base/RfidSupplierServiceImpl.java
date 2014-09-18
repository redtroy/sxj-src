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
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
public class RfidSupplierServiceImpl implements IRfidSupplierService {

	@Autowired
	private IRfidSupplierDao supplierDao;

	@Override
	public List<RfidSupplierEntity> querySupplier(RfidSupplierQuery query)
			throws ServiceException {
		try {
			QueryCondition<RfidSupplierEntity> condition = new QueryCondition<RfidSupplierEntity>();
			if (query != null) {
				condition.addCondition("supplierNo", query.getSupplierNo());// 供应商ID
				condition.addCondition("name", query.getName());// 供应商名称
				condition.addCondition("contactTel", query.getContactTel());// 联系电话
				condition.addCondition("telNum", query.getTelNum());// 固定电话
				condition.addCondition("delstate", query.getDelstate());
				condition.setPage(query);
			}
			List<RfidSupplierEntity> list = supplierDao.queryList(condition);
			query.setPage(condition);
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RfidSupplierEntity getSupplier(String id) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			RfidSupplierEntity supplier = supplierDao.getRfidSupplier(id);
			return supplier;
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

	@Override
	public void add(RfidSupplierEntity Supplier) throws ServiceException {
		try {
			supplierDao.addRfidSupplier(Supplier);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delSupplier(String id) throws ServiceException {
		try {
			RfidSupplierEntity supplier = new RfidSupplierEntity();
			supplier.setDelstate(true);
			supplier.setId(id);
			supplierDao.updateRfidSupplier(supplier);
		} catch (Exception e) {
			SxjLogger.error("逻辑删除供应商ID错误", e, this.getClass());
			throw new ServiceException("逻辑删除供应商ID错误");
		}
	}
}
