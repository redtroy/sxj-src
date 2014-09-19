package com.sxj.supervisor.service.rfid.purchase;

import java.util.List;

import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.util.exception.ServiceException;

public interface IPurchaseRfidService {

	/**
	 * 根据条件高级查询
	 * 
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<RfidPurchaseEntity> queryPurchase(PurchaseRfidQuery query)
			throws ServiceException;
	/**
	 * 更新
	 * @param id
	 * @throws ServiceException
	 */
	public void updatePurchase(RfidPurchaseEntity purchase) throws ServiceException;
	
	
	public List<LogModel> getRfidStateLog(String id) throws ServiceException;
}
