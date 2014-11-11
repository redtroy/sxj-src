package com.sxj.supervisor.service.impl.rfid.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.base.IRfidSupplierDao;
import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.model.rfid.base.RfidSupplierQuery;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class RfidSupplierServiceImpl implements IRfidSupplierService {

	@Autowired
	private IRfidSupplierDao supplierDao;

	@Override
	@Transactional(readOnly = true)
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
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("查询供应商错误");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public RfidSupplierEntity getSupplierByNo(String no)
			throws ServiceException {
		try {
			if (StringUtils.isEmpty(no)) {
				return null;
			}
			RfidSupplierQuery query = new RfidSupplierQuery();
			query.setSupplierNo(no);
			List<RfidSupplierEntity> list = querySupplier(query);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("查询供应商错误");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public RfidSupplierEntity getSupplier(String id) throws ServiceException {
		try {
			RfidSupplierEntity supplier = supplierDao.getRfidSupplier(id);
			return supplier;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("查询供应商错误");
		}
	}

	@Override
	@Transactional
	public void modifySupplier(RfidSupplierEntity Supplier)
			throws ServiceException {
		try {
			supplierDao.updateRfidSupplier(Supplier);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("修改供应商错误");
		}
	}

	@Override
	@Transactional
	public void add(RfidSupplierEntity supplier) throws ServiceException {
		try {
			supplierDao.addRfidSupplier(supplier);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("保存供应商错误");
		}

	}

	@Override
	@Transactional
	public void delSupplier(String id) throws ServiceException {
		try {
			RfidSupplierEntity supplier = new RfidSupplierEntity();
			supplier.setDelstate(true);
			supplier.setId(id);
			supplierDao.updateRfidSupplier(supplier);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("删除供应商错误");
		}
	}

}
