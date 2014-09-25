package com.sxj.supervisor.model.rfid.statistics;

import java.io.Serializable;
import java.util.List;

public class StatisticsModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3392948032546921011L;

	private Long applySum;

	private StatisticsItemModel applyList;

	private Long purchaseSum;

	private StatisticsItemModel purchaseList;

	private Long windowSaleSum;

	private StatisticsItemModel windowSaleList;

	private Long batchSaleSum;

	private StatisticsItemModel batchSaleList;

	private Long windowSaleAmount;

	private StatisticsItemModel windowSaleAmountList;

	private Long batchSaleAmount;

	private StatisticsItemModel batchSaleAmountList;

	private Long windowPurchaseSum;

	private StatisticsItemModel windowPurchaseList;

	private Long batchPurchaseSum;

	private StatisticsItemModel batchPurchaseList;

	private Long expectProfit;

	private StatisticsItemModel expectProfitList;

	private Long realityProfit;

	private StatisticsItemModel realityProfitList;

	public Long getApplySum() {
		return applySum;
	}

	public void setApplySum(Long applySum) {
		this.applySum = applySum;
	}

	public StatisticsItemModel getApplyList() {
		return applyList;
	}

	public void setApplyList(StatisticsItemModel applyList) {
		this.applyList = applyList;
	}

	public Long getPurchaseSum() {
		return purchaseSum;
	}

	public void setPurchaseSum(Long purchaseSum) {
		this.purchaseSum = purchaseSum;
	}

	public StatisticsItemModel getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(StatisticsItemModel purchaseList) {
		this.purchaseList = purchaseList;
	}

	public Long getWindowSaleSum() {
		return windowSaleSum;
	}

	public void setWindowSaleSum(Long windowSaleSum) {
		this.windowSaleSum = windowSaleSum;
	}

	public StatisticsItemModel getWindowSaleList() {
		return windowSaleList;
	}

	public void setWindowSaleList(StatisticsItemModel windowSaleList) {
		this.windowSaleList = windowSaleList;
	}

	public Long getBatchSaleSum() {
		return batchSaleSum;
	}

	public void setBatchSaleSum(Long batchSaleSum) {
		this.batchSaleSum = batchSaleSum;
	}

	public StatisticsItemModel getBatchSaleList() {
		return batchSaleList;
	}

	public void setBatchSaleList(StatisticsItemModel batchSaleList) {
		this.batchSaleList = batchSaleList;
	}

	public Long getWindowSaleAmount() {
		return windowSaleAmount;
	}

	public void setWindowSaleAmount(Long windowSaleAmount) {
		this.windowSaleAmount = windowSaleAmount;
	}

	public StatisticsItemModel getWindowSaleAmountList() {
		return windowSaleAmountList;
	}

	public void setWindowSaleAmountList(StatisticsItemModel windowSaleAmountList) {
		this.windowSaleAmountList = windowSaleAmountList;
	}

	public Long getBatchSaleAmount() {
		return batchSaleAmount;
	}

	public void setBatchSaleAmount(Long batchSaleAmount) {
		this.batchSaleAmount = batchSaleAmount;
	}

	public StatisticsItemModel getBatchSaleAmountList() {
		return batchSaleAmountList;
	}

	public void setBatchSaleAmountList(StatisticsItemModel batchSaleAmountList) {
		this.batchSaleAmountList = batchSaleAmountList;
	}

	public Long getWindowPurchaseSum() {
		return windowPurchaseSum;
	}

	public void setWindowPurchaseSum(Long windowPurchaseSum) {
		this.windowPurchaseSum = windowPurchaseSum;
	}

	public StatisticsItemModel getWindowPurchaseList() {
		return windowPurchaseList;
	}

	public void setWindowPurchaseList(StatisticsItemModel windowPurchaseList) {
		this.windowPurchaseList = windowPurchaseList;
	}

	public Long getBatchPurchaseSum() {
		return batchPurchaseSum;
	}

	public void setBatchPurchaseSum(Long batchPurchaseSum) {
		this.batchPurchaseSum = batchPurchaseSum;
	}

	public StatisticsItemModel getBatchPurchaseList() {
		return batchPurchaseList;
	}

	public void setBatchPurchaseList(StatisticsItemModel batchPurchaseList) {
		this.batchPurchaseList = batchPurchaseList;
	}

	public Long getExpectProfit() {
		return expectProfit;
	}

	public void setExpectProfit(Long expectProfit) {
		this.expectProfit = expectProfit;
	}

	public StatisticsItemModel getExpectProfitList() {
		return expectProfitList;
	}

	public void setExpectProfitList(StatisticsItemModel expectProfitList) {
		this.expectProfitList = expectProfitList;
	}

	public Long getRealityProfit() {
		return realityProfit;
	}

	public void setRealityProfit(Long realityProfit) {
		this.realityProfit = realityProfit;
	}

	public StatisticsItemModel getRealityProfitList() {
		return realityProfitList;
	}

	public void setRealityProfitList(StatisticsItemModel realityProfitList) {
		this.realityProfitList = realityProfitList;
	}

}
