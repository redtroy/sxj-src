package com.sxj.supervisor.service.rfid.base;

import java.util.List;

import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.model.rfid.base.RfidSupplierQuery;
import com.sxj.util.exception.ServiceException;

public interface IRfidSupplierService {
	/**
	 * 根据条件高级查询
	 * 
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<RfidSupplierEntity> querySupplier(RfidSupplierQuery query)
			throws ServiceException;

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public RfidSupplierEntity getSupplier(String id) throws ServiceException;

	/**
	 * 根据No查询
	 * 
	 * @param no
	 * @return
	 * @throws ServiceException
	 */
	public RfidSupplierEntity getSupplierByNo(String no)
			throws ServiceException;

	/**
	 * 更新
	 * 
	 * @param Supplier
	 * @throws ServiceException
	 */
	public void modifySupplier(RfidSupplierEntity Supplier)
			throws ServiceException;

	/**
	 * 新增
	 * 
	 * @param Supplier
	 * @throws ServiceException
	 */
	public void add(RfidSupplierEntity Supplier) throws ServiceException;

	/**
	 * 删除
	 */
	public void delSupplier(String id) throws ServiceException;
}
