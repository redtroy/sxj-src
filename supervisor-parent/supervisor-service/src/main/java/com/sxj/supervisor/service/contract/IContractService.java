package com.sxj.supervisor.service.contract;

import java.sql.SQLException;
import java.util.List;

import com.sxj.jsonrpc.annotation.JsonRpcService;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
import com.sxj.supervisor.entity.contract.ReplenishContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractModifyModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.ModifyBatchModel;
import com.sxj.supervisor.model.contract.ReplenishBatchModel;
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
	 * 根据合同号获取合同实体
	 * 
	 * @param contractNo
	 * @return
	 * @throws ServiceException
	 */
	public ContractEntity getContractEntityByNo(String contractNo)
			throws ServiceException;

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
			List<ContractItemEntity> itemList, String contractIds,
			String changeIds,String contractBatchIds,String changeBatchIds);

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
	public ContractBatchModel getContractBatch(String contractNo, String rfid,
			Integer type);

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

	/**
	 * 新增批次
	 * 
	 * @param model
	 * @param id
	 * @param member
	 * @throws ServiceException
	 */
	public void addBatch(ContractBatchModel model, String id,
			MemberEntity member) throws ServiceException;

	/**
	 * RFID标签补损
	 * 
	 * @param rfidNo
	 * @param contractNo
	 * @param member
	 * @param newRfid
	 * @throws ServiceException
	 */
	public void updateRfidLoss(String rfidNo, String contractNo,
			String batchNo, MemberEntity member, String newRfid)
			throws ServiceException;

	/**
	 * 采购合同物流补损
	 * 
	 * @param rfidNos
	 * @param contractNo
	 * @param member
	 * @param newRfid
	 * @throws ServiceException
	 */
	public void updateContractLoss(String rfidNos, String contractNo,
			String recordNo, MemberEntity member, String newRfid)
			throws RuntimeException;

	public String getReplenish(String contractNo);

	public int getContractByZhaobiaoContractNo(String contractNo,
			String memberId);

	/**
	 * 根据关联合同号获取合同信息
	 */
	public List<ContractEntity> getContractByRefContractNo(String refContractNo)
			throws ServiceException;

	/**
	 * 启用门窗RFID
	 * 
	 * @throws ServiceException
	 */
	public void startWindowRfid(Integer startNum, String refContractNo,
			String minRfid, String maxRfid, String gRfid, String lRfid,
			WindowTypeEnum windowType) throws ServiceException;

	/**
	 * 跟据rfid 获取补损批次
	 * 
	 * @param rfid
	 * @return
	 */
	public List<ReplenishBatchEntity> getReplenishByNewRfid(String batchNo,
			String newRfid);

	/**
	 * 根据RFID获取批次
	 * 
	 * @param rfidNo
	 * @return
	 * @throws ServiceException
	 * @throws SQLException
	 */
	public ContractBatchModel getBatchByRfid(String rfidNo)
			throws ServiceException, SQLException;

	/**
	 * 根据RFID删除批次信息
	 * 
	 * @param rfidNo
	 */
	public void deleteBatch(String rfidNo, String contractNo);

	/**
	 * 删除物流RFID关联
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	public void deleteLogisticsRef(String id) throws ServiceException;

	/**
	 * 删除门窗RFID关联
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	public void deleteWindowRef(String id) throws ServiceException;

	/**
	 * 获取合同补损批次实体列表
	 * 
	 * @param contractNo
	 * @return
	 */
	public List<ReplenishBatchEntity> getReplenishBatch(String contractNo);

	/**
	 * 修改批次支付状态
	 * 
	 * @param contractNo
	 * @param rfidNo
	 */
	public void modifyBatchPayState(String contractNo, String rfidNo, String payNo);

	List<ContractBatchModel> getBacthsByContractNo(String contractNo);

	

}
