package com.sxj.supervisor.service.impl.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.statemachine.StateMachineImpl;
import com.sxj.supervisor.dao.contract.IAccountingDao;
import com.sxj.supervisor.dao.contract.IContractPayDao;
import com.sxj.supervisor.entity.message.TransMessageEntity;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.enu.contract.PayModeEnum;
import com.sxj.supervisor.enu.contract.PayStageEnum;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.contract.ContractPayModel;
import com.sxj.supervisor.model.statistics.AccountingModel;
import com.sxj.supervisor.service.contract.IContractPayService;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.message.ITransMessageService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
public class ContractPayServiceImpl implements IContractPayService {
	@Autowired
	private IContractPayDao payDao;

	@Autowired
	private IAccountingDao accountingDao;

	@Autowired
	private IContractService contractService;

	@Autowired
	private RedisTopics redisTopics;

	@Autowired
	private ITransMessageService tms;

	@Autowired
	@Qualifier("payStagefsm")
	private StateMachineImpl<PayStageEnum> payStagefsm;

	@Autowired
	@Qualifier("payModeFsm")
	private StateMachineImpl<PayModeEnum> payModeFsm;

	@Override
	@Transactional(readOnly = true)
	public List<PayRecordEntity> queryPayListA(ContractPayModel query)
			throws ServiceException {
		try {
			List<PayRecordEntity> payList = new ArrayList<PayRecordEntity>();
			if (query == null) {
				return payList;
			}
			QueryCondition<PayRecordEntity> condition = new QueryCondition<PayRecordEntity>();
			condition.addCondition("memberNo", query.getMemberNo());// 会员号
			condition.addCondition("memberType", query.getMemberType());// 会员号
			condition.addCondition("payNo", query.getPayNo());// 支付单号
			condition.addCondition("contractNo", query.getContractNo());// 合同号
			condition.addCondition("rfidNo", query.getRfidNo());// Rfid编号
			condition.addCondition("startPayDate", query.getStartPayDate());// 开始时间
			condition.addCondition("endPayDate", query.getEndPayDate());// 结束时间
			condition.addCondition("state", query.getState());//
			condition.addCondition("memberName_A", query.getMemberNameA());//
			condition.addCondition("payMode", query.getPayMode());//
			condition.addCondition("contractType", query.getContractType());// 支付类型
			condition.addCondition("payType", query.getPayType());// 支付内容状态
			condition.setPage(query);
			payList = payDao.queryPayContractA(condition);
			query.setPage(condition);
			return payList;
		} catch (Exception e) {
			SxjLogger.error("查询付款管理出错!", e, this.getClass());
			throw new ServiceException("查询付款管理出错!", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<PayRecordEntity> queryPayListB(ContractPayModel query)
			throws ServiceException {
		try {
			List<PayRecordEntity> payList = new ArrayList<PayRecordEntity>();
			if (query == null) {
				return payList;
			}
			QueryCondition<PayRecordEntity> condition = new QueryCondition<PayRecordEntity>();
			condition.addCondition("memberNo", query.getMemberNo());// 会员号
			condition.addCondition("memberType", query.getMemberType());// 会员号
			condition.addCondition("payNo", query.getPayNo());// 支付单号
			condition.addCondition("contractNo", query.getContractNo());// 合同号
			condition.addCondition("rfidNo", query.getRfidNo());// Rfid编号
			condition.addCondition("startPayDate", query.getStartPayDate());// 开始时间
			condition.addCondition("endPayDate", query.getEndPayDate());// 结束时间
			condition.addCondition("state", query.getState());//
			condition.addCondition("memberName_A", query.getMemberNameA());//
			condition.addCondition("payMode", query.getPayMode());//
			condition.addCondition("contractType", query.getContractType());// 支付类型
			condition.addCondition("payType", query.getPayType());// 支付内容状态
			condition.setPage(query);
			payList = payDao.queryPayContractB(condition);
			query.setPage(condition);
			return payList;
		} catch (Exception e) {
			SxjLogger.error("查询付款管理出错!", e, this.getClass());
			throw new ServiceException("查询付款管理出错!", e);
		}
	}

	@Override
	public String updateState(PayRecordEntity re, String flag)
			throws ServiceException {
		try {
			payStagefsm.setCurrentState(re.getState());
			payStagefsm.fire(re.getState().toString() + "_" + flag, re);
			payDao.updatePay(re);
		} catch (Exception e) {
			SxjLogger.error("更改状态出错!", e, this.getClass());
			throw new ServiceException("更改状态出错!", e);
		}
		return "ok";
	}

	@Override
	public String updateMode(String payNo, String event)
			throws ServiceException {
		try {
			PayRecordEntity re = getPayNoBypayNo(payNo);
			payModeFsm.setCurrentState(re.getPayMode());
			payModeFsm.fire(re.getPayMode().toString() + "_"
					+ re.getState().toString() + "_" + event, re);
			payDao.updatePay(re);
			return "1";
		} catch (Exception e) {
			SxjLogger.error("更改状态出错!", e, this.getClass());
			throw new ServiceException("更改状态出错!", e);
		}
	}

	// /**
	// * 甲方付款
	// */
	// @Override
	// @Transactional
	// public String pay(String id, Double payReal) throws ServiceException {
	// try {
	// PayRecordEntity re = payDao.getPayRecordEntity(id);
	// PayStageEnum[] payState = PayStageEnum.values();
	// if (re.getState().ordinal() < 4) {
	// re.setPayReal(payReal);
	// re.setState(payState[4]);
	// re.setPayDate(new Date());
	// payDao.updatePay(re);
	// return "ok";
	// } else {
	// return "false";
	// }
	// } catch (Exception e) {
	// SxjLogger.error("甲方付款出错！", e, this.getClass());
	// throw new ServiceException("甲方付款出错！", e);
	// }
	// }

	/**
	 * 乙方确认收款
	 */
	@Override
	@Transactional
	public String payOk(PayRecordEntity re, String flag)
			throws ServiceException {
		try {
			updateState(re, flag);
			contractService.modifyBatchPayState(re.getContractNo(),
					re.getRfidNo(), re.getPayNo());
			contractService.updateStartDate(re.getContractNo());
			return "ok";
		} catch (Exception e) {
			SxjLogger.error("乙方确认付款出错！", e, this.getClass());
			throw new ServiceException("乙方确认付款出错！", e);
		}
	}

	/**
	 * 财务统计查询
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AccountingModel> query_finance(AccountingModel query,
			String startDate, String endDate, String memberNo)
			throws ServiceException {
		try {
			QueryCondition<AccountingModel> condition = new QueryCondition<AccountingModel>();
			condition.addCondition("recordNo", query.getRecordNo());// 备案号
			condition.addCondition("contractNo", query.getContractNo());// 合同号
			condition.addCondition("state", query.getState());// 合同状态
			condition.addCondition("contractType", query.getContractType());// 合同类型
			condition.addCondition("startDate", startDate);// 开始时间
			condition.addCondition("endDate", endDate);// 结束时间
			condition.addCondition("memberNo", memberNo);// 结束时间
			condition.setPage(query);
			List<AccountingModel> list = accountingDao.query(condition);
			query.setPage(condition);
			list = financeAmount(list);
			return list;
		} catch (Exception e) {
			SxjLogger.error("财务统计查询出错！", e, this.getClass());
			throw new ServiceException("财务统计查询出错！", e);
		}
	}

	@Transactional(readOnly = true)
	public List<AccountingModel> financeAmount(List<AccountingModel> list)
			throws ServiceException {
		try {
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String contractNo = list.get(i).getContractNo();
					Double amount = accountingDao.queryAmountItem(contractNo)
							+ accountingDao.queryAmountModifyItem(contractNo);
					Double payAmount = accountingDao
							.queryAmountBatch(contractNo)
							+ accountingDao.queryAmountModifyBatch(contractNo);
					list.get(i).setAmount(amount);
					list.get(i).setPayAmount(payAmount);
					list.get(i).setNoPayAmount(amount - payAmount);
				}
			}
			return list;
		} catch (Exception e) {
			SxjLogger.error("财务统计金额查询出错！", e, this.getClass());
			throw new ServiceException("财务统计金额查询出错！", e);
		}
	}

	@Override
	@Transactional
	public PayRecordEntity getPayRecordEntity(String id)
			throws ServiceException {
		try {
			PayRecordEntity pay = payDao.getPayRecordEntity(id);
			return pay;
		} catch (Exception e) {
			SxjLogger.error("获取pay实体出错！", e, this.getClass());
			throw new ServiceException("获取pay实体出错！", e);
		}
	}

	@Override
	@Transactional
	public void addPayRecordEntity(PayRecordEntity pay) throws ServiceException {
		try {
			pay.setCreatPayDate(new Date());
			payDao.addPay(pay);
			CometServiceImpl.takeCount(MessageChannel.WEBSITE_PAY_MESSAGE
					+ pay.getMemberNoA());
			redisTopics.getTopic(MessageChannel.TOPIC_NAME).publish(
					MessageChannel.WEBSITE_PAY_MESSAGE + pay.getMemberNoA());
			CometServiceImpl.takeCount(MessageChannel.WEBSITE_FINANCE_MESSAGE
					+ pay.getMemberNoA());
			redisTopics.getTopic(MessageChannel.TOPIC_NAME)
					.publish(
							MessageChannel.WEBSITE_FINANCE_MESSAGE
									+ pay.getMemberNoA());
			TransMessageEntity messageA = new TransMessageEntity();
			messageA.setType(MessageTypeEnum.PAY);
			messageA.setState(MessageStateEnum.UNREAD);
			messageA.setStateMessage("未付款");
			messageA.setContractNo(pay.getPayNo());
			messageA.setMemberNo(pay.getMemberNameA());
			messageA.setSendDate(new Date());
			tms.addMessage(messageA);
			TransMessageEntity messageB = new TransMessageEntity();
			messageB.setType(MessageTypeEnum.PAY);
			messageB.setState(MessageStateEnum.UNREAD);
			messageB.setStateMessage("等待付款");
			messageB.setContractNo(pay.getPayNo());
			messageA.setMemberNo(pay.getMemberNameB());
			messageB.setSendDate(new Date());
			tms.addMessage(messageB);
		} catch (Exception e) {
			SxjLogger.error("新增付款管理出错！", e, this.getClass());
			throw new ServiceException("新增付款管理出错！", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public String getPayNoByRfidNo(String rfidNo) throws ServiceException {
		try {
			PayRecordEntity pay = payDao.getEntityByRfidNo(rfidNo);
			return pay.getPayNo();
		} catch (Exception e) {
			SxjLogger.error("查询支付单号出错！", e, this.getClass());
			throw new ServiceException("查询支付单号出错！", e);
		}
	}

	@Override
	public PayRecordEntity getPayNoBypayNo(String payNo)
			throws ServiceException {
		try {
			return payDao.getEntityByPayNo(payNo);
		} catch (Exception e) {
			SxjLogger.error("根据支付单号查询实体出错！", e, this.getClass());
			throw new ServiceException("根据支付单号查询实体出错！", e);
		}
	}

	@Override
	public List<PayRecordEntity> queryManagerPayList(ContractPayModel query)
			throws ServiceException {
		try {
			List<PayRecordEntity> payList = new ArrayList<PayRecordEntity>();
			if (query == null) {
				return payList;
			}
			QueryCondition<PayRecordEntity> condition = new QueryCondition<PayRecordEntity>();
			condition.addCondition("memberNo", query.getMemberNo());// 会员号
			condition.addCondition("memberType", query.getMemberType());// 会员号
			condition.addCondition("payNo", query.getPayNo());// 支付单号
			condition.addCondition("contractNo", query.getContractNo());// 合同号
			condition.addCondition("rfidNo", query.getRfidNo());// Rfid编号
			condition.addCondition("startPayDate", query.getStartPayDate());// 开始时间
			condition.addCondition("endPayDate", query.getEndPayDate());// 结束时间
			condition.addCondition("state", query.getState());//
			condition.addCondition("memberName_A", query.getMemberNameA());//
			condition.addCondition("payMode", query.getPayMode());//
			condition.addCondition("contractType", query.getContractType());// 支付类型
			condition.addCondition("payType", query.getPayType());// 支付内容状态
			condition.setPage(query);
			payList = payDao.queryManagerPayContract(condition);
			query.setPage(condition);
			return payList;
		} catch (Exception e) {
			SxjLogger.error("查询付款管理出错!", e, this.getClass());
			throw new ServiceException("查询付款管理出错!", e);
		}
	}

	// @Override
	// public String changeState(String payNo, String state)
	// throws ServiceException {
	// try {
	// payDao.changeState(payNo, state);
	// return "1";
	// } catch (Exception e) {
	// SxjLogger.error("改变支付状态出错", e, this.getClass());
	// throw new ServiceException("改变支付状态出错", e);
	// }
	// }

}
