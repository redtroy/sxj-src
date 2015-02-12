package com.sxj.supervisor.service.impl.contract;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.contract.IContractPayDao;
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.enu.contract.PayContractTypeEnum;
import com.sxj.supervisor.enu.contract.PayModeEnum;
import com.sxj.supervisor.enu.contract.PayStageEnum;
import com.sxj.supervisor.enu.contract.PayTypeEnum;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.apply.PayStateEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.service.contract.IContractProcessService;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class ContractProcessServiceImpl implements IContractProcessService {

	/**
	 * 合同DAO
	 */
	@Autowired
	private IContractDao contractDao;

	/**
	 * 备案Dao
	 */
	@Autowired
	private IRecordDao recordDao;

	@Autowired
	private IRfidApplicationService appRfidService;

	@Autowired
	private IContractPayDao payDao;

	@Autowired
	private RedisTopics redisTopics;

	/**
	 * 变更确认状态
	 */
	@Override
	@Transactional
	public void modifyConfirmState(ContractEntity contract)
			throws ServiceException {
		try {
			contractDao.updateContract(contract);
			if (contract.getRecordNo() != null) {
				// RecordQuery recordQuery = new RecordQuery();
				// recordQuery.setContractNo(contract.getContractNo());
				// recordQuery.setSort("DESC");
				// recordQuery.setSortColumn("R.APPLY_DATE");
				// List<RecordEntity> recordList = recordService
				// .queryRecord(recordQuery);
				// // 变更该合同所有备案状态
				// if (recordList != null) {
				// for (RecordEntity recordEntity : recordList) {
				// fsm.setCurrentState(recordEntity.getConfirmState());
				// fsm.fire("ac_un", null);
				// recordEntity.setConfirmState(fsm.getCurrentState());
				//
				// if (recordEntity.getFlag().getId() == 0) {
				// if (recordEntity.getAcceptState() == null
				// || recordEntity.getAcceptState() == 0) {
				// recordEntity.setAcceptDate(new Date());// 受理时间
				// recordEntity.setAcceptState(1);
				// }
				// } else {
				// recordEntity.setAcceptDate(new Date());
				// }
				// recordDao.updateRecord(recordEntity);
				// }
				// 更改合同备案确认状态
				recordDao.batchUpdateConfirmState(contract.getContractNo());
				// 生成RFID申请单
				if (contract.getType().equals(ContractTypeEnum.BIDDING)) {
					RfidApplicationEntity app = new RfidApplicationEntity();
					app.setDateNo(DateTimeUtils.getTime("yyMM"));
					app.setMemberNo(contract.getMemberIdB());
					app.setMemberName(contract.getMemberNameB());
					app.setRfidType(RfidTypeEnum.DOOR);
					app.setContractNo(contract.getContractNo());
					int count = (int) (contract.getItemQuantity() + 100);
					app.setCount(new Long(count));
					app.setApplyDate(new Date());
					app.setPayState(PayStateEnum.NOT_PAYMENT);
					app.setReceiptState(ReceiptStateEnum.SHIPMENTS);
					app.setHasNumber(0l);
					appRfidService.addApp(app);
				}

				RecordEntity userRecord = recordDao.queryUserRecord(contract
						.getContractNo());

				String key_a = MessageChannel.WEBSITE_RECORD_MESSAGE
						+ userRecord.getMemberIdA();
				// List<String> messageList = CometServiceImpl.get(key_a);
				// if (messageList == null || messageList.size() == 0) {
				// messageList = new ArrayList<String>();
				// }
				String msgName = "";
				if (userRecord.getType().getId() == 0) {
					if (userRecord.getContractType().getId() == 0) {
						msgName = "开发商";
					}
				} else if (userRecord.getType().getId() == 1) {
					msgName = "变更";
				} else if (userRecord.getType().getId() == 2) {
					msgName = "补损";
				}
				String message = userRecord.getId() + "," + msgName + ","
						+ contract.getContractNo() + ','
						+ userRecord.getMemberIdA() + ','
						+ userRecord.getContractType().getId();
				// messageList.add(message);
				CometServiceImpl.add(key_a, message);
				redisTopics.getTopic(MessageChannel.TOPIC_NAME).publish(key_a);
				// HierarchicalCacheManager.set(2, "comet_message",
				// "record_push_message_" + record.getMemberIdA(),
				// messageList);
				// 乙方
				String key_b = MessageChannel.WEBSITE_RECORD_MESSAGE
						+ userRecord.getMemberIdB();
				// List<String> messageListB = null;
				// Object cacheB = HierarchicalCacheManager.get(2,
				// "comet_message",
				// "record_push_message_" + record.getMemberIdB());
				// if (cacheB instanceof ArrayList) {
				// messageListB = (List<String>) cacheB;
				// } else {
				// messageListB = new ArrayList<String>();
				// }
				String msgNameB = "";
				if (userRecord.getType().getId() == 0) {
					if (userRecord.getContractType().getId() == 0) {
						msgNameB = "开发商";
					}
				} else if (userRecord.getType().getId() == 1) {
					msgNameB = "变更";
				} else if (userRecord.getType().getId() == 2) {
					msgNameB = "补损";
				}
				String messageB = userRecord.getId() + "," + msgNameB + ","
						+ contract.getContractNo() + ','
						+ userRecord.getMemberIdB() + ','
						+ userRecord.getContractType().getId();
				CometServiceImpl.add(key_b, messageB);
				redisTopics.getTopic(MessageChannel.TOPIC_NAME).publish(key_b);
				// messageListB.add(messageB);
				// HierarchicalCacheManager.set(2, "comet_message",
				// "record_push_message_" + record.getMemberIdB(),
				// messageListB);
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("确认合同错误", e);
		}
	}

	@Override
	public void modifyCheckState(ContractEntity contract) {
		try {
			contractDao.updateContract(contract);
			// 批量修改审核状态
			if (contract.getRecordNo() != null) {
				// 更改合同备案审核状态
				recordDao.batchUpdateCheckState(contract.getContractNo());
				// 生成RFID申请单
				if (contract.getType().equals(ContractTypeEnum.BIDDING)) {
					RfidApplicationEntity app = new RfidApplicationEntity();
					app.setDateNo(DateTimeUtils.getTime("yyMM"));
					app.setMemberNo(contract.getMemberIdB());
					app.setMemberName(contract.getMemberNameB());
					app.setRfidType(RfidTypeEnum.DOOR);
					app.setContractNo(contract.getContractNo());
					int count = (int) (contract.getItemQuantity() + 100);
					app.setCount(new Long(count));
					app.setApplyDate(new Date());
					app.setPayState(PayStateEnum.NOT_PAYMENT);
					app.setReceiptState(ReceiptStateEnum.SHIPMENTS);
					app.setHasNumber(0l);
					appRfidService.addApp(app);
				}

				RecordEntity userRecord = recordDao.queryUserRecord(contract
						.getContractNo());

				String key_a = MessageChannel.WEBSITE_RECORD_MESSAGE
						+ userRecord.getMemberIdA();

				String msgName = "";
				if (userRecord.getType().getId() == 0) {
					if (userRecord.getContractType().getId() == 0) {
						msgName = "开发商";
					}
				} else if (userRecord.getType().getId() == 1) {
					msgName = "变更";
				} else if (userRecord.getType().getId() == 2) {
					msgName = "补损";
				}
				String message = userRecord.getId() + "," + msgName + ","
						+ contract.getContractNo() + ','
						+ userRecord.getMemberIdA() + ','
						+ userRecord.getContractType().getId();
				CometServiceImpl.add(key_a, message);
				redisTopics.getTopic(MessageChannel.TOPIC_NAME).publish(key_a);

				// 乙方
				String key_b = MessageChannel.WEBSITE_RECORD_MESSAGE
						+ userRecord.getMemberIdB();

				String msgNameB = "";
				if (userRecord.getType().getId() == 0) {
					if (userRecord.getContractType().getId() == 0) {
						msgNameB = "开发商";
					}
				} else if (userRecord.getType().getId() == 1) {
					msgNameB = "变更";
				} else if (userRecord.getType().getId() == 2) {
					msgNameB = "补损";
				}
				String messageB = userRecord.getId() + "," + msgNameB + ","
						+ contract.getContractNo() + ','
						+ userRecord.getMemberIdB() + ','
						+ userRecord.getContractType().getId();
				CometServiceImpl.add(key_b, messageB);
				redisTopics.getTopic(MessageChannel.TOPIC_NAME).publish(key_b);
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("审核合同错误", e);
		}

	}

	@Override
	@Transactional
	public void addContractPay(String contractNo) throws ServiceException {
		try {
			Assert.hasText(contractNo, "合同号不能为空");
			ContractEntity con = contractDao
					.getContractByContractNo(contractNo);
			Assert.notNull(con, "合同不存在");
			// 生成支付单
			if (con.getType().equals(ContractTypeEnum.BIDDING)) {
				return;
			}
			PayRecordEntity pay = new PayRecordEntity();
			pay.setDateNo(con.getContractNo() + "P");// 编号
			pay.setContractNo(con.getContractNo());
			pay.setMemberNameA(con.getMemberNameA());
			pay.setMemberNameB(con.getMemberNameB());
			pay.setMemberNoA(con.getMemberIdA());
			pay.setMemberNoB(con.getMemberIdB());
			pay.setPayAmount(con.getDeposit());// 定金
			pay.setContent("合同定金");
			pay.setState(PayStageEnum.STAGE1);
			pay.setPayMode(PayModeEnum.MODE1);
			if (con.getType().equals(ContractTypeEnum.GLASS)) {
				pay.setContractType(PayContractTypeEnum.GLASS);
			} else if (con.getType().equals(ContractTypeEnum.EXTRUSIONS)) {
				pay.setContractType(PayContractTypeEnum.EXTRUDERS);
			}

			pay.setPayType(PayTypeEnum.DEPOSIT);
			int flag = payDao.addContractPay(pay);// 新增定金支付单
			if (flag == 1) {
				CometServiceImpl.takeCount(MessageChannel.WEBSITE_PAY_MESSAGE
						+ pay.getMemberNoA());
				redisTopics.getTopic(MessageChannel.TOPIC_NAME)
						.publish(
								MessageChannel.WEBSITE_PAY_MESSAGE
										+ pay.getMemberNoA());
			}
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("新增合同支付单错误", e);
		}
	}

}
