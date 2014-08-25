package com.sxj.supervisor.service.impl.record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.util.persistent.QueryCondition;
import com.sxj.util.persistent.ResultList;
import com.sxj.util.persistent.ResultListImpl;
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
		RecordEntity re=new RecordEntity();
		
		recordDao.updateRecord(re);

	}

	/**
	 * 删除备案
	 */
	@Override
	public void deleteRecord(String id) {
		recordDao.deleteRecord(id);

	}

	/**
	 * 查询备案
	 */
	@Override
	public RecordEntity getRecord(String id) {
		RecordEntity record=recordDao.getRecord(id);
		return record;
	}

	/**
	 * 条件查询备案
	 */
	@Override
	@Transactional
	public List<RecordEntity> queryRecord(RecordQuery query) {
		QueryCondition<RecordEntity> qc = new QueryCondition<RecordEntity>();
		Map<String, Object> condition =new HashMap<String, Object>();
		condition.put("recrodNo", query.getRecrodNo());//备案号
		condition.put("memberId",query.getMemberId());//会员Id
		condition.put("applyId",query.getApplyId());//申请会员ID
		condition.put("contractType",query.getContractType());//合同类型
		condition.put("contractNo",query.getContractNo());//合同号
		condition.put("recordType",query.getRecordType());//备案类型
		condition.put("state",query.getState());//备案状态
		condition.put("startApplyDate",query.getStartApplyDate());//开始申请时间
		condition.put("endApplyDate",query.getEndApplyDate());//结束申请时间
		condition.put("startAcceptDate",query.getStartAcceptDate());//开始受理时间
		condition.put("endAcceptDate",query.getEndAcceptDate());//结束受理时间
		qc.setCondition(condition);
		List<RecordEntity> recordList= recordDao.queryRecord(qc);
		return recordList;
	}

	/**
	 * 绑定合同
	 */
	@Override
	@Transactional
	public void bindingContract(String contractNo, String refContractNo,
			String recordId) {
		
		RecordEntity record= recordDao.getRecord(recordId);
		if(record!=null){
			record.setRefContractNo(refContractNo);
			record.setContractNo(contractNo);
			recordDao.updateRecord(record);
		}
	}

}
