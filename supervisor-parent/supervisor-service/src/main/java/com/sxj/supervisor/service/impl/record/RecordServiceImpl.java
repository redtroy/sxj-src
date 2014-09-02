package com.sxj.supervisor.service.impl.record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class RecordServiceImpl implements IRecordService {

	@Autowired
	private IRecordDao recordDao;

	/**
	 * 新增备案
	 */
	@Override
	public void addRecord(RecordEntity record) {
		recordDao.addRecord(record);
	}

	/**
	 * 更新备案
	 */
	@Override
	public void modifyRecord(RecordEntity record) {
		RecordEntity re = getRecord(record.getId());
		re.setId(record.getId());
		re.setApplyName(record.getApplyName());
		re.setMemberNameA(record.getMemberNameA());
		re.setMemberNameB(record.getMemberNameB());
		re.setContractType(record.getContractType());
		re.setRecordNo(record.getRecordNo());
		re.setType(record.getType());
		if(record.getImgPath()!=null && record.getImgPath().length()>0){
			re.setImgPath(record.getImgPath());
		}
		if(record.getRfidNo()!=null && record.getRfidNo().length()>0){
			re.setRfidNo(record.getRfidNo());
		}
		recordDao.updateRecord(re);
	}

	/**
	 * 删除备案
	 */
	@Override
	public void deleteRecord(String id) {
		RecordEntity record = getRecord(id);
		record.setDelState(true);
		recordDao.updateRecord(record);

	}

	/**
	 * 查询备案
	 */
	@Override
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
			String recordNo, String recordNo2) {

		RecordEntity record = recordDao.getRecord(recordNo);
		RecordEntity record2 = recordDao.getRecord(recordNo2);
		if (record != null) {
			record.setRefContractNo(refContractNo);
			record.setContractNo(contractNo);
			recordDao.updateRecord(record);
		}
		if (record2 != null) {
			record2.setRefContractNo(refContractNo);
			record2.setContractNo(contractNo);
			recordDao.updateRecord(record2);
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
}
