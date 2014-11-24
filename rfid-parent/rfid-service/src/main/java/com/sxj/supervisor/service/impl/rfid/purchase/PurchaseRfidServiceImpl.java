package com.sxj.supervisor.service.impl.rfid.purchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.purchase.IRfidPurchaseDao;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.entity.rfid.sale.RfidPriceEntity;
import com.sxj.supervisor.entity.rfid.sale.RfidSaleStatisticalEntity;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.DeliveryStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.ImportStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.PayStateEnum;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.rfid.IRfidKeyService;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.service.rfid.purchase.IPurchaseRfidService;
import com.sxj.supervisor.service.rfid.sale.IRfidPriceService;
import com.sxj.supervisor.service.rfid.sale.IRfidSaleStatisticalService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.CustomDecimal;
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
	private IRfidApplicationService applyService;

	@Autowired
	private IWindowRfidService winRfidService;

	@Autowired
	private ILogisticsRfidService logisticsRfidService;

	@Autowired
	private IRfidKeyService keyService;

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
	@Transactional
	public void updatePurchase(RfidPurchaseEntity purchase)
			throws ServiceException {
		try {
			if (purchase == null) {
				throw new ServiceException("采购单不存在");
			}
			RfidPurchaseEntity old = getRfidPurchase(purchase.getId());
			if (old == null) {
				throw new ServiceException("采购单不存在");
			}
			if (old.getPayState().equals(PayStateEnum.paid)) {
				throw new ServiceException("采购单已付款，不能修改");
			}
			if (!old.getReceiptState().equals(DeliveryStateEnum.unfilled)) {
				throw new ServiceException("采购单已发货，不能修改");
			}
			if (old.getImportState().equals(ImportStateEnum.imported)) {
				throw new ServiceException("采购单已导入，不能修改");
			}
			RfidApplicationEntity app = applyService.getApplication(old
					.getApplyNo());
			if (app == null) {
				throw new ServiceException("申请单不存在");
			}
			app.setHasNumber((app.getHasNumber() - old.getCount())
					+ purchase.getCount());
			rfidPurchaseDao.updateRfidPurchase(purchase);
			applyService.updateApp(app);
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("更新采购单错误", e);
		}
	}

	@Override
	@Transactional
	public void updatePayState(String id, PayStateEnum state)
			throws ServiceException {
		try {
			RfidPurchaseEntity purchase = new RfidPurchaseEntity();
			purchase.setId(id);
			purchase.setPayState(state);
			rfidPurchaseDao.updateRfidPurchase(purchase);
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("更新采购单状态错误", e);
		}

	}

	@Override
	@Transactional
	public void addPurchase(RfidPurchaseEntity purchase, String applyId,
			String hasNumber) throws ServiceException {
		try {
			RfidApplicationEntity app = applyService
					.getApplicationInfo(applyId);
			app.setHasNumber(Long.valueOf(hasNumber) + purchase.getCount());
			purchase.setApplyNo(app.getApplyNo());
			purchase.setDateNo("CG" + DateTimeUtils.getTime("yyMM"));
			rfidPurchaseDao.addRfidPurchase(purchase);
			applyService.updateApp(app);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("新增采购单错误", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
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
	public void confirmDelivery(String id) throws ServiceException {
		try {
			RfidPurchaseEntity purchase = rfidPurchaseDao.getRfidPurchase(id);
			purchase.setReceiptState(DeliveryStateEnum.shipped);
			rfidPurchaseDao.updateRfidPurchase(purchase);
			// 修改RFID状态
			if (purchase.getRfidType().equals(RfidTypeEnum.door)) {
				WindowRfidQuery query = new WindowRfidQuery();
				query.setPurchaseNo(purchase.getPurchaseNo());
				List<WindowRfidEntity> listRfid = winRfidService
						.queryWindowRfid(query);
				if (listRfid == null) {
					throw new ServiceException("未导入RFID标签，不能发货！");
				}
				if (listRfid.size() < purchase.getCount()) {
					throw new ServiceException("未完全导入RFID标签，不能发货！");
				}
				for (WindowRfidEntity windowRfid : listRfid) {
					if (windowRfid == null) {
						continue;
					}
					windowRfid.setProgressState(LabelProgressEnum.shipped);
				}
				winRfidService.batchUpdateWindowRfid(listRfid
						.toArray(new WindowRfidEntity[listRfid.size()]));
			} else {
				LogisticsRfidQuery query = new LogisticsRfidQuery();
				query.setPurchaseNo(purchase.getPurchaseNo());
				List<LogisticsRfidEntity> listRfid = logisticsRfidService
						.queryLogistics(query);
				if (listRfid == null) {
					throw new ServiceException("未导入RFID标签，不能发货！");
				}
				if (listRfid.size() < purchase.getCount()) {
					throw new ServiceException("未完全导入RFID标签，不能发货！");
				}
				for (LogisticsRfidEntity rfidEntity : listRfid) {
					if (rfidEntity == null) {
						continue;
					}
					rfidEntity.setProgressState(LabelStateEnum.shipped);
				}
				logisticsRfidService.batchUpdateLogistics(listRfid
						.toArray(new LogisticsRfidEntity[listRfid.size()]));
			}
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("确认发货失败", e);
		}

	}

	/**
	 * 确认收货
	 */
	@Override
	@Transactional
	public void confirmReceipt(String id) throws ServiceException {
		try {
			RfidPurchaseEntity purchase = rfidPurchaseDao.getRfidPurchase(id);
			purchase.setReceiptState(DeliveryStateEnum.receiving);
			rfidPurchaseDao.updateRfidPurchase(purchase);
			// 修改RFID状态
			if (purchase.getRfidType().equals(RfidTypeEnum.door)) {
				WindowRfidQuery query = new WindowRfidQuery();
				query.setPurchaseNo(purchase.getPurchaseNo());
				List<WindowRfidEntity> listRfid = winRfidService
						.queryWindowRfid(query);
				if (listRfid == null) {
					throw new ServiceException("未导入RFID标签，不能收货！");
				}
				if (listRfid.size() < purchase.getCount()) {
					throw new ServiceException("未完全导入RFID标签，不能收货！");
				}
				for (WindowRfidEntity windowRfid : listRfid) {
					if (windowRfid == null) {
						continue;
					}
					windowRfid.setProgressState(LabelProgressEnum.hasReceipt);
				}
				winRfidService.batchUpdateWindowRfid(listRfid
						.toArray(new WindowRfidEntity[listRfid.size()]));
			} else {
				LogisticsRfidQuery query = new LogisticsRfidQuery();
				query.setPurchaseNo(purchase.getPurchaseNo());
				List<LogisticsRfidEntity> listRfid = logisticsRfidService
						.queryLogistics(query);
				if (listRfid == null) {
					throw new ServiceException("未导入RFID标签，不能收货！");
				}
				if (listRfid.size() < purchase.getCount()) {
					throw new ServiceException("未完全导入RFID标签，不能收货！");
				}
				for (LogisticsRfidEntity rfidEntity : listRfid) {
					if (rfidEntity == null) {
						continue;
					}
					rfidEntity.setProgressState(LabelStateEnum.hasReceipt);
				}
				logisticsRfidService.batchUpdateLogistics(listRfid
						.toArray(new LogisticsRfidEntity[listRfid.size()]));
			}

			// 修改申请单状态
			String appNo = purchase.getApplyNo();
			RfidApplicationEntity apply = applyService.getApplication(appNo);
			if (apply != null) {
				PurchaseRfidQuery query = new PurchaseRfidQuery();
				query.setApplyNo(appNo);
				query.setReceiptState(DeliveryStateEnum.receiving.getId());
				Long hasReceCount = 0l;
				List<RfidPurchaseEntity> list = queryPurchase(query);
				if (list != null) {
					for (RfidPurchaseEntity rePurchase : list) {
						if (rePurchase == null) {
							continue;
						}
						hasReceCount = hasReceCount + rePurchase.getCount();
					}
				}
				if (apply.getCount().intValue() == hasReceCount.intValue()) {
					apply.setReceiptState(ReceiptStateEnum.Goods_receipt);
					applyService.updateApp(apply);
				}
			}
			// 插入销售记录
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
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("确认收货失败", e);
		}
	}

	@Override
	@Transactional(timeout = 30)
	public void importRfid(String purchaseId) throws ServiceException {
		try {
			long statrTime = System.currentTimeMillis();
			RfidPurchaseEntity purchase = getRfidPurchase(purchaseId);
			RfidApplicationEntity apply = applyService.getApplication(purchase
					.getApplyNo());
			RfidTypeEnum rfidType = purchase.getRfidType();
			Long count = purchase.getCount();
			Long lastkey = keyService.getKey(count.intValue());
			if (RfidTypeEnum.door.equals(rfidType)) {
				List<WindowRfidEntity> winRfids = new ArrayList<>();
				for (Long i = lastkey; i > lastkey - count; i--) {
					WindowRfidEntity rfid = new WindowRfidEntity();
					rfid.setApplyNo(purchase.getApplyNo());
					rfid.setPurchaseNo(purchase.getPurchaseNo());
					rfid.setContractNo(purchase.getContractNo());
					rfid.setImportDate(new Date());
					rfid.setMemberNo(apply.getMemberNo());
					rfid.setMemberName(apply.getMemberName());
					rfid.setRfidState(RfidStateEnum.unused);
					if (purchase.getReceiptState().equals(
							DeliveryStateEnum.unfilled)) {
						rfid.setProgressState(LabelProgressEnum.unfilled);
					} else if (purchase.getReceiptState().equals(
							DeliveryStateEnum.shipped)) {
						rfid.setProgressState(LabelProgressEnum.shipped);
					} else if (purchase.getReceiptState().equals(
							DeliveryStateEnum.receiving)) {
						rfid.setProgressState(LabelProgressEnum.hasReceipt);
					}

					rfid.setGenerateKey(i);
					String rfidNo = CustomDecimal.getDecimalString(4,
							new BigDecimal(i));
					rfid.setRfidNo(rfidNo);
					winRfids.add(rfid);
				}
				// 执行线程
				ExecutorService pool = Executors
						.newFixedThreadPool(threadCount);
				List<Future<Integer>> futures = new ArrayList<>();
				List<Integer[]> splits = NumberUtils.split(count.intValue(),
						executeCount);
				for (Integer[] split : splits) {
					List<WindowRfidEntity> subList = winRfids.subList(split[0],
							split[1]);
					WindowRfidThread thread1 = new WindowRfidThread(subList,
							winRfidService);
					Future<Integer> submit = pool.submit(thread1);
					futures.add(submit);
				}
				Integer threadCount = 0;
				for (Future<Integer> future : futures) {
					Integer submitCount = future.get();
					threadCount = threadCount + submitCount;
				}
				if (threadCount.intValue() != count.intValue())
					throw new ServiceException("导入RFID失败！");

			} else {
				List<LogisticsRfidEntity> rfids = new ArrayList<>();
				for (Long i = lastkey; i > lastkey - count; i--) {
					LogisticsRfidEntity rfid = new LogisticsRfidEntity();
					rfid.setApplyNo(purchase.getApplyNo());
					rfid.setPurchaseNo(purchase.getPurchaseNo());
					// rfid.setContractNo(purchase.getContractNo());
					rfid.setImportDate(new Date());
					rfid.setRfidState(RfidStateEnum.unused);
					rfid.setMemberNo(apply.getMemberNo());
					rfid.setMemberName(apply.getMemberName());
					if (purchase.getReceiptState().equals(
							DeliveryStateEnum.unfilled)) {
						rfid.setProgressState(LabelStateEnum.unfilled);
					} else if (purchase.getReceiptState().equals(
							DeliveryStateEnum.shipped)) {
						rfid.setProgressState(LabelStateEnum.shipped);
					} else if (purchase.getReceiptState().equals(
							DeliveryStateEnum.receiving)) {
						rfid.setProgressState(LabelStateEnum.hasReceipt);
					}
					rfid.setType(rfidType);
					rfid.setGenerateKey(i);
					String rfidNo = CustomDecimal.getDecimalString(4,
							new BigDecimal(i));
					rfid.setRfidNo(rfidNo);
					rfids.add(rfid);
				}

				// 执行线程
				ExecutorService pool = Executors
						.newFixedThreadPool(threadCount);
				List<Future<Integer>> futures = new ArrayList<>();
				List<Integer[]> splits = NumberUtils.split(count.intValue(),
						executeCount);
				for (Integer[] split : splits) {
					List<LogisticsRfidEntity> subList = rfids.subList(split[0],
							split[1]);
					LogisticsRfidThread thread1 = new LogisticsRfidThread(
							subList, logisticsRfidService);
					Future<Integer> submit = pool.submit(thread1);
					futures.add(submit);

				}
				Integer threadCount = 0;
				for (Future<Integer> future : futures) {
					Integer submitCount = future.get();
					threadCount = threadCount + submitCount;
				}
				if (threadCount.intValue() != count.intValue())
					throw new ServiceException("导入RFID失败！");
			}
			purchase.setImportState(ImportStateEnum.imported);
			rfidPurchaseDao.updateRfidPurchase(purchase);
			// updatePurchase(purchase);
			long endTime = System.currentTimeMillis();
			System.out.println("--------------------------------"
					+ (endTime - statrTime));
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("导入RFID错误", e);
		}

	}

	@Override
	@Transactional
	public void deletePurchase(String id) throws ServiceException {
		try {
			RfidPurchaseEntity purchase = rfidPurchaseDao.getRfidPurchase(id);
			if (purchase == null) {
				throw new ServiceException("采购单不存在");
			}
			if (purchase.isDelstate()) {
				throw new ServiceException("采购单已经被删除");
			}
			if (purchase.getImportState().equals(ImportStateEnum.imported)) {
				throw new ServiceException("采购单已经导入RFID，不能被删除");
			}
			if (purchase.getPayState().equals(PayStateEnum.paid)) {
				throw new ServiceException("采购单已经付款，不能被删除");
			}
			if (purchase.getReceiptState().equals(DeliveryStateEnum.shipped)) {
				throw new ServiceException("采购单已发货，不能被删除");
			}
			if (purchase.getReceiptState().equals(DeliveryStateEnum.receiving)) {
				throw new ServiceException("采购单已收货，不能被删除");
			}
			RfidApplicationEntity apply = applyService.getApplication(purchase
					.getApplyNo());
			if (apply != null) {
				apply.setHasNumber(apply.getHasNumber() - purchase.getCount());
				applyService.updateApp(apply);
			}
			purchase.setDelstate(true);
			rfidPurchaseDao.updateRfidPurchase(purchase);
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("删除采购单错误！");
		}

	}

}
