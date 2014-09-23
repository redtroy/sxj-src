package com.sxj.supervisor.service.impl.rfid.purchase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.purchase.IRfidPurchaseDao;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.supervisor.service.rfid.purchase.IPurchaseRfidService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
@Service
@Transactional
public class PurchaseRfidServiceImpl implements IPurchaseRfidService {

	@Autowired
	private IRfidPurchaseDao rfidPurchaseDao;
	
	@Override
	public List<RfidPurchaseEntity> queryPurchase(PurchaseRfidQuery query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<RfidPurchaseEntity> condition = new QueryCondition<RfidPurchaseEntity>();
			condition.addCondition("purchaseNo", query.getPurchaseNo());
			condition.addCondition("applyNo", query.getApplyNo());
			condition.addCondition("contractNo", query.getContractNo());
			condition.addCondition("supplierName", query.getSupplierName());
			condition.addCondition("rfidType", query.getRfidType());
			condition.addCondition("payState", query.getPayState());
			condition.addCondition("receiptState", query.getReceiptState());
			condition.addCondition("importState", query.getImportState());
			condition.addCondition("startDate", query.getStartDate());
			condition.addCondition("endDate", query.getEndDate());
			condition.setPage(query);
			List<RfidPurchaseEntity> rfidList=rfidPurchaseDao.queryList(condition);
			query.setPage(condition);
			return rfidList;
		} catch (Exception e) {
			throw new ServiceException("查询采购单错误", e);
		}
	}

	@Override
	public void updatePurchase(RfidPurchaseEntity purchase)
			throws ServiceException {
		try {
			rfidPurchaseDao.updateRfidPurchase(purchase);
		} catch (Exception e) {
			throw new ServiceException("更新采购单错误", e);
		}

	}

	@Override
	public RfidPurchaseEntity getRfidPurchase(String id) throws ServiceException {
		try {
			RfidPurchaseEntity purchase=rfidPurchaseDao.getRfidPurchase(id);
			return purchase;
		} catch (Exception e) {
			throw new ServiceException("获取采购单错误", e);
		}
		
	}

}
