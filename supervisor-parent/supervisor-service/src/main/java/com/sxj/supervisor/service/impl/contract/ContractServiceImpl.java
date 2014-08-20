package com.sxj.supervisor.service.impl.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.contract.IContractBatchDao;
import com.sxj.supervisor.dao.contract.IContractModifyBatchDao;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.contract.IContractItemDao;
import com.sxj.supervisor.dao.contract.IContractModifyItemDao;
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ModifyBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.StateLogModel;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
import com.sxj.util.persistent.ResultList;
import com.sxj.util.persistent.ResultListImpl;

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
	private IContractModifyItemDao contractItemHisDao;
	/**
	 * 备案Dao
	 */
	@Autowired
	private IRecordDao recordDao;
	
	/**
	 * 新增合同
	 */
	@Override
	public void addContract(ContractEntity contract,
			List<ContractItemEntity> itemList,
			List<ContractBatchEntity> batchList) {
		contractDao.addContract(contract);
		if (contract.getId() != null) {
			if (itemList != null) {
				for (int i = 0; i < itemList.size(); i++) {
					ContractItemEntity ci = itemList.get(i);
					ci.setContractId(contract.getId());
				}
				contractItemDao.addItems(itemList
						.toArray(new ContractItemEntity[itemList.size()]));// 新增条目
			}
			if (batchList != null) {
				for (int i = 0; i < batchList.size(); i++) {
					ContractBatchEntity cb = batchList.get(i);
					cb.setContractId(contract.getId());// 新增批次
				}
				contractBatchDao.addBatchs(batchList
						.toArray(new ContractBatchEntity[batchList.size()]));
			}
		}
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
					ContractBatchEntity batch=batchList.get(i);
					ContractBatchModel batchModel = new ContractBatchModel();
				}
				contractModel.setBatchList(newBatchModelLIst);
			}
			// 变更信息
			QueryCondition<RecordEntity> qc = new QueryCondition<RecordEntity>();
			Map<String, Object> condition =new HashMap<String, Object>();
			condition.put("contractNo", contract.getId());//合同号
			qc.setCondition(condition);
			List<RecordEntity> record=recordDao.queryRecord(qc);
			if(record!=null){
				String recordId = "";
				for (Iterator<RecordEntity> iterator = record.iterator(); iterator.hasNext();) {
					RecordEntity recordEntity = (RecordEntity) iterator.next();
					if(recordEntity.getType()==1){//合同变更备案类型
						recordId +=recordEntity.getId()+",";
					}
					recordId = recordId.substring(0,recordId.length()-1);
				}
//				//条目
//				QueryCondition<ModifyItemEntity> itemQuery = new QueryCondition<ModifyItemEntity>();
//				Map<String, Object> itemMap =new HashMap<String, Object>();
//				itemMap.put("recordIds", recordId);//合同号
//				itemQuery.setCondition(itemMap);
//				List itemHisList=contractItemHisDao.queryItems(itemQuery);
//				contractModel.setModifyItemList(itemHisList);
//				//批次
//				QueryCondition<ModifyBatchEntity> batchQuery = new QueryCondition<ModifyBatchEntity>();
//				Map<String, Object> batchMap =new HashMap<String, Object>();
//				batchMap.put("recordIds", recordId);//合同号
//				batchQuery.setCondition(batchMap);
//				List batchHisList=contractBatchHisDao.queryBacths(batchQuery);
//				contractModel.setModifyBatchList(batchHisList);
//				//变更扫描件
//				QueryCondition<ContractImgHisEntity> imgQuery = new QueryCondition<ContractImgHisEntity>();
//				Map<String, Object> imgMap =new HashMap<String, Object>();
//				imgMap.put("recordIds", recordId);//合同号
//				imgQuery.setCondition(imgMap);
//				List imgHisList=contractImgHisDao.queryImages(imgQuery);
//				contractModel.setHisImageList(imgHisList);
			}
		}
		return contractModel;
	}

	/**
	 * 查询合同列表
	 */
	@Override
	public List<ContractModel> queryContracts(ContractQuery query) throws ServiceException {
		QueryCondition<ContractEntity> qc = new QueryCondition<ContractEntity>();
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("contractNo", query.getContractNo());//合同号
		map.put("recordNo", query.getRecordNo());//备案号
		map.put("memberId", query.getMemberId());//签订会员ＩＤ
		map.put("contractType",query.getContractType());//合同类型
		map.put("refContractNo", query.getRefContractNo());//关联合同号
		map.put("startCreateDate", query.getStartCreateDate());//开始签订时间
		map.put("endCreateDate", query.getEndCreateDate());//结束签订合同号
		map.put("startRecordDate", query.getStartRecordDate());//开始备案时间
		map.put("endRecordDate", query.getEndRecordDate());//结束备案时间
		map.put("confirmState", query.getConfirmState());//确认状态
		map.put("state", query.getState());//合同状态
		qc.setCondition(map);
		List<ContractEntity> contractList =contractDao.queryContract(qc);
		List<ContractModel> contractModelList = new ArrayList<ContractModel>();
		for (Iterator<ContractEntity> iterator = contractList.iterator(); iterator.hasNext();) {
			ContractEntity contractEntity = (ContractEntity) iterator.next();
			//JsonMapper.nonEmptyMapper().fromJson(contractEntity.getStateLog(), StateLogModel.class);//备案记录
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
			List<ModifyItemEntity> itemList, List<ContractBatchModel> batchList,
			String recordNo) {
		contractBatchHisDao.addBatchs(batchList.toArray(new ModifyBatchEntity[itemList.size()]));
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
		ContractEntity ce=contractDao.getContract(contractId);
		if(ce!=null){
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
		ContractEntity ce=contractDao.getContract(contractId);
		if(ce!=null){
			ce.setState(state);
			contractDao.updateContract(ce);
		}
	}

}
