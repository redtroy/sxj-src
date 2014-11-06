package com.sxj.supervisor.service.contract;

import java.util.List;

import com.sxj.jsonrpc.annotation.JsonRpcService;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ReplenishContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractModifyModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.ModifyBatchModel;
import com.sxj.supervisor.model.contract.ReplenishBatchModel;
import com.sxj.supervisor.model.contract.StateLogModel;
import com.sxj.util.exception.ServiceException;

@JsonRpcService("api/service/contract.json")
public interface IContractService {
	/**
	 * 新增合同
	 *
	 * @param contract
	 * @param itemList
	 * @param batchList
	 **/
	public void addContract(ContractEntity contract,
			List<ContractItemEntity> itemList, String recordId);

	/**
	 * 修改合同
	 *
	 * @param contract
	 **/
	public void modifyContract(ContractModel contract);

	/**
	 * 获取合同
	 *
	 * @param id
	 **/
	public ContractModel getContract(String id);

	/**
	 * 查询合同列表
	 *
	 * @param query
	 **/
	public List<ContractModel> queryContracts(ContractQuery query);

	/**
	 * 删除合同
	 *
	 * @param id
	 **/
	public void deleteContract(String id);

	/**
	 * 变更合同
	 *
	 * @param contractId
	 * @param itemList
	 * @param batchList
	 * @param recordNo
	 **/
	public void changeContract(String recordId, String contractId,
			ContractModifyModel model, String recordNo,
			List<ContractItemEntity> itemList,String contractIds,String changeIds);

	/**
	 * 补损合同
	 *
	 * @param contractId
	 * @param list
	 * @param recordNo
	 **/
	public void suppContract(String recordId, String contractId,
			List<ReplenishBatchModel> list,
			ReplenishContractEntity replenishContract);

	/**
	 * 更新状态
	 * 
	 * @param contractId
	 * @param state
	 */
	public void modifyState(String contractId, RecordConfirmStateEnum state);

	/**
	 * 新增合同状态变更记录
	 * 
	 * @param stateLog
	 * @param contractId
	 */
	public void addStateLog(StateLogModel stateLog, String contractId);

	/**
	 * 变更确认状态
	 * 
	 * @param contractId
	 * @param state
	 */
	public void modifyCheckState(String contractId, ContractStateEnum state);

	/**
	 * 根据合同号获取详情
	 * 
	 * @param contractNo
	 * @return
	 */
	public ContractModel getContractModelByContractNo(String contractNo);

	/**
	 * 根据合同号获取合同条目
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<ContractItemEntity> getContractItem(String contractNo)
			throws ServiceException;

	/**
	 * 获取合同批次
	 * 
	 * @param contractNo
	 * @param rfid
	 * @return
	 */
	public List<ContractBatchModel> getContractBatch(String contractNo,
			String rfid);

	/**
	 * 获取合同变更批次
	 * 
	 * @param contractNo
	 * @param rfid
	 * @return
	 */
	public List<ModifyBatchModel> getContractModifyBatch(String contractNo,
			String rfid);

	/**
	 * 获取合同补损批次
	 * 
	 * @param contractNo
	 * @param rfid
	 * @return
	 */
	public List<ReplenishBatchModel> getContractReplenishBatch(
			String contractNo, String rfid);

	/**
	 * 修改批次申请合同信息
	 * 
	 * @param contractId
	 * @param state
	 */
	public void modifyBatch(ContractBatchModel model) throws ServiceException;

	void addBatch(ContractBatchModel model, String id, MemberEntity member)
			throws ServiceException;

	void updateRfid(String id, String rfidNo, String contractNo,
			MemberEntity member, String newRfid) throws ServiceException;

	String getReplenish(String contractNo);

	int getContractByZhaobiaoContractNo(String contractNo, String  memberId);

	/**
	 * 根据关联合同号获取合同信息
	 */
	public List<ContractEntity> getContractByRefContractNo(String refContractNo)
			throws ServiceException;

}
