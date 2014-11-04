package com.sxj.supervisor.service.impl.record;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.redis.service.comet.CometServiceImpl;
import com.sxj.supervisor.dao.contract.IContractBatchDao;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class RecordServiceImpl implements IRecordService {

	@Autowired
	private IRecordDao recordDao;

	@Autowired
	private IContractService contractService;

	@Autowired
	private IContractDao contractDao;

	@Autowired
	private IContractBatchDao batchDao;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IStorageClientService storageClientService;

	/**
	 * 新增备案
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addRecord(RecordEntity record) throws ServiceException {
		try {
			String year = new SimpleDateFormat("yy", Locale.CHINESE)
					.format(Calendar.getInstance().getTime());
			String month = new SimpleDateFormat("MM", Locale.CHINESE)
					.format(Calendar.getInstance().getTime());
			record.setDateNo("BA" + year + month);
			record.setAcceptState(0);
			record.setRecordState(0);
			recordDao.addRecord(record);

			CometServiceImpl.takeCount(MessageChannel.RECORD_MESSAGE);
			MessageChannel.initTopic().publish(MessageChannel.RECORD_MESSAGE);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("新增备案信息错误", e);
		}

	}

	/**
	 * 更新备案
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void modifyRecord(RecordEntity record) {
		RecordEntity oldRe = getRecord(record.getId());
		String[] oldPath = null;
		String[] nowPath = null;
		if (oldRe != null) {
			String oldImage = oldRe.getImgPath();
			if (StringUtils.isNotEmpty(oldImage)) {
				oldPath = oldImage.split(",");
			}
			if (StringUtils.isNotEmpty(record.getImgPath())) {
				nowPath = record.getImgPath().split(",");
			}
			if (oldPath != null && oldPath.length > 0) {
				for (int i = 0; i < oldPath.length; i++) {
					if (StringUtils.isNotEmpty(oldPath[i])) {
						continue;
					}
					for (int j = 0; j < nowPath.length; j++) {
						if (StringUtils.isNotEmpty(nowPath[j])) {
							continue;
						}
						if (!oldPath[i].equals(nowPath[j])) {
							storageClientService.deleteFile(oldPath[i]);
						}
					}
				}
			}
		}
		// 更改用户---修改备案状态
		if (oldRe.getContractType().getId() != 0) {
			MemberEntity member = memberService.memberInfo(record
					.getMemberIdB());
			if (member != null) {
				if (member.getType().getId() == 1) {
					record.setContractType(ContractTypeEnum.glass);
				} else if (member.getType().getId() == 2) {
					record.setContractType(ContractTypeEnum.extrusions);
				}
			}
		}

		recordDao.updateRecord(record);
	}

	/**
	 * 删除备案
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRecord(String id) throws ServiceException {
		try {
			recordDao.deleteRecord(id);

		} catch (Exception e) {
			throw new ServiceException("查询备案信息错误", e);
		}
	}

	/**
	 * 查询备案
	 */
	@Override
	@Transactional(readOnly = true)
	public RecordEntity getRecord(String id) {
		RecordEntity record = recordDao.getRecord(id);
		return record;
	}

	/**
	 * 条件查询备案
	 */
	@Override
	@Transactional
	public List<RecordEntity> queryRecord(RecordQuery query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<RecordEntity> condition = new QueryCondition<RecordEntity>();
			condition.addCondition("recordNo", query.getRecordNo());// 备案号
			condition.addCondition("memberId", query.getMemberId());// 会员Id
			condition.addCondition("applyId", query.getApplyId());// 申请会员ID
			condition.addCondition("contractType", query.getContractType());// 合同类型
			condition.addCondition("contractNo", query.getContractNo());// 合同号
			condition.addCondition("recordType", query.getRecordType());// 备案类型
			condition.addCondition("state", query.getState());// 备案状态
			condition.addCondition("startApplyDate", query.getStartApplyDate());// 开始申请时间
			condition.addCondition("endApplyDate", query.getEndApplyDate());// 结束申请时间
			condition.addCondition("startAcceptDate",
					query.getStartAcceptDate());// 开始受理时间
			condition.addCondition("endAcceptDate", query.getEndAcceptDate());// 结束受理时间
			condition.addCondition("contractPepole", query.getContractPepole());
			condition.addCondition("memberIdA", query.getMemberIdA());// 结束受理时间
			condition.addCondition("memberIdB", query.getMemberIdB());// 结束受理时间
			condition.addCondition("confirmState", query.getConfirmState());// 确认状态
			condition.addCondition("sort", query.getSort());// 排序
			condition.addCondition("sortColumn", query.getSortColumn());// 排序字段
			condition.addCondition("flag", query.getFlag());// 备案方
			condition.addCondition("type", query.getType());// 备案类型

			condition.setPage(query);
			List<RecordEntity> recordList = recordDao.queryRecord(condition);
			query.setPage(condition);
			return recordList;
		} catch (Exception e) {
			throw new ServiceException("查询备案信息错误", e);
		}

	}

	/**
	 * 绑定合同
	 */
	@Override
	@Transactional
	public void bindingContract(String contractNo, String refContractNo,
			String recordNo, String recordNo2, String recordIdA,
			String recordIdB) {

		RecordEntity record = recordDao.getRecord(recordIdA);
		RecordEntity record2 = recordDao.getRecord(recordIdB);
		if (record != null) {
			record.setRefContractNo(refContractNo);
			record.setContractNo(contractNo);
			record.setState(RecordStateEnum.Binding);
			recordDao.updateRecord(record);
		}
		if (record2 != null) {
			record2.setRefContractNo(refContractNo);
			record2.setContractNo(contractNo);
			record2.setState(RecordStateEnum.Binding);
			recordDao.updateRecord(record2);
		}
		// 插入合同
		ContractModel ce = contractService
				.getContractModelByContractNo(contractNo);
		if (ce != null) {
			ContractEntity contract = ce.getContract();
			contract.setRecordNo(recordNo + "," + recordNo2);
			contract.setRefContractNo(refContractNo);
			contractDao.updateContract(contract);
		}

	}

	@Override
	public RecordEntity getRecordByNo(String no) throws ServiceException {
		try {
			if (StringUtils.isEmpty(no)) {
				return null;
			}
			RecordQuery query = new RecordQuery();
			query.setRecordNo(no);
			List<RecordEntity> list = queryRecord(query);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException("", e);
		}
	}

	@Override
	@Transactional
	public void modifyState(String contractId, String recordId,
			RecordConfirmStateEnum state) {
		try {

			// RecordEntity re = new RecordEntity();
			// re.setId(recordId);
			// re.setConfirmState(state);
			// recordDao.updateRecord(re);
			ContractModel conModel = contractService.getContract(contractId);
			ContractEntity con = conModel.getContract();
			// 更改合同关联所有备案状态
			RecordQuery recordQuery = new RecordQuery();
			recordQuery.setContractNo(con.getContractNo());
			List<RecordEntity> recordList = queryRecord(recordQuery);
			for (RecordEntity re : recordList) {
				RecordEntity rEntity = new RecordEntity();
				rEntity.setId(re.getId());
				if (re.getContractType().getId() != 0) {
					if (con.getConfirmState().getId() == 0) {
						if (state.getId() == 2) {
							rEntity.setConfirmState(RecordConfirmStateEnum.confirmedA);
						} else if (state.getId() == 3) {
							rEntity.setConfirmState(RecordConfirmStateEnum.confirmedB);
						}
					} else {
						rEntity.setConfirmState(RecordConfirmStateEnum.hasRecord);
						if (re.getFlag().getId() == 1) {
							rEntity.setRecordState(1);
							rEntity.setRecordDate(new Date());// 备案时间
						}
						if (re.getType().getId() == 1) {
							rEntity.setState(RecordStateEnum.change);
							if (re.getRecordState() == 0) {
								rEntity.setRecordState(1);
								rEntity.setRecordDate(new Date());// 备案时间
							}
						} else if (re.getType().getId() == 2) {
							rEntity.setState(RecordStateEnum.supplement);
							if (re.getRecordState() == 0) {
								rEntity.setRecordState(1);
								rEntity.setRecordDate(new Date());// 备案时间
							}
						} else {
							rEntity.setState(RecordStateEnum.Binding);
							if (re.getRecordState() == 0) {
								rEntity.setRecordState(1);
								rEntity.setRecordDate(new Date());// 备案时间
							}
						}
					}
				} else {
					rEntity.setConfirmState(RecordConfirmStateEnum.hasRecord);
					rEntity.setRecordDate(new Date());// 备案时间

				}
				recordDao.updateRecord(rEntity);
			}
			ContractEntity newCon = new ContractEntity();
			newCon.setId(con.getId());
			if (con.getType().getId() != 0) {
				if (con.getConfirmState().getId() == 0) {
					if (state.getId() == 2) {
						newCon.setConfirmState(ContractSureStateEnum.aaffirm);
					} else if (state.getId() == 3) {
						newCon.setConfirmState(ContractSureStateEnum.baffirm);
					}
				} else {
					newCon.setConfirmState(ContractSureStateEnum.filings);
					newCon.setRecordDate(new Date());
				}
			} else {
				newCon.setConfirmState(ContractSureStateEnum.filings);
				newCon.setRecordDate(new Date());
			}
			contractDao.updateContract(newCon);
		} catch (Exception e) {
			throw new ServiceException("", e);
		}

	}

	/**
	 * 获取批次
	 * 
	 * @param recordId
	 * @return
	 */
	@Override
	@Transactional
	public String getBatch(String recordId) {
		try {
			RecordEntity re = recordDao.getRecord(recordId);
			QueryCondition<ContractBatchEntity> query = new QueryCondition<ContractBatchEntity>();
			query.addCondition("contractId", re.getContractNo());
			List<ContractBatchEntity> batchList = batchDao.queryBacths(query);
			String batch = "";
			String batchId = "";
			for (ContractBatchEntity contractBatchEntity : batchList) {
				batch += contractBatchEntity.getBatchNo() + ",";
				batchId += contractBatchEntity.getId() + ",";
			}
			if (batch != "") {
				batch = batch.substring(0, batch.length() - 1);
				batchId = batchId.substring(0, batchId.length() - 1);
				StringBuffer sb = new StringBuffer();
				sb.append(batch).append("#").append(batchId);
				return sb.toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			throw new ServiceException("查询批次信息错误", e);
		}
	}

	/**
	 * 获取批次
	 * 
	 * @param recordId
	 * @return
	 */
	@Override
	@Transactional
	public List<ContractBatchEntity> getBatchList(String recordId) {
		try {
			RecordEntity re = recordDao.getRecord(recordId);
			QueryCondition<ContractBatchEntity> query = new QueryCondition<ContractBatchEntity>();
			query.addCondition("contractId", re.getContractNo());
			List<ContractBatchEntity> batchList = batchDao.queryBacths(query);
			if (batchList != null) {
				return batchList;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new ServiceException("查询批次信息错误", e);
		}
	}

	@Override
	public String getRfid(String batchId) {
		try {
			ContractBatchEntity batch = batchDao.getBatch(batchId);
			return batch.getRfidNo();
		} catch (Exception e) {
			throw new ServiceException("查询rfid信息错误", e);
		}
	}

	@Override
	@Transactional
	public void saveRecord(RecordEntity record) throws ServiceException {
		try {
			record.setDateNo("BA"
					+ DateTimeUtils.formateDate2Str(new Date(), "yyMM"));
			record.setApplyDate(new Date());// 申請時間
			record.setAcceptDate(null);
			record.setAcceptState(0);
			record.setRecordState(0);
			record.setRecordDate(null);
			recordDao.addRecord(record);
			// 更改合同关联所有的备案状态
			String contractNo = record.getContractNo();
			ContractModel cm = contractService
					.getContractModelByContractNo(contractNo);
			RecordQuery query = new RecordQuery();
			query.setContractNo(contractNo);
			query.setRecordType("0");
			List<RecordEntity> list = queryRecord(query);
			for (RecordEntity recordEntity : list) {
				recordEntity.setConfirmState(RecordConfirmStateEnum.accepted);
				recordDao.updateRecord(recordEntity);
			}
			if (cm != null) {
				// 变更合同状态
				ContractEntity ce = cm.getContract();
				ContractEntity centity = new ContractEntity();
				centity.setId(ce.getId());
				centity.setState(ContractStateEnum.approval);
				centity.setConfirmState(ContractSureStateEnum.noaffirm);
				contractDao.updateContract(centity);
			}
			Long messageCount = null;
			Object cache = HierarchicalCacheManager.get(2, "comet_message",
					"record_count_message");
			if (cache instanceof Long) {
				messageCount = (Long) cache;
			} else {
				messageCount = 0l;
			}
			messageCount = messageCount + 1;
			HierarchicalCacheManager.set(2, "comet_message",
					"record_count_message", messageCount);
		} catch (Exception e) {
			throw new ServiceException("更新备案错误", e);
		}
	}

	/**
	 * 同步合同与备案信息
	 */
	@Override
	@Transactional
	public void updateRecordAndContract(RecordEntity record,
			ContractEntity contract) throws ServiceException {
		try {
			// if (record != null) {
			// if (record.getContractType().getId() == 0) {
			// recordDao.updateRecord(record);
			// ContractModel cm = contractService
			// .getContractModelByContractNo(record
			// .getContractNo());
			// contract = cm.getContract();
			// contract.setMemberNameA(record.getMemberNameA());
			// contractDao.updateContract(contract);
			// } else {
			// recordDao.updateRecord(record);
			// ContractModel cm = contractService
			// .getContractModelByContractNo(record
			// .getContractNo());
			// contract = cm.getContract();
			// contract.setMemberIdA(record.getMemberIdA());
			// contract.setMemberIdB(record.getMemberIdB());
			// contract.setMemberNameA(record.getMemberNameA());
			// contract.setMemberNameB(record.getMemberNameB());
			// contract.setType(record.getContractType());
			// contract.setRefContractNo(record.getRefContractNo());
			// contractDao.updateContract(contract);
			// // 更新所有备案信息
			// QueryCondition<RecordEntity> query = new
			// QueryCondition<RecordEntity>();
			// query.addCondition("contractNo", record.getContractNo());
			// query.addCondition("applyId", record.getApplyId());
			// List<RecordEntity> recordList = recordDao
			// .queryRecord(query);
			// for (RecordEntity recordEntity : recordList) {
			// if (recordEntity.getContractType().getId() != 0) {
			// recordEntity.setMemberIdA(record.getMemberIdA());
			// recordEntity.setMemberIdB(record.getMemberIdB());
			// recordEntity
			// .setMemberNameA(record.getMemberNameA());
			// recordEntity
			// .setMemberNameB(record.getMemberNameB());
			// }
			// recordDao.updateRecord(recordEntity);
			// }
			// }
			// }
		} catch (Exception e) {
			throw new ServiceException("更新合同备案出错!", e);
		}
	}

}
