package com.sxj.supervisor.service.impl.contract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sxj.cache.manager.HierarchicalCacheManager;
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
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
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
import com.sxj.supervisor.service.record.IRecordService;
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
	 * 备案service
	 */
	@Autowired
	private IRecordService recordService;

	/**
	 * 新增合同
	 */
	@Override
	@Transactional
	public void addContract(ContractEntity contract,
			List<ContractItemEntity> itemList, String recordId)
			throws ServiceException {
		try {
			if (contract != null) {
				RecordEntity record = recordDao.getRecord(recordId);
				// 拼装实体
				if (record != null) {
					contract.setRecordDate(record.getAcceptDate()); // 备案时间就是受理时间?
					contract.setRecordNo(record.getRecordNo());// 备案号
					contract.setType(record.getContractType());
					contract.setImgPath(record.getImgPath());

					contract.setState(ContractStateEnum.approval);
					contract.setConfirmState(ContractSureStateEnum.noaffirm);
					contract.setCreateDate(new Date());
					contractDao.addContract(contract);

					if (itemList != null) {
						List<ContractItemEntity> newList = new ArrayList<ContractItemEntity>();
						for (int i = 0; i < itemList.size(); i++) {
							ContractItemEntity ci = itemList.get(i);
							if (ci.getAmount() != null && ci.getPrice() != null) {
								ci.setContractId(contract.getContractNo());
								newList.add(ci);

							}

						}
						contractItemDao.addItem(newList);// 新增条目
					}
					if (contract.getContractNo() != null) {
						record.setContractNo(contract.getContractNo());
						record.setState(RecordStateEnum.Binding);
						recordDao.updateRecord(record);
					}
					List<String> messageList = null;
					Object cache = HierarchicalCacheManager.get(2,
							"comet_record", "record_push_message");
					if (cache instanceof ArrayList) {
						messageList = (List<String>) cache;
					} else {
						messageList = new ArrayList<String>();
					}
					String message = record.getId() + ","
							+ record.getType().getName();
					messageList.add(message);
					HierarchicalCacheManager.set(2, "comet_record",
							"record_push_message", messageList);
				}
			}
		} catch (Exception e) {
			throw new ServiceException("新增合同出错", e);
		}
	}

	/**
	 * 修改合同
	 */
	@Override
	public void modifyContract(ContractModel contract) throws ServiceException {
		try {
			// 主体
			if (contract.getContract() != null) {

				ContractEntity ce = contractDao.getContract(contract
						.getContract().getId());
				String[] arr = ce.getRecordNo().split(",");
				
				String recordNo = contract.getContract().getRecordNo();
				if(recordNo!=null && recordNo.length()>0){
				String[] recordNoArr = recordNo.split(",");
				boolean flag = Arrays.equals(arr, recordNoArr);
				if (!flag) {
					// 解绑备案号
					for (String str : arr) {
						RecordEntity re = recordService.getRecordByNo(str
								.trim());
						re.setContractNo("");
						re.setState(RecordStateEnum.noBinding);
						recordDao.updateRecord(re);
					}
					// 更新备案号
					for (String str : recordNoArr) {
						RecordEntity re = recordService.getRecordByNo(str);
						re.setContractNo(contract.getContract().getContractNo());
						re.setState(RecordStateEnum.Binding);
						recordDao.updateRecord(re);
					}
				}
				}
				contractDao.updateContract(contract.getContract());
			}
			// 条目
			if (contract.getItemList() != null) {
				contractItemDao.updateItem(contract.getItemList());
			}
			// 批次
			if (contract.getBatchList() != null) {
				List<ContractBatchEntity> cbelist = new ArrayList<ContractBatchEntity>();
				for (int i = 0; i < contract.getBatchList().size(); i++) {
					ContractBatchModel cbm = contract.getBatchList().get(i);
					ContractBatchEntity cbe = cbm.getBatch();
					if (cbm.getBatchItems() != null) {
						cbe.setBatchItems(JsonMapper.nonEmptyMapper().toJson(
								cbm.getBatchItems()));// 转json
					}
					cbelist.add(cbe);
				}
				contractBatchDao.updateBatchs(cbelist);
			}
			// 变更记录
			if (contract.getModifyList() != null) {
				List<ModifyContractEntity> mceList = new ArrayList<ModifyContractEntity>();// 变更记录主体
				for (int i = 0; i < contract.getModifyList().size(); i++) {
					ContractModifyModel cmm = contract.getModifyList().get(i);
					if (cmm.getModifyContract() != null) {
						mceList.add(cmm.getModifyContract());
					}
					if (cmm.getModifyItemList() != null) {
						contractModifyItemDao.updateItems(cmm
								.getModifyItemList());
					}
					List<ModifyBatchEntity> mbeList = new ArrayList<ModifyBatchEntity>();
					for (int j = 0; j < cmm.getModifyBatchList().size(); j++) {
						ModifyBatchModel mbm = cmm.getModifyBatchList().get(j);
						if (mbm.getModifyBatchItems() != null) {
							mbm.getModifyBatch().setBatchItems(
									JsonMapper.nonEmptyMapper().toJson(
											mbm.getModifyBatchItems()));
						}
						mbeList.add(mbm.getModifyBatch());
					}
					contractModifyBatchDao.updateItems(mbeList);
				}
				contractModifyDao.updateModify(mceList);
			}
			// 补损记录
			if (contract.getReplenishList() != null) {
				List<ReplenishContractEntity> mceList = new ArrayList<ReplenishContractEntity>();// 补损记录主体
				for (int i = 0; i < contract.getReplenishList().size(); i++) {
					ContractReplenishModel crm = contract.getReplenishList()
							.get(i);
					if (crm.getReplenishContract() != null) {
						mceList.add(crm.getReplenishContract());
					}
					List<ReplenishBatchEntity> rbeList = new ArrayList<ReplenishBatchEntity>();
					if (crm.getBatchItems() != null) {
						for (int j = 0; j < crm.getBatchItems().size(); j++) {
							ReplenishBatchModel rbm = crm.getBatchItems()
									.get(j);
							if (rbm.getReplenishBatch() != null) {
								rbm.getReplenishBatch().setBatchItems(
										JsonMapper.nonEmptyMapper().toJson(
												rbm.getReplenishBatchItems()));
							}
							rbeList.add(rbm.getReplenishBatch());
						}
						contractReplenishBatchDao.updateReplenishBatch(rbeList);
					}
				}
				contractReplenishDao.updateReplenish(mceList);
			}
		} catch (Exception e) {
			throw new ServiceException("修改合同出错", e);
		}
	}

	/**
	 * 获取合同
	 */
	@Override
	@Transactional
	public ContractModel getContract(String id) throws ServiceException {
		try{
		ContractModel contractModel = new ContractModel();
		ContractEntity contract = contractDao.getContract(id);// 合同主体
		if (contract != null) {
			contractModel.setContract(contract);
			List<ContractItemEntity> itemList = contractItemDao
					.queryItems(contract.getContractNo());// 产品条目
			if (itemList != null && itemList.size() > 0) {
				contractModel.setItemList(itemList);
			}
			QueryCondition<ContractBatchEntity> batchCondition = new QueryCondition<ContractBatchEntity>();
			batchCondition
					.addCondition("contractId", contract.getContractNo());// 补损备案
			List<ContractBatchEntity> batchList = contractBatchDao
					.queryBacths(batchCondition);// 批次
			if (batchList != null && batchList.size() > 0) {
				List<ContractBatchModel> newBatchModelLIst = new ArrayList<ContractBatchModel>();
				List<BatchItemModel> bmList = new ArrayList<BatchItemModel>();
				for (int i = 0; i < batchList.size(); i++) {
					ContractBatchEntity batch = batchList.get(i);
					ContractBatchModel batchModel = new ContractBatchModel();
					batchModel.setBatch(batch);
					System.err.println(batch.getBatchItems());
					List<BatchItemModel> beanList = null;
					try {
						beanList = JsonMapper
								.nonEmptyMapper()
								.getMapper()
								.readValue(
										batch.getBatchItems(),
										new TypeReference<List<BatchItemModel>>() {
										});

					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					batchModel.setBatchItems(beanList);
					newBatchModelLIst.add(batchModel);
				}
				System.err.println(JsonMapper.nonEmptyMapper().toJson(bmList));
				contractModel.setBatchList(newBatchModelLIst);
			}
//			// 时间轴
//			if (contract.getStateLog() != null
//					&& contract.getStateLog().length() > 0) {
//				List<StateLogModel> stateLogModel = null;
//				try {
//					stateLogModel = JsonMapper
//							.nonEmptyMapper()
//							.getMapper()
//							.readValue(contract.getStateLog(),
//									new TypeReference<List<StateLogModel>>() {
//									});
//				} catch (JsonParseException e) {
//					e.printStackTrace();
//				} catch (JsonMappingException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				// 时间排序
//				Collections.sort(stateLogModel,
//						new Comparator<StateLogModel>() {
//							public int compare(StateLogModel arg0,
//									StateLogModel arg1) {
//								return arg0.getModifyDate().compareTo(
//										arg1.getModifyDate());
//							}
//						});
//				contractModel.setStateLogList(stateLogModel);// 时间轴
//			}
			// 变更信息

			String modifyRecordIds = this.recordIdArr(contract.getContractNo(),
					"1");// 获取变更备案
			if (modifyRecordIds != null && modifyRecordIds.length() > 0) {

				// 变更合同主体
				QueryCondition<ModifyBatchEntity> modifyCondition = new QueryCondition<ModifyBatchEntity>();
				modifyCondition.addCondition("recordIds", modifyRecordIds);// 变更备案ID
				List<ModifyContractEntity> modifyList = contractModifyDao
						.queryModify(modifyCondition);
				if (modifyList != null) {
					List<ContractModifyModel> modifymodelList = new ArrayList<ContractModifyModel>();
					for (int i = 0; i < modifyList.size(); i++) {
						ContractModifyModel cmm = new ContractModifyModel();
						ModifyContractEntity modify = modifyList.get(i);
						cmm.setModifyContract(modify);
						List<ModifyItemEntity> item = contractModifyItemDao
								.queryItems(modify.getId());// 变更条目
						cmm.setModifyItemList(item);
						List<ModifyBatchEntity> batch = contractModifyBatchDao
								.queryBacths(modify.getId());// 变更批次
						if (batch != null && batch.size() > 0) {
							List<ModifyBatchModel> modifyBatchModelList = new ArrayList<ModifyBatchModel>();
							for (int j = 0; j < batch.size(); j++) {
								ModifyBatchModel modifyBatchModel = new ModifyBatchModel();
								ModifyBatchEntity modifyBatchEntity = batch
										.get(j);
								if (modifyBatchEntity.getBatchItems() != null
										&& modifyBatchEntity.getBatchItems()
												.length() > 0) {
									List<BatchItemModel> batchItemModel = null;
									try {
										batchItemModel = JsonMapper
												.nonEmptyMapper()
												.getMapper()
												.readValue(
														modifyBatchEntity
																.getBatchItems(),
														new TypeReference<List<BatchItemModel>>() {
														});
									} catch (JsonParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (JsonMappingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									modifyBatchModel
											.setModifyBatchItems(batchItemModel);
								}
								modifyBatchModel
										.setModifyBatch(modifyBatchEntity);
								modifyBatchModelList.add(modifyBatchModel);
							}
							cmm.setModifyBatchList(modifyBatchModelList);
						}
						modifymodelList.add(cmm);
					}
					contractModel.setModifyList(modifymodelList);
				}

			}
			// 补损合同
			String replenishRecordIds = this.recordIdArr(
					contract.getContractNo(), "2");// 获取变更备案
			if (replenishRecordIds != null && replenishRecordIds.length() > 0) {

				QueryCondition<ReplenishContractEntity> replenishCondition = new QueryCondition<ReplenishContractEntity>();
				replenishCondition
						.addCondition("recordIds", replenishRecordIds);// 补损备案ID
				List<ReplenishContractEntity> replenishList = contractReplenishDao
						.queryReplenish(replenishCondition);
				for (int i = 0; i < replenishList.size(); i++) {
					ContractReplenishModel contractReplenishModel = new ContractReplenishModel();
					ReplenishContractEntity replenishEntity = replenishList
							.get(i);
					contractReplenishModel
							.setReplenishContract(replenishEntity);
					List<ReplenishBatchEntity> replenishBatchList = contractReplenishBatchDao
							.queryReplenishBatch(replenishEntity.getId());
					List<ContractReplenishModel> crmList = new ArrayList<ContractReplenishModel>();
					if (replenishBatchList != null) {
						List<ReplenishBatchModel> ReplenishBatchModelList = new ArrayList<ReplenishBatchModel>();
						for (int j = 0; j < replenishBatchList.size(); j++) {
							ReplenishBatchModel replenishBatchModel = new ReplenishBatchModel();
							ReplenishBatchEntity ReplenishBatchEntity = replenishBatchList
									.get(j);
							replenishBatchModel
									.setReplenishBatch(ReplenishBatchEntity);
							if (ReplenishBatchEntity.getBatchItems() != null
									&& ReplenishBatchEntity.getBatchItems()
											.length() > 0) {
								List<BatchItemModel> batchItemModelList = null;
								try {
									batchItemModelList = JsonMapper
											.nonEmptyMapper()
											.getMapper()
											.readValue(
													ReplenishBatchEntity
															.getBatchItems(),
													new TypeReference<List<BatchItemModel>>() {
													});
								} catch (JsonParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								replenishBatchModel
										.setReplenishBatchItems(batchItemModelList);
							}
							ReplenishBatchModelList.add(replenishBatchModel);
						}
						contractReplenishModel
								.setBatchItems(ReplenishBatchModelList);
						crmList.add(contractReplenishModel);
					}
					contractModel.setReplenishList(crmList);
				}

			}
		}
		return contractModel;
	} catch (Exception e) {
		throw new ServiceException("查询合同信息错误", e);
	}
	}
	/**
	 * 获取备案ID
	 * 
	 * @param contractId
	 * @param type
	 *            变更备案:1/补损备案:2
	 * @return
	 */
	public String recordIdArr(String contractNo, String type) {
		QueryCondition<RecordEntity> qc = new QueryCondition<RecordEntity>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("contractNo", contractNo);// 合同号
		condition.put("type", type);// 备案状态
		qc.setCondition(condition);
		List<RecordEntity> record = recordDao.queryRecord(qc);
		String recordIds = "";
		for (Iterator<RecordEntity> iterator = record.iterator(); iterator
				.hasNext();) {
			RecordEntity recordEntity = (RecordEntity) iterator.next();
			recordIds += recordEntity.getRecordNo() + ",";
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
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<ContractEntity> condition = new QueryCondition<ContractEntity>();
			condition.addCondition("contractNo", query.getContractNo());// 合同号
			condition.addCondition("recordNo", query.getRecordNo());// 备案号
			condition.addCondition("memberId", query.getMemberId());// 签订会员ＩＤ
			condition.addCondition("contractType", query.getContractType());// 合同类型
			condition.addCondition("refContractNo", query.getRefContractNo());// 关联合同号
			condition.addCondition("startCreateDate",
					query.getStartCreateDate());// 开始签订时间
			condition.addCondition("endCreateDate", query.getEndCreateDate());// 结束签订合同号
			condition.addCondition("startRecordDate",
					query.getStartRecordDate());// 开始备案时间
			condition.addCondition("endRecordDate", query.getEndRecordDate());// 结束备案时间
			condition.addCondition("confirmState", query.getConfirmState());// 确认状态
			condition.addCondition("state", query.getState());// 合同状态
			condition.setPage(query);

			List<ContractEntity> contractList = contractDao
					.queryContract(condition);
			query.setPage(condition);
			List<ContractModel> contractModelList = new ArrayList<ContractModel>();
			for (ContractEntity contractEntity : contractList) {
				// JsonMapper.nonEmptyMapper().fromJson(contractEntity.getStateLog(),
				// StateLogModel.class);//备案记录
				ContractModel cm = new ContractModel();
				cm.setContract(contractEntity);
				contractModelList.add(cm);
			}
			return contractModelList;
		} catch (Exception e) {
			throw new ServiceException("查询合同信息错误", e);
		}

	}

	/**
	 * 删除合同
	 */
	@Override
	public void deleteContract(String id) throws ServiceException {
		try {
			ContractEntity ce = new ContractEntity();
			ce.setId(id);
			ce.setDeleteState(true);
			contractDao.updateContract(ce);
		} catch (Exception e) {
			throw new ServiceException("删除合同出错", e);
		}
	}

	/**
	 * 变更合同
	 */
	@Override
	@Transactional
	public void changeContract(String contractId, ContractModifyModel model,
			String recordNo, List<ContractItemEntity> itemList)
			throws ServiceException {
		try {
			ModifyContractEntity mec = model.getModifyContract();
			if (itemList != null) {
				contractItemDao.updateItem(itemList);
			}
			if (mec != null) {
				contractModifyDao.addModify(mec);
				if (mec.getId() != null) {
					// 变更条目
					List<ModifyItemEntity> mieList = new ArrayList<ModifyItemEntity>();
					if (model.getModifyItemList() != null) {
						for (Iterator iterator = model.getModifyItemList()
								.iterator(); iterator.hasNext();) {
							ModifyItemEntity mie = (ModifyItemEntity) iterator
									.next();
							mie.setModifyId(mec.getId());
							mie.setUpdateState(0);
							mieList.add(mie);
						}
						contractModifyItemDao.addItems(mieList);
					}
					// 变更批次
					List<ModifyBatchModel> mbmList = model.getModifyBatchList();
					List<ModifyBatchEntity> mbeList = new ArrayList<ModifyBatchEntity>();
					if (mbmList != null) {
						for (Iterator iterator = mbmList.iterator(); iterator
								.hasNext();) {
							ModifyBatchModel modifyBatchModel = (ModifyBatchModel) iterator
									.next();
							ModifyBatchEntity mbe = modifyBatchModel
									.getModifyBatch();
							String json = JsonMapper.nonEmptyMapper().toJson(
									modifyBatchModel.getModifyBatchItems());
							mbe.setBatchItems(json);
							mbeList.add(mbe);
						}
						contractModifyBatchDao.addBatchs(mbeList);
					}

				}
			}
		} catch (Exception e) {
			throw new ServiceException("变更合同信息错误", e);
		}
	}

	/**
	 * 补损合同
	 */
	@Override
	public void suppContract(String contractId,
			List<ReplenishBatchModel> batchList,
			ReplenishContractEntity replenishContract) throws ServiceException {
		try {
			if (replenishContract != null) {
				contractReplenishDao.addReplenish(replenishContract);
				if (replenishContract.getId() != null) {
					// 补损批次
					if (batchList != null) {
						List<ReplenishBatchEntity> list = new ArrayList<ReplenishBatchEntity>();
						for (ReplenishBatchModel replenishBatchModel : batchList) {
							String json = JsonMapper.nonEmptyMapper().toJson(
									replenishBatchModel
											.getReplenishBatchItems());
							ReplenishBatchEntity rb = replenishBatchModel
									.getReplenishBatch();
							rb.setBatchItems(json);
							rb.setReplenishId(replenishContract.getId());
							list.add(rb);
						}
						contractReplenishBatchDao.addReplenishBatch(list);
					}

				}

			}
		} catch (Exception e) {
			throw new ServiceException("补损合同信息错误", e);
		}
	}

	/**
	 * 更新合同
	 */
	@Override
	public void modifyState(String contractId, RecordConfirmStateEnum state)
			throws ServiceException {
		// try {
		// ContractEntity ce = new ContractEntity();
		// ce.setId(contractId);
		// ce.setConfirmState(state);
		// contractDao.updateContract(ce);
		// } catch (Exception e) {
		// throw new ServiceException("更改合同状态出错", e);
		// }
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
	public void modifyCheckState(String contractId, ContractStateEnum state)
			throws ServiceException {
		try {
			ContractEntity ce = new ContractEntity();
			if (contractId != null) {
				ce.setId(contractId);
				ce.setState(state);
				contractDao.updateContract(ce);
			}
		} catch (Exception e) {
			throw new ServiceException("审核合同错误", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ContractModel getContractByContractNo(String contractNo) {
		try {
			ContractQuery query = new ContractQuery();
			query.setContractNo(contractNo);
			List<ContractModel> res = queryContracts(query);
			if (res != null && res.size() > 0) {
				return res.get(0);
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException("获取合同信息错误", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ContractModel getContractModelByContractNo(String contractNo) {
		try {
			ContractQuery query = new ContractQuery();
			query.setContractNo(contractNo);
			List<ContractModel> res = queryContracts(query);
			if (res != null && res.size() > 0) {
				ContractModel cm = getContract(res.get(0).getContract().getId());
				return cm;
			}
			return null;
		} catch (Exception e) {
			throw new ServiceException("获取合同信息错误", e);
		}
	}
	@Override
	@Transactional(readOnly = true)
	public List<ContractBatchModel> getContractBatch(String contractNo,String rfidNo) {
		try {
			QueryCondition<ContractBatchEntity> condition = new QueryCondition<ContractBatchEntity>();
			condition.addCondition("contractId", contractNo);// 合同号
			condition.addCondition("rfidNo", rfidNo);// 备案号
			List<ContractBatchEntity> batchList = contractBatchDao.queryBacths(condition);// 批次
			List<ContractBatchModel> newBatchModelLIst = new ArrayList<ContractBatchModel>();
			if (batchList != null && batchList.size() > 0) {
				List<BatchItemModel> bmList = new ArrayList<BatchItemModel>();
				for (int i = 0; i < batchList.size(); i++) {
					ContractBatchEntity batch = batchList.get(i);
					ContractBatchModel batchModel = new ContractBatchModel();
					batchModel.setBatch(batch);
					System.err.println(batch.getBatchItems());
					List<BatchItemModel> beanList = null;
					try {
						beanList = JsonMapper
								.nonEmptyMapper()
								.getMapper()
								.readValue(
										batch.getBatchItems(),
										new TypeReference<List<BatchItemModel>>() {
										});

					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					batchModel.setBatchItems(beanList);
					newBatchModelLIst.add(batchModel);
				}
				System.err.println(JsonMapper.nonEmptyMapper().toJson(bmList));
			}
			return newBatchModelLIst;
		} catch (Exception e) {
			throw new ServiceException("获取合同信息错误", e);
		}
	}
}
