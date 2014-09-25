package com.sxj.supervisor.model.rfid.statistics;

import java.io.Serializable;
import java.util.List;

public class StatisticsModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3392948032546921011L;

	private Long applySum;

	private List<StatisticsItemModel> applyList;

	private Long purchaseSum;

	private List<StatisticsItemModel> purchaseList;

	private Long windowSaleSum;

	private List<StatisticsItemModel> windowSaleList;

	private Long batchSaleSum;

	private List<StatisticsItemModel> batchSaleList;

	private Long windowSaleAmount;

	private List<StatisticsItemModel> windowSaleAmountList;

	private Long batchSaleAmount;

	private List<StatisticsItemModel> batchSaleAmountList;

	private Long windowPurchaseSum;

	private List<StatisticsItemModel> windowPurchaseList;

	private Long batchPurchaseSum;

	private List<StatisticsItemModel> batchPurchaseList;

	private Long expectProfit;

	private List<StatisticsItemModel> expectProfitList;

	private Long realityProfit;

	private List<StatisticsItemModel> realityProfitList;

	public Long getApplySum() {
		return applySum;
	}

	public void setApplySum(Long applySum) {
		this.applySum = applySum;
	}

	public List<StatisticsItemModel> getApplyList() {
		return applyList;
	}

	public void setApplyList(List<StatisticsItemModel> applyList) {
		this.applyList = applyList;
	}

	public Long getPurchaseSum() {
		return purchaseSum;
	}

	public void setPurchaseSum(Long purchaseSum) {
		this.purchaseSum = purchaseSum;
	}

	public List<StatisticsItemModel> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(List<StatisticsItemModel> purchaseList) {
		this.purchaseList = purchaseList;
	}

	public Long getWindowSaleSum() {
		return windowSaleSum;
	}

	public void setWindowSaleSum(Long windowSaleSum) {
		this.windowSaleSum = windowSaleSum;
	}

	public List<StatisticsItemModel> getWindowSaleList() {
		return windowSaleList;
	}

	public void setWindowSaleList(List<StatisticsItemModel> windowSaleList) {
		this.windowSaleList = windowSaleList;
	}

	public Long getBatchSaleSum() {
		return batchSaleSum;
	}

	public void setBatchSaleSum(Long batchSaleSum) {
		this.batchSaleSum = batchSaleSum;
	}

	public List<StatisticsItemModel> getBatchSaleList() {
		return batchSaleList;
	}

	public void setBatchSaleList(List<StatisticsItemModel> batchSaleList) {
		this.batchSaleList = batchSaleList;
	}

	public Long getWindowSaleAmount() {
		return windowSaleAmount;
	}

	public void setWindowSaleAmount(Long windowSaleAmount) {
		this.windowSaleAmount = windowSaleAmount;
	}

	public List<StatisticsItemModel> getWindowSaleAmountList() {
		return windowSaleAmountList;
	}

	public void setWindowSaleAmountList(
			List<StatisticsItemModel> windowSaleAmountList) {
		this.windowSaleAmountList = windowSaleAmountList;
	}

	public Long getBatchSaleAmount() {
		return batchSaleAmount;
	}

	public void setBatchSaleAmount(Long batchSaleAmount) {
		this.batchSaleAmount = batchSaleAmount;
	}

	public List<StatisticsItemModel> getBatchSaleAmountList() {
		return batchSaleAmountList;
	}

	public void setBatchSaleAmountList(
			List<StatisticsItemModel> batchSaleAmountList) {
		this.batchSaleAmountList = batchSaleAmountList;
	}

	public Long getWindowPurchaseSum() {
		return windowPurchaseSum;
	}

	public void setWindowPurchaseSum(Long windowPurchaseSum) {
		this.windowPurchaseSum = windowPurchaseSum;
	}

	public List<StatisticsItemModel> getWindowPurchaseList() {
		return windowPurchaseList;
	}

	public void setWindowPurchaseList(
			List<StatisticsItemModel> windowPurchaseList) {
		this.windowPurchaseList = windowPurchaseList;
	}

	public Long getBatchPurchaseSum() {
		return batchPurchaseSum;
	}

	public void setBatchPurchaseSum(Long batchPurchaseSum) {
		this.batchPurchaseSum = batchPurchaseSum;
	}

	public List<StatisticsItemModel> getBatchPurchaseList() {
		return batchPurchaseList;
	}

	public void setBatchPurchaseList(List<StatisticsItemModel> batchPurchaseList) {
		this.batchPurchaseList = batchPurchaseList;
	}

	public Long getExpectProfit() {
		return expectProfit;
	}

	public void setExpectProfit(Long expectProfit) {
		this.expectProfit = expectProfit;
	}

	public List<StatisticsItemModel> getExpectProfitList() {
		return expectProfitList;
	}

	public void setExpectProfitList(List<StatisticsItemModel> expectProfitList) {
		this.expectProfitList = expectProfitList;
	}

	public Long getRealityProfit() {
		return realityProfit;
	}

	public void setRealityProfit(Long realityProfit) {
		this.realityProfit = realityProfit;
	}

	public List<StatisticsItemModel> getRealityProfitList() {
		return realityProfitList;
	}

	public void setRealityProfitList(List<StatisticsItemModel> realityProfitList) {
		this.realityProfitList = realityProfitList;
	}

}
