package com.sxj.finance.service.finance;

import java.util.List;

import com.sxj.finance.entity.FinanceEntity;
import com.sxj.finance.model.finance.FinanceModel;
import com.sxj.util.exception.ServiceException;

public interface IFinanceService {

	/**
	 * 查询 前台
	 * 
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<FinanceEntity> queryWebSite(FinanceModel query)
			throws ServiceException;

	/**
	 * 查询 前台
	 * 
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<FinanceEntity> queryManage(FinanceModel query)
			throws ServiceException;

	/**
	 * 融资申请
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public Boolean apply(FinanceEntity fe) throws ServiceException;

	/**
	 * 受理
	 */
	public Boolean accept(FinanceEntity fe) throws ServiceException;

	/**
	 * 支付
	 */
	public Boolean pay(FinanceEntity fe) throws ServiceException;

	/**
	 * 搁置
	 */
	public Boolean shelve(FinanceEntity fe) throws ServiceException;
}
