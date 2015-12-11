package com.sxj.supervisor.service.record;

import java.util.List;

import com.sxj.statemachine.exceptions.StateMachineException;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.util.exception.ServiceException;

public interface IRecordService {
	/**
	 * 新增备案
	 **/
	public void addRecord(RecordEntity record) throws ServiceException;

	/**
	 * 修改备案
	 *
	 * @param record
	 **/
	public void modifyRecord(RecordEntity record) throws ServiceException;

	/**
	 * 删除备案
	 *
	 * @param id
	 **/
	public void deleteRecord(String id) throws ServiceException;

	/**
	 * 获取备案详情
	 *
	 * @param id
	 **/
	public RecordEntity getRecord(String id) throws ServiceException;

	/**
	 * 获取备案详情
	 *
	 * @param id
	 **/
	public RecordEntity getRecordByNo(String no) throws ServiceException;

	/**
	 * 查询备案列表
	 *
	 * @param query
	 **/
	public List<RecordEntity> queryRecord(RecordQuery query)
			throws ServiceException;

	/**
	 * 绑定合同
	 *
	 * @param contractNo
	 * @param refContractNo
	 * @param recordId
	 **/
	public void bindingContract(String contractNo, String refContractNo,
			String recordNo, String recordNo2, String recordIdA,
			String recordIdB) throws ServiceException;

	/**
	 * 更改状态
	 * 
	 * @param contractId
	 * @param state
	 */
	public void modifyState(String contractId, MemberTypeEnum memType);

	public void batchModifyConfimState(String contractNo, MemberTypeEnum memType)
			throws StateMachineException;

	public List<ContractBatchEntity> getBatch(String recordId);

	public String getRfid(String batchId);

	public void saveRecord(RecordEntity record) throws ServiceException;

	public List<ContractBatchEntity> getBatchList(String recordId);

	void updateRecordAndContract(RecordEntity record, ContractEntity contract)
			throws ServiceException;

	String getRecordNo(String contractNo, MemberEntity menber);

    void editRecord(RecordEntity record) throws ServiceException;

    /**
     * 获取会员申请的备案
     * @param contractNo
     * @param memberNo
     * @param type
     * @return
     */
	RecordEntity getRecordByContract(String contractNo, String memberNo,
			String type);

}
