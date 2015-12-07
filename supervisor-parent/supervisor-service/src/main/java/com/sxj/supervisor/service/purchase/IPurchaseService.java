package com.sxj.supervisor.service.purchase;

import java.util.List;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.purchase.ApplyEntity;
import com.sxj.supervisor.entity.purchase.PurchaseEntity;
import com.sxj.supervisor.entity.purchase.ReleaseRecordEntity;
import com.sxj.util.exception.ServiceException;

public interface IPurchaseService {

	/**
	 * 同步会员
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	public void syncMember(MemberEntity entity) throws ServiceException;

	/**
	 * 添加采购申请单
	 * 
	 * @param apply
	 */
	public void addApply(ApplyEntity apply);

	/**
	 * 更新采购池数据
	 * 
	 * @param purchaseEntity
	 */
	public void updatePurchase(PurchaseEntity purchaseEntity);

	/**
	 * 获取采购池数据
	 */
	public PurchaseEntity getPurchase();

	/**
	 * 获取采购申请单
	 * 
	 * @param apply
	 * @return
	 */
	public List<ApplyEntity> queryApply(ApplyEntity apply);

	/**
	 * 添加采购池数据
	 * 
	 * @param releaseRecordEntity
	 */
	public void addReleaseRecordDao(ReleaseRecordEntity releaseRecordEntity);

	/**
	 * 获取采购池数据
	 * 
	 * @param releaseRecordEntity
	 * @return
	 */
	public List<ReleaseRecordEntity> queryReleaseRecords(
			ReleaseRecordEntity releaseRecordEntity);

	void updateApply(ApplyEntity apply);
}
