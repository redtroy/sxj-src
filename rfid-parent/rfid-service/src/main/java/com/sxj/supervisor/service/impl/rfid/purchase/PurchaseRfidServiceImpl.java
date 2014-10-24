package com.sxj.supervisor.service.impl.rfid.purchase;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.apply.IRfidApplicationDao;
import com.sxj.supervisor.dao.rfid.purchase.IRfidPurchaseDao;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.entity.rfid.sale.RfidPriceEntity;
import com.sxj.supervisor.entity.rfid.sale.RfidSaleStatisticalEntity;
import com.sxj.supervisor.enu.rfid.purchase.DeliveryStateEnum;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.supervisor.service.rfid.purchase.IPurchaseRfidService;
import com.sxj.supervisor.service.rfid.sale.IRfidPriceService;
import com.sxj.supervisor.service.rfid.sale.IRfidSaleStatisticalService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class PurchaseRfidServiceImpl implements IPurchaseRfidService {

	@Autowired
	private IRfidPurchaseDao rfidPurchaseDao;

	@Autowired
	private IRfidSaleStatisticalService saleStatisticalService;

	@Autowired
	private IRfidPriceService rfidPriceService;

	@Autowired
	IRfidApplicationDao appDao;

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
			List<RfidPurchaseEntity> rfidList = rfidPurchaseDao
					.queryList(condition);
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
	@Transactional
	public void addPurchase(RfidPurchaseEntity purchase, String applyId,
			String hasNumber) throws ServiceException {
		try {
			RfidApplicationEntity app = appDao.getRfidApplication(applyId);
			app.setHasNumber(Long.valueOf(hasNumber) + purchase.getCount());
			rfidPurchaseDao.addRfidPurchase(purchase);
			appDao.updateRfidApplication(app);
		} catch (Exception e) {
			throw new ServiceException("新增采购单错误", e);
		}

	}

	@Override
	public RfidPurchaseEntity getRfidPurchase(String id)
			throws ServiceException {
		try {
			RfidPurchaseEntity purchase = rfidPurchaseDao.getRfidPurchase(id);
			return purchase;
		} catch (Exception e) {
			throw new ServiceException("获取采购单错误", e);
		}

	}

	/**
	 * 确认发货
	 */
	@Override
	@Transactional
	public void confirmDelivery(String id) throws ServiceException {
		try {
			RfidPurchaseEntity purchase = rfidPurchaseDao.getRfidPurchase(id);
			purchase.setReceiptState(DeliveryStateEnum.receiving);
			rfidPurchaseDao.updateRfidPurchase(purchase);
			RfidSaleStatisticalEntity entity = new RfidSaleStatisticalEntity();
			entity.setApplyNo(purchase.getApplyNo());
			entity.setPurchaseNo(purchase.getPurchaseNo());
			entity.setSaleDate(new Date());
			entity.setCount(purchase.getCount());
			entity.setRfidType(purchase.getRfidType());
			List<RfidPriceEntity> list = rfidPriceService.queryPrice();
			if (list != null && list.size() > 0) {
				RfidPriceEntity price = list.get(0);
				if (purchase.getRfidType().getId() == 0) {
					entity.setPrice(price.getWindowPrice());
				} else {
					entity.setPrice(price.getLogisticsPrice());
				}

			} else {
				throw new ServiceException();
			}
			saleStatisticalService.add(entity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
