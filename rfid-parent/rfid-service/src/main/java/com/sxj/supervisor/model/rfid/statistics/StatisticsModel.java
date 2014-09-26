package com.sxj.supervisor.model.rfid.statistics;

import java.io.Serializable;

public class StatisticsModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3392948032546921011L;

	private StatisticsItemModel applyList;

	private StatisticsItemModel purchaseList;

	private StatisticsItemModel windowSaleList;

	private StatisticsItemModel batchSaleList;

	private StatisticsItemModel windowSaleAmountList;

	private StatisticsItemModel batchSaleAmountList;

	private StatisticsItemModel windowPurchaseAmountList;

	private StatisticsItemModel batchPurchaseAmountList;

	private StatisticsItemModel expectProfitList;

	private StatisticsItemModel realityProfitList;

	public StatisticsItemModel getApplyList() {
		return applyList;
	}

	public void setApplyList(StatisticsItemModel applyList) {
		this.applyList = applyList;
	}

	public StatisticsItemModel getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(StatisticsItemModel purchaseList) {
		this.purchaseList = purchaseList;
	}

	public StatisticsItemModel getWindowSaleList() {
		return windowSaleList;
	}

	public void setWindowSaleList(StatisticsItemModel windowSaleList) {
		this.windowSaleList = windowSaleList;
	}

	public StatisticsItemModel getBatchSaleList() {
		return batchSaleList;
	}

	public void setBatchSaleList(StatisticsItemModel batchSaleList) {
		this.batchSaleList = batchSaleList;
	}

	public StatisticsItemModel getWindowSaleAmountList() {
		return windowSaleAmountList;
	}

	public void setWindowSaleAmountList(StatisticsItemModel windowSaleAmountList) {
		this.windowSaleAmountList = windowSaleAmountList;
	}

	public StatisticsItemModel getBatchSaleAmountList() {
		return batchSaleAmountList;
	}

	public void setBatchSaleAmountList(StatisticsItemModel batchSaleAmountList) {
		this.batchSaleAmountList = batchSaleAmountList;
	}

	public StatisticsItemModel getWindowPurchaseAmountList() {
		return windowPurchaseAmountList;
	}

	public void setWindowPurchaseAmountList(
			StatisticsItemModel windowPurchaseAmountList) {
		this.windowPurchaseAmountList = windowPurchaseAmountList;
	}

	public StatisticsItemModel getBatchPurchaseAmountList() {
		return batchPurchaseAmountList;
	}

	public void setBatchPurchaseAmountList(
			StatisticsItemModel batchPurchaseAmountList) {
		this.batchPurchaseAmountList = batchPurchaseAmountList;
	}

	public StatisticsItemModel getExpectProfitList() {
		return expectProfitList;
	}

	public void setExpectProfitList(StatisticsItemModel expectProfitList) {
		this.expectProfitList = expectProfitList;
	}

	public StatisticsItemModel getRealityProfitList() {
		return realityProfitList;
	}

	public void setRealityProfitList(StatisticsItemModel realityProfitList) {
		this.realityProfitList = realityProfitList;
	}

}
