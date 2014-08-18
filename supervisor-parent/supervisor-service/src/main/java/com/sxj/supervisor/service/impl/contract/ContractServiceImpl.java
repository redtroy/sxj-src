package com.sxj.supervisor.service.impl.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.StateLogModel;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.util.persistent.ResultList;
/**
 * 合同业务类
 * @author Ann
 *
 */
@Transactional
public class ContractServiceImpl implements IContractService {

	/**
	 * 合同DAO
	 */
	@Autowired
	private IContractDao contractDao;
	
	/**
	 * 新增合同
	 */
	@Override
	public void addContract(ContractEntity contract,
			List<ContractItemEntity> itemList,
			List<ContractBatchEntity> batchList) {
		// TODO Auto-generated method stub

	}

	/**
	 * 修改合同
	 */
	@Override
	public void modifyContract(ContractModel contract) {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取合同
	 */
	@Override
	public ContractModel getContract(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询合同列表
	 */
	@Override
	public ResultList<ContractModel> queryContracts(ContractQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 删除合同
	 */
	@Override
	public void deleteContract(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 变更合同
	 */
	@Override
	public void changeContract(String contractId,
			List<BatchItemModel> itemList, List<ContractBatchModel> batchList,
			String recordNo) {
		// TODO Auto-generated method stub

	}

	/**
	 * 补损合同
	 */
	@Override
	public void suppContract(String contractId,
			List<ContractBatchModel> batchList, String recordNo) {
		// TODO Auto-generated method stub

	}

	/**
	 * 更新合同
	 */
	@Override
	public void modifyState(String contractId, Integer state) {
		// TODO Auto-generated method stub

	}
	/**
	 * 新增合同状态变更记录
	 */
	@Override
	public void addStateLog(StateLogModel stateLog, String contractId) {
		// TODO Auto-generated method stub

	}

	/**
	 * 变更确认状态
	 */
	@Override
	public void modifyCheckState(String contractId, Integer state) {
		// TODO Auto-generated method stub

	}

}
