package com.sxj.supervisor.service.impl.rfid.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.supervisor.model.rfid.statistics.StatisticsItemModel;
import com.sxj.supervisor.model.rfid.statistics.StatisticsModel;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.service.rfid.purchase.IPurchaseRfidService;
import com.sxj.supervisor.service.rfid.statistics.IStatisticsService;
import com.sxj.util.exception.ServiceException;

@Service
@Transactional
public class StatisticsServiceImpl implements IStatisticsService {

	@Autowired
	private IRfidApplicationService appService;

	@Autowired
	private IPurchaseRfidService purchaseService;

	@Override
	@Transactional
	public List<StatisticsModel> statistics(String startDate, String endDate)
			throws ServiceException {
		try {
			StatisticsModel model = new StatisticsModel();
			
			RfidApplicationQuery query = new RfidApplicationQuery();
			query.setStarApplyDate(startDate);
			query.setEndApplyDate(endDate);
			List<RfidApplicationEntity> appList = appService.query(query);
			Long appCount = 0l;
			StatisticsItemModel applyItem = new StatisticsItemModel();
			List<Date> applyDateList=new ArrayList<Date>();
			List<Long> applyCountList=new ArrayList<Long>();
			for (Iterator<RfidApplicationEntity> iterator = appList.iterator(); iterator
					.hasNext();) {
				RfidApplicationEntity rfidApplicationEntity = iterator.next();
				appCount = appCount + rfidApplicationEntity.getCount();
				applyDateList.add(rfidApplicationEntity.getApplyDate());
				applyCountList.add(rfidApplicationEntity.getCount());
			}
			applyItem.setCountList(applyCountList);
			applyItem.setDateList(applyDateList);
			model.setApplyList(applyItem);
			model.setApplySum(appCount);

			PurchaseRfidQuery purchaseQuery = new PurchaseRfidQuery();
			List<RfidPurchaseEntity> purchaseList = purchaseService
					.queryPurchase(purchaseQuery);
			Long purchaseCount = 0l;
			StatisticsItemModel purchaseItem = new StatisticsItemModel();
			List<Date> purchaseDateList=new ArrayList<Date>();
			List<Long> purchaseCountList=new ArrayList<Long>();
			for (RfidPurchaseEntity rfidPurchaseEntity : purchaseList) {
				purchaseCount=purchaseCount+rfidPurchaseEntity.getCount();
				purchaseDateList.add(rfidPurchaseEntity.getPurchaseDate());
				purchaseCountList.add(rfidPurchaseEntity.getCount());
			}
			applyItem.setCountList(purchaseCountList);
			applyItem.setDateList(purchaseDateList);
			model.setApplyList(purchaseItem);
			model.setPurchaseSum(purchaseCount);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
