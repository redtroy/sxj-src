package com.sxj.supervisor.service.impl.record;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.file.fastdfs.IFileUpLoad;
import com.sxj.supervisor.dao.contract.IContractBatchDao;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
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
	private IFileUpLoad fastDfsClient;

	/**
	 * 新增备案
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addRecord(RecordEntity record) throws ServiceException {
		try {
			recordDao.addRecord(record);
			Long messageCount = null;
			Object cache = HierarchicalCacheManager.get(2, "comet_record",
					"record_count_message");
			if (cache instanceof Long) {
				messageCount = (Long) cache;
			} else {
				messageCount = 0l;
			}
			messageCount = messageCount + 1;
			HierarchicalCacheManager.set(2, "comet_record",
					"record_count_message", messageCount);
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
		String oldImage = oldRe.getImgPath();
		if (StringUtils.isNotEmpty(oldImage)) {
			oldPath = oldImage.split(",");
		}
		if (StringUtils.isNotEmpty(record.getImgPath())) {
			nowPath = record.getImgPath().split(",");
		}
		StringBuffer newPath = new StringBuffer();
		if (oldPath != null && oldPath.length > 0  ) {
			for (int i = 0; i < oldPath.length; i++) {
				if (StringUtils.isNotEmpty(oldPath[i])) {
					continue;
				}
				for (int j = 0; j < nowPath.length; j++) {
					if (StringUtils.isNotEmpty(nowPath[j])) {
						continue;
					} 
					if (!oldPath[i].equals(nowPath[j])) {
						fastDfsClient.removeFile(oldPath[i]);
					}
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
	public void deleteRecord(String id) {
		RecordEntity record = getRecord(id);
		record.setDelState(true);
		recordDao.updateRecord(record);

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
			record.setAcceptDate(new Date());
			recordDao.updateRecord(record);
		}
		if (record2 != null) {
			record2.setRefContractNo(refContractNo);
			record2.setContractNo(contractNo);
			record2.setState(RecordStateEnum.Binding);
			record2.setAcceptDate(new Date());
			recordDao.updateRecord(record2);
		}
		// 插入合同
		ContractModel ce = contractService.getContractByContractNo(contractNo);
		if (ce != null) {
			ContractEntity contract = ce.getContract();
			contract.setRecordNo(recordNo + "," + recordNo2);
			contractDao.updateContract(contract);
		}

	}

	@Override
	public RecordEntity getRecordByNo(String no) throws ServiceException {
		try {
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

			RecordEntity re = new RecordEntity();
			re.setId(recordId);
			re.setConfirmState(state);
			recordDao.updateRecord(re);
			ContractModel conModel = contractService.getContract(contractId);
			ContractEntity con = conModel.getContract();
			ContractEntity newCon = new ContractEntity();
			newCon.setId(con.getId());
			if (con.getConfirmState().getId() == 0) {
				if (state.getId() == 2) {
					newCon.setConfirmState(ContractSureStateEnum.aaffirm);
				} else if (state.getId() == 3) {
					newCon.setConfirmState(ContractSureStateEnum.baffirm);
				}
			} else {
				newCon.setConfirmState(ContractSureStateEnum.filings);
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
	public void sevaRecord(RecordEntity record) throws ServiceException {
		try {
			recordDao.addRecord(record);
			// 更改合同关联所有的备案状态
			String contractNo = record.getContractNo();
			contractService.getContractByContractNo(contractNo);
			RecordQuery query = new RecordQuery();
			query.setContractNo(contractNo);
			query.setRecordType(RecordTypeEnum.contract.getId());
			List<RecordEntity> list = queryRecord(query);
			for (RecordEntity recordEntity : list) {
				recordEntity.setConfirmState(RecordConfirmStateEnum.accepted);
				recordDao.updateRecord(recordEntity);
			}
		} catch (Exception e) {
			throw new ServiceException("更新备案错误", e);
		}
	}
}
