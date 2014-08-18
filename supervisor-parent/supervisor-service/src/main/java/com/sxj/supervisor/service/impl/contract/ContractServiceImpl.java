package com.sxj.supervisor.service.impl.contract;

import java.util.List;

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

public class ContractServiceImpl implements IContractService {

	@Override
	public void addContract(ContractEntity contract,
			List<ContractItemEntity> itemList,
			List<ContractBatchEntity> batchList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyContract(ContractModel contract) {
		// TODO Auto-generated method stub

	}

	@Override
	public ContractModel getContract(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultList<ContractModel> queryContracts(ContractQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteContract(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeContract(String contractId,
			List<BatchItemModel> itemList, List<ContractBatchModel> batchList,
			String recordNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void suppContract(String contractId,
			List<ContractBatchModel> batchList, String recordNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyState(String contractId, Integer state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addStateLog(StateLogModel stateLog, String contractId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyCheckState(String contractId, Integer state) {
		// TODO Auto-generated method stub

	}

}
