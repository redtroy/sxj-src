package com.sxj.supervisor.service.impl.contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.contract.IContractBatchDao;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.contract.IContractItemDao;
import com.sxj.supervisor.dao.contract.IContractModifyBatchDao;
import com.sxj.supervisor.dao.contract.IContractModifyDao;
import com.sxj.supervisor.dao.contract.IContractModifyItemDao;
import com.sxj.supervisor.dao.contract.IContractReplenishBatchDao;
import com.sxj.supervisor.dao.contract.IContractReplenishDao;
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ModifyBatchEntity;
import com.sxj.supervisor.entity.contract.ModifyContractEntity;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
import com.sxj.supervisor.entity.contract.ReplenishContractEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractModifyModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.ContractReplenishModel;
import com.sxj.supervisor.model.contract.ModifyBatchModel;
import com.sxj.supervisor.model.contract.ReplenishBatchModel;
import com.sxj.supervisor.model.contract.StateLogModel;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

/**
 * 合同管理业务类
 * 
 * @author Ann
 *
 */
@Service
@Transactional
public class ContractServiceImpl implements IContractService {

	/**
	 * 合同DAO
	 */
	@Autowired
	private IContractDao contractDao;

	/**
	 * 批次DAO
	 */
	@Autowired
	private IContractBatchDao contractBatchDao;
	/**
	 * 变更合同批次DAO
	 */
	@Autowired
	private IContractModifyBatchDao contractBatchHisDao;
	/**
	 * 合同产品条目
	 */
	@Autowired
	private IContractItemDao contractItemDao;
	/**
	 * 变更合同产品条目
	 */
	@Autowired
	private IContractModifyItemDao contractModifyItemDao;
	/**
	 * 变更合同主体
	 */
	@Autowired
	private IContractModifyDao contractModifyDao;
	/**
	 * 变更合同批次
	 */
	@Autowired
	private IContractModifyBatchDao contractModifyBatchDao;
	/**
	 * 补损合同主体
	 */
	@Autowired
	private IContractReplenishDao contractReplenishDao;
	/**
	 * 补损合同批次
	 */
	@Autowired
	private IContractReplenishBatchDao contractReplenishBatchDao;

	/**
	 * 备案Dao
	 */
	@Autowired
	private IRecordDao recordDao;

	/**
	 * 新增合同
	 */
	@Override
	@Transactional
	public void addContract(ContractEntity contract,
			List<ContractItemEntity> itemList,String recordId)throws ServiceException {
		if(contract!=null){
			RecordEntity record=recordDao.getRecord(recordId);
			//拼装实体
			if(record!=null){
				contract.setRecordDate(record.getAcceptDate());	//备案时间就是受理时间?
				contract.setRecordNo(record.getRecordNo());//备案号
			}
			contract.setState(0);
			contract.setConfirmState(0);
			contract.setCreateDate(new Date());
			contractDao.addContract(contract);
		
			if (itemList != null) {
				for (int i = 0; i < itemList.size(); i++) {
					ContractItemEntity ci = itemList.get(i);
					if(ci.getAmount()!=null && ci.getPrice()!=null){
						ci.setContractId(contract.getId());
						contractItemDao.addItem(ci);// 新增条目
					}
					
				}
				
			}
		}
	}

	/**
	 * 修改合同
	 */
	@Override
	public void modifyContract(ContractModel contract) {
	

	}

	/**
	 * 获取合同
	 */
	@Override
	@Transactional
	public ContractModel getContract(String id)throws ServiceException {
		ContractModel contractModel = new ContractModel();
		ContractEntity contract = contractDao.getContract(id);// 合同主体
		if (contract != null) {
			contractModel.setContract(contract);
			List<ContractItemEntity> itemList = contractItemDao
					.queryItems(contract.getId());// 产品条目
			if (itemList != null && itemList.size() > 0) {
				contractModel.setItemList(itemList);
			}
			List<ContractBatchEntity> batchList = contractBatchDao
					.queryBacths(contract.getId());// 批次
			if (batchList != null && batchList.size() > 0) {
				List<ContractBatchModel> newBatchModelLIst = new ArrayList<ContractBatchModel>();
				for (int i = 0; i < batchList.size(); i++) {
					ContractBatchEntity batch = batchList.get(i);
					ContractBatchModel batchModel = new ContractBatchModel();
				}
				contractModel.setBatchList(newBatchModelLIst);
			}
			//时间轴
//			if(contract.getStateLog()!=null && contract.getStateLog().length()>0){
//				List<StateLogModel>  stateLogModel=(List<StateLogModel>) JsonMapper.nonEmptyMapper().fromJson(contract.getStateLog(), StateLogModel.class);
//				//时间排序
//				Collections.sort(stateLogModel, new Comparator<StateLogModel>() {
//		            public int compare(StateLogModel arg0, StateLogModel arg1) {
//		                return arg0.getModifyDate().compareTo(arg1.getModifyDate());
//		            }
//		        });
//				contractModel.setStateLogList(stateLogModel);//时间轴
//			}
			// 变更信息
			
			String modifyRecordIds=this.recordIdArr(contract.getId(), "1");//获取变更备案
			if (modifyRecordIds != null) {
				
				// 变更合同主体
				QueryCondition<ModifyBatchEntity> modifyCondition = new QueryCondition<ModifyBatchEntity>();
				Map<String, Object> modifyMap = new HashMap<String, Object>();
				modifyMap.put("recordIds", modifyRecordIds);// 变更备案ID
				modifyCondition.setCondition(modifyMap);
				List<ModifyContractEntity> modifyList = contractModifyDao
						.queryModify(modifyCondition);
				if (modifyList != null) {
					List<ContractModifyModel> modifymodelList = new ArrayList<ContractModifyModel>();
					for (int i = 0; i < modifyList.size(); i++) {
						ContractModifyModel cmm = new ContractModifyModel();
						ModifyContractEntity modify = modifyList.get(i);
						cmm.setModifyContract(modify);
						List<ModifyItemEntity> item = contractModifyItemDao
								.queryItems(modify.getId());//变更条目
						cmm.setModifyItemList(item);
						List<ModifyBatchEntity> batch = contractModifyBatchDao
								.queryBacths(modify.getId());//变更批次
						List<ModifyBatchModel> modifyBatchModelList = new ArrayList<ModifyBatchModel>();
						for (int j = 0; j < batch.size(); j++) {
							ModifyBatchModel modifyBatchModel = new ModifyBatchModel();
							ModifyBatchEntity modifyBatchEntity = batch.get(j);
							if(modifyBatchEntity.getBatchItems()!=null && modifyBatchEntity.getBatchItems().length()>0){
								List<BatchItemModel> batchItemModel = (List<BatchItemModel>) JsonMapper.nonEmptyMapper().fromJson(modifyBatchEntity.getBatchItems(),BatchItemModel.class);
								modifyBatchModel.setModifyBatchItems(batchItemModel);
							}
							modifyBatchModel.setModifyBatch(modifyBatchEntity);
							modifyBatchModelList.add(modifyBatchModel);
						}
						cmm.setModifyBatchList(modifyBatchModelList);
						modifymodelList.add(cmm);
					}
					contractModel.setModifyList(modifymodelList);
				}
				
			}
			//补损合同
			String replenishRecordIds=this.recordIdArr(contract.getId(), "2");//获取变更备案
			if(replenishRecordIds!=null && replenishRecordIds.length()>0){
				
				QueryCondition<ReplenishContractEntity> replenishCondition = new QueryCondition<ReplenishContractEntity>();
				Map<String, Object> replenishMap = new HashMap<String, Object>();
				replenishMap.put("recordIds", replenishRecordIds);// 补损备案ID
				replenishCondition.setCondition(replenishMap);
				List<ReplenishContractEntity> replenishList = contractReplenishDao.queryReplenish(replenishCondition);
				for (int i = 0; i < replenishList.size(); i++) {
					ContractReplenishModel contractReplenishModel = new ContractReplenishModel();
					ReplenishContractEntity replenishEntity= replenishList.get(i);
					contractReplenishModel.setReplenishContract(replenishEntity);
					List<ReplenishBatchEntity> replenishBatchList=contractReplenishBatchDao.queryReplenishBatch(replenishEntity.getId());
					if(replenishBatchList!=null){
						List<ReplenishBatchModel>  ReplenishBatchModelList= new ArrayList<ReplenishBatchModel>();
						for (int j = 0; j < replenishBatchList.size(); j++) {
							ReplenishBatchModel replenishBatchModel = new ReplenishBatchModel();
							ReplenishBatchEntity ReplenishBatchEntity= replenishBatchList.get(j);
							replenishBatchModel.setReplenishBatch(ReplenishBatchEntity);
							if(ReplenishBatchEntity.getBatchItems()!=null && ReplenishBatchEntity.getBatchItems().length()>0){
								List<BatchItemModel> batchItemModelList=(List<BatchItemModel>) JsonMapper.nonEmptyMapper().fromJson(ReplenishBatchEntity.getBatchItems(), BatchItemModel.class);
								replenishBatchModel.setReplenishBatchItems(batchItemModelList);
							}
							ReplenishBatchModelList.add(replenishBatchModel);
						}
						contractReplenishModel.setBatchItems(ReplenishBatchModelList);
					}
				}
			}
		}
		return contractModel;
	}
	/**
	 * 获取备案ID
	 * @param contractId
	 * @param type 变更备案:1/补损备案:2
	 * @return
	 */
	public String recordIdArr(String contractId,String type){
		QueryCondition<RecordEntity> qc = new QueryCondition<RecordEntity>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("contractNo", contractId);// 合同号
		condition.put("type", type);// 备案状态
		qc.setCondition(condition);
		List<RecordEntity> record = recordDao.queryRecord(qc);
		String recordIds = "";
		for (Iterator<RecordEntity> iterator = record.iterator(); iterator
				.hasNext();) {
			RecordEntity recordEntity = (RecordEntity) iterator.next();
			recordIds += recordEntity.getId() + ",";
			recordIds = recordIds.substring(0, recordIds.length() - 1);
		}
		return recordIds;
		
	}

	/**
	 * 查询合同列表
	 */
	@Override
	public List<ContractModel> queryContracts(ContractQuery query)
			throws ServiceException {
		QueryCondition<ContractEntity> qc = new QueryCondition<ContractEntity>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", query.getContractNo());// 合同号
		map.put("recordNo", query.getRecordNo());// 备案号
		map.put("memberId", query.getMemberId());// 签订会员ＩＤ
		map.put("contractType", query.getContractType());// 合同类型
		map.put("refContractNo", query.getRefContractNo());// 关联合同号
		map.put("startCreateDate", query.getStartCreateDate());// 开始签订时间
		map.put("endCreateDate", query.getEndCreateDate());// 结束签订合同号
		map.put("startRecordDate", query.getStartRecordDate());// 开始备案时间
		map.put("endRecordDate", query.getEndRecordDate());// 结束备案时间
		map.put("confirmState", query.getConfirmState());// 确认状态
		map.put("state", query.getState());// 合同状态
		qc.setCondition(map);
		List<ContractEntity> contractList = contractDao.queryContract(qc);
		List<ContractModel> contractModelList = new ArrayList<ContractModel>();
		for (Iterator<ContractEntity> iterator = contractList.iterator(); iterator
				.hasNext();) {
			ContractEntity contractEntity = (ContractEntity) iterator.next();
			// JsonMapper.nonEmptyMapper().fromJson(contractEntity.getStateLog(),
			// StateLogModel.class);//备案记录
			ContractModel cm = new ContractModel();
			cm.setContract(contractEntity);
			contractModelList.add(cm);
		}
		return contractModelList;
	}

	/**
	 * 删除合同
	 */
	@Override
	public void deleteContract(String id) {
		contractDao.deleteContract(id);

	}

	/**
	 * 变更合同
	 */
	@Override
	public void changeContract(String contractId,
			List<ModifyItemEntity> itemList,
			List<ContractBatchModel> batchList, String recordNo) {
		contractBatchHisDao.addBatchs(batchList
				.toArray(new ModifyBatchEntity[itemList.size()]));
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
		ContractEntity ce = contractDao.getContract(contractId);
		if (ce != null) {
			ce.setState(state);
			contractDao.updateContract(ce);
		}
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
		ContractEntity ce = contractDao.getContract(contractId);
		if (ce != null) {
			ce.setState(state);
			contractDao.updateContract(ce);
		}
	}

}
