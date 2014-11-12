package com.sxj.supervisor.service.impl.rfid.statistics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.entity.rfid.sale.RfidPriceEntity;
import com.sxj.supervisor.entity.rfid.sale.RfidSaleStatisticalEntity;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.supervisor.model.rfid.sale.RfidSaleQuery;
import com.sxj.supervisor.model.rfid.statistics.StatisticsItemModel;
import com.sxj.supervisor.model.rfid.statistics.StatisticsModel;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.service.rfid.purchase.IPurchaseRfidService;
import com.sxj.supervisor.service.rfid.sale.IRfidPriceService;
import com.sxj.supervisor.service.rfid.sale.IRfidSaleStatisticalService;
import com.sxj.supervisor.service.rfid.statistics.IStatisticsService;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class StatisticsServiceImpl implements IStatisticsService {

	@Autowired
	private IRfidApplicationService appService;

	@Autowired
	private IPurchaseRfidService purchaseService;

	@Autowired
	private IRfidSaleStatisticalService saleService;

	@Autowired
	private IRfidPriceService priceService;

	@Override
	@Transactional
	public StatisticsModel statistics(String startDate, String endDate)
			throws ServiceException {
		try {
			StatisticsModel model = new StatisticsModel();
			StatisticsItemModel applyItem = statisticsApply(startDate, endDate);
			model.setApplyList(applyItem);

			StatisticsItemModel purchaseItem = statisticsPurchase(startDate,
					endDate);
			model.setPurchaseList(purchaseItem);

			// 门窗标签销售量
			StatisticsItemModel windowSaleItem = statisticsWindowSale(
					startDate, endDate);
			model.setWindowSaleList(windowSaleItem);

			// 批次标签销售量
			StatisticsItemModel batchSaleItem = statisticsBatchSale(startDate,
					endDate);
			model.setBatchSaleList(batchSaleItem);

			// 门窗标签销售额
			StatisticsItemModel windowSaleAmountItem = statisticsWindowAmountSale(
					startDate, endDate);
			model.setWindowSaleAmountList(windowSaleAmountItem);

			StatisticsItemModel batchSaleAmountItem = statisticsBatchAmountSale(
					startDate, endDate);
			model.setBatchSaleAmountList(batchSaleAmountItem);

			// 门窗采购额
			StatisticsItemModel windowPurchaseAmountItem = statisticsWindowPurchaseAmount(
					startDate, endDate);
			model.setWindowPurchaseAmountList(windowPurchaseAmountItem);
			// 批次采购额
			StatisticsItemModel batchPurchaseAmountItem = statisticsWindowBatchAmount(
					startDate, endDate);
			model.setBatchPurchaseAmountList(batchPurchaseAmountItem);

			// 预计利润
			StatisticsItemModel expectProfitList = statisticsApplyAmount(
					startDate, endDate);
			model.setExpectProfitList(expectProfitList);
			// 实际利润
			StatisticsItemModel realityProfitList = statisticsRealityProfit(
					startDate, endDate);
			model.setRealityProfitList(realityProfitList);

			return model;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
		}
		return null;
	}

	private StatisticsItemModel statisticsApply(String startDate, String endDate)
			throws ServiceException {
		// 申请数量
		RfidApplicationQuery query = new RfidApplicationQuery();
		query.setStarApplyDate(startDate);
		query.setEndApplyDate(endDate);
		List<RfidApplicationEntity> appList = appService.query(query);
		Long appCount = 0l;
		StatisticsItemModel applyItem = new StatisticsItemModel();
		List<String> applyDateList = new ArrayList<String>();
		List<Double> applyCountList = new ArrayList<Double>();
		for (Iterator<RfidApplicationEntity> iterator = appList.iterator(); iterator
				.hasNext();) {
			RfidApplicationEntity rfidApplicationEntity = iterator.next();
			appCount = appCount + rfidApplicationEntity.getCount();
			applyDateList.add(DateTimeUtils
					.formatPageDate(rfidApplicationEntity.getApplyDate()));
			applyCountList
					.add(new Double(rfidApplicationEntity.getCount() + ""));
		}
		applyItem.setCountList(applyCountList);
		applyItem.setDateList(applyDateList);
		applyItem.setCountSum(new Double(appCount + ""));
		return applyItem;
	}

	private StatisticsItemModel statisticsPurchase(String startDate,
			String endDate) throws ServiceException {
		// 采购数量
		PurchaseRfidQuery purchaseQuery = new PurchaseRfidQuery();
		purchaseQuery.setStartDate(startDate);
		purchaseQuery.setEndDate(endDate);
		List<RfidPurchaseEntity> purchaseList = purchaseService
				.queryPurchase(purchaseQuery);
		Long purchaseCount = 0l;
		StatisticsItemModel purchaseItem = new StatisticsItemModel();
		List<String> purchaseDateList = new ArrayList<String>();
		List<Double> purchaseCountList = new ArrayList<Double>();
		for (RfidPurchaseEntity rfidPurchaseEntity : purchaseList) {
			purchaseCount = purchaseCount + rfidPurchaseEntity.getCount();
			purchaseDateList.add(DateTimeUtils
					.formatPageDate(rfidPurchaseEntity.getPurchaseDate()));
			purchaseCountList
					.add(new Double(rfidPurchaseEntity.getCount() + ""));
		}
		purchaseItem.setCountList(purchaseCountList);
		purchaseItem.setDateList(purchaseDateList);
		purchaseItem.setCountSum(new Double(purchaseCount + ""));
		return purchaseItem;
	}

	private StatisticsItemModel statisticsWindowSale(String startDate,
			String endDate) throws ServiceException {
		RfidSaleQuery query = new RfidSaleQuery();
		query.setStartDate(startDate);
		query.setEndDate(endDate);
		query.setRfidType(RfidTypeEnum.door);
		List<RfidSaleStatisticalEntity> list = saleService.queryList(query);
		Long count = 0l;
		StatisticsItemModel item = new StatisticsItemModel();
		List<String> dateList = new ArrayList<String>();
		List<Double> countList = new ArrayList<Double>();
		for (RfidSaleStatisticalEntity entity : list) {
			if (entity == null) {
				continue;
			}
			count = count + entity.getCount();
			dateList.add(DateTimeUtils.formatPageDate(entity.getSaleDate()));
			countList.add(new Double(entity.getCount() + ""));
		}
		item.setCountList(countList);
		item.setDateList(dateList);
		item.setCountSum(new Double(count + ""));
		return item;

	}

	private StatisticsItemModel statisticsBatchSale(String startDate,
			String endDate) throws ServiceException {
		RfidSaleQuery query = new RfidSaleQuery();
		query.setStartDate(startDate);
		query.setEndDate(endDate);
		query.setRfidType(RfidTypeEnum.extrusions);
		List<RfidSaleStatisticalEntity> list1 = saleService.queryList(query);
		query.setRfidType(RfidTypeEnum.glass);
		List<RfidSaleStatisticalEntity> list2 = saleService.queryList(query);
		if (list1 == null) {
			list1 = new ArrayList<RfidSaleStatisticalEntity>();
		}
		list1.addAll(list2);
		Long count = 0l;
		StatisticsItemModel item = new StatisticsItemModel();
		List<String> dateList = new ArrayList<String>();
		List<Double> countList = new ArrayList<Double>();
		for (RfidSaleStatisticalEntity entity : list1) {
			if (entity == null) {
				continue;
			}
			count = count + entity.getCount();
			dateList.add(DateTimeUtils.formatPageDate(entity.getSaleDate()));
			countList.add(new Double(entity.getCount() + ""));
		}
		item.setCountList(countList);
		item.setDateList(dateList);
		item.setCountSum(new Double(count + ""));
		return item;
	}

	private StatisticsItemModel statisticsWindowAmountSale(String startDate,
			String endDate) throws ServiceException {
		RfidSaleQuery query = new RfidSaleQuery();
		query.setStartDate(startDate);
		query.setEndDate(endDate);
		query.setRfidType(RfidTypeEnum.door);
		List<RfidSaleStatisticalEntity> list = saleService.queryList(query);
		Long count = 0l;
		StatisticsItemModel item = new StatisticsItemModel();
		List<String> dateList = new ArrayList<String>();
		List<Double> countList = new ArrayList<Double>();
		for (RfidSaleStatisticalEntity entity : list) {
			if (entity == null) {
				continue;
			}
			// count = count + (entity.getCount() * entity.getPrice());
			dateList.add(DateTimeUtils.formatPageDate(entity.getSaleDate()));
			countList.add(new Double(NumberUtils.leftMove(
					(entity.getCount() * entity.getPrice()) + "", 2)));
		}
		item.setCountList(countList);
		item.setDateList(dateList);
		item.setCountSum(new Double(NumberUtils.leftMove(count + "", 2)));
		return item;

	}

	private StatisticsItemModel statisticsBatchAmountSale(String startDate,
			String endDate) throws ServiceException {
		RfidSaleQuery query = new RfidSaleQuery();
		query.setStartDate(startDate);
		query.setEndDate(endDate);
		query.setRfidType(RfidTypeEnum.extrusions);
		List<RfidSaleStatisticalEntity> list1 = saleService.queryList(query);
		query.setRfidType(RfidTypeEnum.glass);
		List<RfidSaleStatisticalEntity> list2 = saleService.queryList(query);
		if (list1 == null) {
			list1 = new ArrayList<RfidSaleStatisticalEntity>();
		}
		list1.addAll(list2);
		Long count = 0l;
		StatisticsItemModel item = new StatisticsItemModel();
		List<String> dateList = new ArrayList<String>();
		List<Double> countList = new ArrayList<Double>();
		for (RfidSaleStatisticalEntity entity : list1) {
			if (entity == null) {
				continue;
			}
			// count = count + (entity.getCount() * entity.getPrice());
			dateList.add(DateTimeUtils.formatPageDate(entity.getSaleDate()));
			countList.add(new Double(NumberUtils.leftMove(
					(entity.getCount() * entity.getPrice()) + "", 2)));
		}
		item.setCountList(countList);
		item.setDateList(dateList);
		item.setCountSum(new Double(NumberUtils.leftMove(count + "", 2)));
		return item;
	}

	private StatisticsItemModel statisticsWindowPurchaseAmount(
			String startDate, String endDate) throws ServiceException {
		PurchaseRfidQuery query = new PurchaseRfidQuery();
		query.setStartDate(startDate);
		query.setEndDate(endDate);
		// query.setRfidType(RfidTypeEnum.door);
		List<RfidPurchaseEntity> list = purchaseService.queryPurchase(query);
		Long count = 0l;
		StatisticsItemModel item = new StatisticsItemModel();
		List<String> dateList = new ArrayList<String>();
		List<Double> countList = new ArrayList<Double>();
		for (RfidPurchaseEntity entity : list) {
			if (entity == null) {
				continue;
			}
			// count = count + (entity.getCount() * entity.getPrice());
			dateList.add(DateTimeUtils.formatPageDate(entity.getPurchaseDate()));
			countList.add(new Double(NumberUtils.leftMove(
					(entity.getCount() * entity.getPrice()) + "", 2)));
		}
		item.setCountList(countList);
		item.setDateList(dateList);
		item.setCountSum(new Double(NumberUtils.leftMove(count + "", 2)));
		return item;
	}

	private StatisticsItemModel statisticsWindowBatchAmount(String startDate,
			String endDate) throws ServiceException {
		PurchaseRfidQuery query = new PurchaseRfidQuery();
		query.setStartDate(startDate);
		query.setEndDate(endDate);
		// query.setRfidType(RfidTypeEnum.glass);
		List<RfidPurchaseEntity> list1 = purchaseService.queryPurchase(query);
		// query.setRfidType(RfidTypeEnum.extrusions);
		List<RfidPurchaseEntity> list2 = purchaseService.queryPurchase(query);
		if (list1 == null) {
			list1 = new ArrayList<RfidPurchaseEntity>();
		}
		list1.addAll(list2);
		Long count = 0l;
		StatisticsItemModel item = new StatisticsItemModel();
		List<String> dateList = new ArrayList<String>();
		List<Double> countList = new ArrayList<Double>();
		for (RfidPurchaseEntity entity : list1) {
			if (entity == null) {
				continue;
			}
			// count = count + (entity.getCount() * entity.getPrice());
			dateList.add(DateTimeUtils.formatPageDate(entity.getPurchaseDate()));
			countList.add(new Double(NumberUtils.leftMove(
					(entity.getCount() * entity.getPrice()) + "", 2)));
		}
		item.setCountList(countList);
		item.setDateList(dateList);
		item.setCountSum(new Double(NumberUtils.leftMove(count + "", 2)));
		return item;
	}

	private StatisticsItemModel statisticsApplyAmount(String startDate,
			String endDate) throws ServiceException {
		// 申请数量
		RfidApplicationQuery query = new RfidApplicationQuery();
		query.setStarApplyDate(startDate);
		query.setEndApplyDate(endDate);
		List<RfidApplicationEntity> appList = appService.query(query);
		Long appCount = 0l;
		StatisticsItemModel item = new StatisticsItemModel();
		List<String> dateList = new ArrayList<String>();
		List<Double> countList = new ArrayList<Double>();
		List<RfidPriceEntity> priceList = priceService.queryPrice();
		RfidPriceEntity price = null;
		if (priceList != null) {
			price = priceList.get(0);
		}
		for (Iterator<RfidApplicationEntity> iterator = appList.iterator(); iterator
				.hasNext();) {
			RfidApplicationEntity entity = iterator.next();
			if (entity.getRfidType().getId() == RfidTypeEnum.door.getId()) {
				// appCount = appCount
				// + (entity.getCount() * price.getWindowPrice());
				countList.add(new Double(NumberUtils.leftMove(
						(entity.getCount() * price.getWindowPrice()) + "", 2)));
			} else {
				// appCount = appCount
				// + (entity.getCount() * price.getLogisticsPrice());
				countList.add(new Double(
						NumberUtils.leftMove(
								(entity.getCount() * price.getLogisticsPrice())
										+ "", 2)));
			}
			dateList.add(DateTimeUtils.formatPageDate(entity.getApplyDate()));

		}
		item.setCountList(countList);
		item.setDateList(dateList);
		item.setCountSum(new Double(NumberUtils.leftMove(appCount + "", 2)));
		return item;
	}

	private StatisticsItemModel statisticsRealityProfit(String startDate,
			String endDate) throws ServiceException {
		RfidSaleQuery query = new RfidSaleQuery();
		query.setStartDate(startDate);
		query.setEndDate(endDate);
		List<RfidSaleStatisticalEntity> list = saleService.queryList(query);
		Long count = 0l;
		StatisticsItemModel item = new StatisticsItemModel();
		List<String> dateList = new ArrayList<String>();
		List<Double> countList = new ArrayList<Double>();
		for (RfidSaleStatisticalEntity entity : list) {
			if (entity == null) {
				continue;
			}
			// count = count + (entity.getCount() * entity.getPrice());
			dateList.add(DateTimeUtils.formatPageDate(entity.getSaleDate()));
			countList.add(new Double(NumberUtils.leftMove(
					(entity.getCount() * entity.getPrice()) + "", 2)));
		}
		item.setCountList(countList);
		item.setDateList(dateList);
		item.setCountSum(new Double(NumberUtils.leftMove(count + "", 2)));
		return item;

	}
}
