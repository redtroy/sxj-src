package com.sxj.supervisor.service.impl.contract;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sxj.redis.service.comet.CometServiceImpl;
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
import com.sxj.supervisor.dao.rfid.logistics.ILogisticsRfidDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ModifyBatchEntity;
import com.sxj.supervisor.entity.contract.ModifyContractEntity;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
import com.sxj.supervisor.entity.contract.ReplenishContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.ref.LogisticsRefEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.apply.PayStateEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.enu.rfid.ref.AssociationTypesEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractModifyModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.ContractReplenishModel;
import com.sxj.supervisor.model.contract.ModifyBatchModel;
import com.sxj.supervisor.model.contract.ReplenishBatchModel;
import com.sxj.supervisor.model.contract.StateLogModel;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.service.rfid.ref.ILogisticsRefService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
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

	@Autowired
	private ILogisticsRfidDao logisticsDao;
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

	@Autowired
	private ILogisticsRfidService logisticsRfidService;

	@Autowired
	private ILogisticsRefService logisticsRefService;

	@Autowired
	private IWindowRfidService windowRfidService;

	@Autowired
	private IRfidApplicationService appRfidService;

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
				if (StringUtils.isNotEmpty(record.getContractNo())
						&& record.getState().equals(RecordStateEnum.Binding)) {
					throw new ServiceException("合同已经生成,不能重复生成");
				}
				// 拼装实体
				if (record != null) {
					contract.setRecordDate(record.getAcceptDate()); // 备案时间就是受理时间?
					contract.setRecordNo(record.getRecordNo());// 备案号
					contract.setType(record.getContractType());
					contract.setImgPath(record.getImgPath());
					contract.setState(ContractStateEnum.approval);
					contract.setConfirmState(ContractSureStateEnum.noaffirm);
					contract.setCreateDate(new Date());
					String year = new SimpleDateFormat("yy", Locale.CHINESE)
							.format(Calendar.getInstance().getTime());
					String month = new SimpleDateFormat("MM", Locale.CHINESE)
							.format(Calendar.getInstance().getTime());
					contract.setDateNo("CT" + year + month);
					contract.setUseQuantity(0f);
					contractDao.addContract(contract);

					float itemQuantity = 0f;
					if (itemList != null) {
						List<ContractItemEntity> newList = new ArrayList<ContractItemEntity>();
						for (int i = 0; i < itemList.size(); i++) {
							ContractItemEntity ci = itemList.get(i);
							itemQuantity = itemQuantity + ci.getQuantity();
							if (ci.getAmount() != null && ci.getPrice() != null) {
								ci.setContractId(contract.getContractNo());
								newList.add(ci);
							}

						}
						contractItemDao.addItem(newList);// 新增条目
					}
					contract.setItemQuantity(itemQuantity);
					contractDao.updateContract(contract);
					if (contract.getContractNo() != null) {
						record.setContractNo(contract.getContractNo());
						record.setState(RecordStateEnum.Binding);
						recordDao.updateRecord(record);
					}

				}
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("新增合同出错:" + e.getMessage(), e);
		}
	}

	/**
	 * 修改合同
	 */
	@Override
	@Transactional
	public void modifyContract(ContractModel contract) throws ServiceException {
		try {
			List<ContractItemEntity> insertList = new ArrayList<ContractItemEntity>();
			Float itemQuantity = 0f;
			for (ContractItemEntity contractItemEntity : contract.getItemList()) {
				if (StringUtils.isNotEmpty(contractItemEntity.getId())) {
					ContractItemEntity ci = contractItemDao
							.getItems(contractItemEntity.getId());
					if (ci.getUpdateState() != null && ci.getUpdateState() != 0) {
						contractItemEntity.setUpdateState(1);
						contractItemEntity.setId(null);
					}

				}
				contractItemEntity.setContractId(contract.getContract()
						.getContractNo());
				itemQuantity = itemQuantity + contractItemEntity.getQuantity();
				insertList.add(contractItemEntity);
			}
			// 条目
			if (contract.getItemList() != null) {
				List<ContractItemEntity> item = contractItemDao
						.queryItems(contract.getContract().getContractNo());
				if (item != null) {
					String ids = "";
					for (ContractItemEntity contractItemEntity : item) {
						if (ids == "") {
							ids += contractItemEntity.getId();
						} else {
							ids += "," + contractItemEntity.getId();
						}
					}
					// 删除条目
					contractItemDao.deleteItems(ids.split(","));
				}

				contractItemDao.addItem(insertList);
			}

			// 主体
			if (contract.getContract() != null) {
				ContractEntity ce = contractDao.getContract(contract
						.getContract().getId());
				String[] arr = ce.getRecordNo().split(",");
				String recordNo = contract.getContract().getRecordNo();
				if (recordNo != null && recordNo.length() > 0) {
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
							re.setContractNo(contract.getContract()
									.getContractNo());
							re.setState(RecordStateEnum.Binding);
							recordDao.updateRecord(re);
						}
					}
				}
				contract.getContract().setItemQuantity(itemQuantity);
				contractDao.updateContract(contract.getContract());
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
					if (cmm.getModifyBatchList() != null) {
						for (int j = 0; j < cmm.getModifyBatchList().size(); j++) {
							ModifyBatchModel mbm = cmm.getModifyBatchList()
									.get(j);
							if (mbm.getModifyBatchItems() != null) {
								mbm.getModifyBatch().setBatchItems(
										JsonMapper.nonEmptyMapper().toJson(
												mbm.getModifyBatchItems()));
							}
							mbeList.add(mbm.getModifyBatch());
						}
						contractModifyBatchDao.updateItems(mbeList);
					}
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
		try {
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
				batchCondition.addCondition("contractId",
						contract.getContractNo());// 补损备案
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
					System.err.println(JsonMapper.nonEmptyMapper().toJson(
							bmList));
					contractModel.setBatchList(newBatchModelLIst);
				}
				// 变更信息

				String modifyRecordIds = this.recordIdArr(
						contract.getContractNo(), "1");// 获取变更备案
				if (modifyRecordIds != null && modifyRecordIds.length() > 0) {

					// 变更合同主体
					QueryCondition<ModifyBatchEntity> modifyCondition = new QueryCondition<ModifyBatchEntity>();
					modifyCondition.addCondition("recordIds",
							modifyRecordIds.trim());// 变更备案ID
					modifyCondition.addCondition("contractId",
							contract.getContractNo());
					List<ModifyContractEntity> modifyList = contractModifyDao
							.queryModify(modifyCondition);
					if (modifyList != null && modifyList.size() > 0) {
						List<ContractModifyModel> modifymodelList = new ArrayList<ContractModifyModel>();
						for (int i = 0; i < modifyList.size(); i++) {
							ContractModifyModel cmm = new ContractModifyModel();
							ModifyContractEntity modify = modifyList.get(i);
							cmm.setModifyContract(modify);
							List<ModifyItemEntity> item = contractModifyItemDao
									.queryItems(modify.getId());// 变更条目
							cmm.setModifyItemList(item);
							QueryCondition<ModifyBatchEntity> query = new QueryCondition<ModifyBatchEntity>();
							query.addCondition("modifyId", modify.getId());
							List<ModifyBatchEntity> batch = contractModifyBatchDao
									.queryBacths(query);// 变更批次
							if (batch != null && batch.size() > 0) {
								List<ModifyBatchModel> modifyBatchModelList = new ArrayList<ModifyBatchModel>();
								for (int j = 0; j < batch.size(); j++) {
									ModifyBatchModel modifyBatchModel = new ModifyBatchModel();
									ModifyBatchEntity modifyBatchEntity = batch
											.get(j);
									if (modifyBatchEntity.getBatchItems() != null
											&& modifyBatchEntity
													.getBatchItems().length() > 0) {
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
					} else {
						contractModel.setModifyList(null);
					}

				}
				// 补损合同
				String replenishRecordIds = this.recordIdArr(
						contract.getContractNo(), "2");// 获取变更备案
				if (replenishRecordIds != null
						&& replenishRecordIds.length() > 0) {

					QueryCondition<ReplenishContractEntity> replenishCondition = new QueryCondition<ReplenishContractEntity>();
					replenishCondition.addCondition("recordIds",
							replenishRecordIds);// 补损备案ID
					List<ReplenishContractEntity> replenishList = contractReplenishDao
							.queryReplenish(replenishCondition);
					List<ContractReplenishModel> crmList = new ArrayList<ContractReplenishModel>();
					for (int i = 0; i < replenishList.size(); i++) {
						ContractReplenishModel contractReplenishModel = new ContractReplenishModel();
						ReplenishContractEntity replenishEntity = replenishList
								.get(i);
						contractReplenishModel
								.setReplenishContract(replenishEntity);
						QueryCondition<ReplenishBatchEntity> query = new QueryCondition<ReplenishBatchEntity>();
						query.addCondition("recordIds", replenishEntity.getId());
						List<ReplenishBatchEntity> replenishBatchList = contractReplenishBatchDao
								.queryReplenishBatch(query);
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
								ReplenishBatchModelList
										.add(replenishBatchModel);
								contractReplenishModel
										.setBatchItems(ReplenishBatchModelList);

							}
							crmList.add(contractReplenishModel);
						}

					}
					contractModel.setReplenishList(crmList);
				} else {
					contractModel.setReplenishList(null);
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
		qc.addCondition("contractNo", contractNo);// 合同号
		qc.addCondition("recordType", type);// 备案状态
		List<RecordEntity> record = recordDao.queryRecord(qc);
		String recordIds = "";
		if (record != null && record.size() > 0) {
			for (Iterator<RecordEntity> iterator = record.iterator(); iterator
					.hasNext();) {
				RecordEntity recordEntity = (RecordEntity) iterator.next();
				recordIds += "'" + recordEntity.getRecordNo() + "',";
			}
			recordIds = recordIds.substring(0, recordIds.length() - 1);
			return recordIds;
		} else {
			return "";
		}

	}

	/**
	 * 查询合同列表
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ContractModel> queryContracts(ContractQuery query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<ContractEntity> condition = new QueryCondition<ContractEntity>();
			condition.addCondition("keyword", query.getKeyword());
			condition.addCondition("contractNo", query.getContractNo());// 合同号
			condition.addCondition("recordNo", query.getRecordNo());// 备案号
			condition.addCondition("memberId", query.getMemberId());// 签订会员ＩＤ
			condition.addCondition("memberName", query.getMemberName());// 签订会员名称
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
			condition.addCondition("memberIdB", query.getMemberIdB());// 签订会员ＩＤ
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
	public void changeContract(String recordId, String contractId,
			ContractModifyModel model, String recordNo,
			List<ContractItemEntity> itemList, String contractIds,
			String changeIds) throws ServiceException {
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

						for (ModifyBatchModel modifyBatchEntity : mbmList) {
							ModifyBatchEntity mbe = modifyBatchEntity
									.getModifyBatch();
							String json = JsonMapper.nonEmptyMapper().toJson(
									modifyBatchEntity.getModifyBatchItems());
							mbe.setBatchItems(json);
							mbe.setReplenishState(0);
							mbe.setModifyId(mec.getId());
							mbeList.add(mbe);
						}
						contractModifyBatchDao.addBatchs(mbeList);
					}

				}
			}
			RecordEntity re = new RecordEntity();
			re.setId(recordId);
			re.setState(RecordStateEnum.change);
			recordDao.updateRecord(re);
			// 更新变更信息
			if (StringUtils.isNotEmpty(contractIds)) {
				contractIds = contractIds
						.substring(0, contractIds.length() - 1);
				String[] contractIdArr = contractIds.split(",");
				List<ContractItemEntity> cilist = new ArrayList<ContractItemEntity>();
				for (String string : contractIdArr) {
					ContractItemEntity ci = new ContractItemEntity();
					ci.setId(string);
					ci.setUpdateState(1);
					cilist.add(ci);
				}
				contractItemDao.updateItem(cilist);
			}
			if (StringUtils.isNotEmpty(changeIds)) {
				changeIds = changeIds.substring(0, changeIds.length() - 1);
				String[] changeIdsArr = changeIds.split(",");
				List<ModifyItemEntity> milist = new ArrayList<ModifyItemEntity>();
				for (String string : changeIdsArr) {
					ModifyItemEntity ci = new ModifyItemEntity();
					ci.setId(string);
					ci.setUpdateState(1);
					milist.add(ci);
				}
				contractModifyItemDao.updateItems(milist);
			}
		} catch (Exception e) {
			throw new ServiceException("变更合同信息错误", e);
		}
	}

	/**
	 * 补损合同
	 */
	@Override
	@Transactional
	public void suppContract(String recordId, String contractId,
			List<ReplenishBatchModel> batchList,
			ReplenishContractEntity replenishContract) throws ServiceException {
		try {
			if (replenishContract != null) {
				RecordEntity record = recordDao.getRecord(recordId);
				if (record.getRfidNo() != null) {
					// 更新补损状态
					String[] rfidNoArr = record.getRfidNo().split(",");
					for (String rfidNo : rfidNoArr) {
						ContractBatchEntity batch = contractBatchDao
								.getBacthsByRfid(rfidNo);
						if (batch != null) {
							if (batch.getType() == 1) {
								ContractBatchEntity cb = new ContractBatchEntity();
								cb.setId(batch.getId());
								cb.setReplenishState(1);
								contractBatchDao.updateBatch(batch);
							} else if (batch.getType() == 2) {
								ModifyBatchEntity mb = new ModifyBatchEntity();
								mb.setId(batch.getId());
								mb.setReplenishState(1);
								contractModifyBatchDao.updateBatch(mb);
							} else if (batch.getType() == 3) {
								ReplenishBatchEntity rb = new ReplenishBatchEntity();
								rb.setId(batch.getId());
								rb.setReplenishState(1);
								contractReplenishBatchDao.updateBatch(rb);
							}
						}
					}
					contractReplenishDao.addReplenish(replenishContract);
					if (replenishContract.getId() != null) {
						// 补损批次
						if (batchList != null) {
							List<ReplenishBatchEntity> list = new ArrayList<ReplenishBatchEntity>();
							for (ReplenishBatchModel replenishBatchModel : batchList) {
								String json = JsonMapper.nonEmptyMapper()
										.toJson(replenishBatchModel
												.getReplenishBatchItems());
								ReplenishBatchEntity rb = replenishBatchModel
										.getReplenishBatch();
								rb.setBatchItems(json);
								rb.setReplenishState(0);
								rb.setReplenishId(replenishContract.getId());
								rb.setNoType(contractId + "-");
								list.add(rb);
							}
							contractReplenishBatchDao.addReplenishBatch(list);
						}

					}
					RecordEntity re = new RecordEntity();
					re.setId(recordId);
					re.setState(RecordStateEnum.supplement);
					recordDao.updateRecord(re);
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
	@Transactional
	public void modifyCheckState(String contractId, ContractStateEnum state)
			throws ServiceException {
		try {
			ContractEntity ce = new ContractEntity();
			if (contractId != null) {
				ce.setId(contractId);
				ce.setState(state);
				ce.setConfirmState(ContractSureStateEnum.noaffirm);
				contractDao.updateContract(ce);
			}
			ContractEntity centity = contractDao.getContract(contractId);
			// 生成RFID申请单
			if (centity.getType().equals(ContractTypeEnum.bidding)) {
				RfidApplicationEntity app = new RfidApplicationEntity();
				app.setDateNo(DateTimeUtils.getTime("yyMM"));
				app.setMemberNo(centity.getMemberIdB());
				app.setMemberName(centity.getMemberNameB());
				app.setRfidType(RfidTypeEnum.door);
				app.setContractNo(centity.getContractNo());
				int count = (int) (centity.getItemQuantity() + 100);
				app.setCount(new Long(count));
				app.setApplyDate(new Date());
				app.setPayState(PayStateEnum.non_payment);
				app.setReceiptState(ReceiptStateEnum.shipments);
				app.setHasNumber(0l);
				appRfidService.addApp(app);
			}
			if (centity.getRecordNo() != null) {
				RecordQuery recordQuery = new RecordQuery();
				recordQuery.setContractNo(centity.getContractNo());
				recordQuery.setSort("DESC");
				recordQuery.setSortColumn("APPLY_DATE");
				List<RecordEntity> recordList = recordService
						.queryRecord(recordQuery);
				// 变更该合同所有备案状态
				if (recordList != null) {
					for (RecordEntity recordEntity : recordList) {
						recordEntity
								.setConfirmState(RecordConfirmStateEnum.unconfirmed);
						if (recordEntity.getFlag().getId() == 0) {
							if (recordEntity.getAcceptState() == null
									|| recordEntity.getAcceptState() == 0) {
								recordEntity.setAcceptDate(new Date());// 受理时间
								recordEntity.setAcceptState(1);
							}
						} else {
							recordEntity.setAcceptDate(new Date());
						}
						recordDao.updateRecord(recordEntity);
					}
					RecordEntity record = recordList.get(0);

					String key_a = MessageChannel.WEBSITE_RECORD_MESSAGE
							+ record.getMemberIdA();
					// List<String> messageList = CometServiceImpl.get(key_a);
					// if (messageList == null || messageList.size() == 0) {
					// messageList = new ArrayList<String>();
					// }
					String msgName = "";
					if (record.getType().getId() == 0) {
						if (record.getContractType().getId() == 0) {
							msgName = "开发商";
						}
					} else if (record.getType().getId() == 1) {
						msgName = "变更";
					} else if (record.getType().getId() == 2) {
						msgName = "补损";
					}
					String message = record.getId() + "," + msgName + ","
							+ centity.getContractNo() + ','
							+ record.getMemberIdA() + ','
							+ record.getContractType().getId();
					// messageList.add(message);
					CometServiceImpl.add(key_a, message);
					MessageChannel.initTopic().publish(key_a);
					// HierarchicalCacheManager.set(2, "comet_message",
					// "record_push_message_" + record.getMemberIdA(),
					// messageList);
					// 乙方
					String key_b = MessageChannel.WEBSITE_RECORD_MESSAGE
							+ record.getMemberIdB();
					// List<String> messageListB = null;
					// Object cacheB = HierarchicalCacheManager.get(2,
					// "comet_message",
					// "record_push_message_" + record.getMemberIdB());
					// if (cacheB instanceof ArrayList) {
					// messageListB = (List<String>) cacheB;
					// } else {
					// messageListB = new ArrayList<String>();
					// }
					String msgNameB = "";
					if (record.getType().getId() == 0) {
						if (record.getContractType().getId() == 0) {
							msgNameB = "开发商";
						}
					} else if (record.getType().getId() == 1) {
						msgNameB = "变更";
					} else if (record.getType().getId() == 2) {
						msgNameB = "补损";
					}
					String messageB = record.getId() + "," + msgNameB + ","
							+ centity.getContractNo() + ','
							+ record.getMemberIdB() + ','
							+ record.getContractType().getId();
					CometServiceImpl.add(key_b, messageB);
					MessageChannel.initTopic().publish(key_b);
					// messageListB.add(messageB);
					// HierarchicalCacheManager.set(2, "comet_message",
					// "record_push_message_" + record.getMemberIdB(),
					// messageListB);
				}
			}

		} catch (Exception e) {
			throw new ServiceException("审核合同错误", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ContractModel getContractModelByContractNo(String contractNo) {
		try {
			if (StringUtils.isEmpty(contractNo)) {
				return null;
			}
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
	public List<ContractBatchModel> getContractBatch(String contractNo,
			String rfidNo) {
		try {
			QueryCondition<ContractBatchEntity> condition = new QueryCondition<ContractBatchEntity>();
			if (StringUtils.isNotEmpty(contractNo)) {
				condition.addCondition("contractId", contractNo);// 合同号
			}
			if (StringUtils.isNotEmpty(rfidNo)) {
				condition.addCondition("rfidNo", rfidNo);// RFID号
			}
			List<ContractBatchEntity> batchList = contractBatchDao
					.queryBacths(condition);// 批次
			List<ContractBatchModel> newBatchModelLIst = new ArrayList<ContractBatchModel>();
			if (batchList != null && batchList.size() > 0) {
				for (int i = 0; i < batchList.size(); i++) {
					ContractBatchEntity batch = batchList.get(i);
					ContractBatchModel batchModel = new ContractBatchModel();
					batchModel.setBatch(batch);
					List<BatchItemModel> beanList = null;
					beanList = JsonMapper
							.nonEmptyMapper()
							.getMapper()
							.readValue(batch.getBatchItems(),
									new TypeReference<List<BatchItemModel>>() {
									});
					batchModel.setBatchItems(beanList);
					newBatchModelLIst.add(batchModel);
				}
			}
			return newBatchModelLIst;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("获取合同信息错误", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ModifyBatchModel> getContractModifyBatch(String contractNo,
			String rfid) {
		try {
			QueryCondition<ModifyBatchEntity> condition = new QueryCondition<ModifyBatchEntity>();
			condition.addCondition("contractId", contractNo);// 合同号
			condition.addCondition("rfidNo", rfid);// 备案号
			List<ModifyBatchEntity> batchList = contractModifyBatchDao
					.queryBacths(condition);
			List<ModifyBatchModel> newBatchModelLIst = new ArrayList<ModifyBatchModel>();
			if (batchList != null && batchList.size() > 0) {
				for (int i = 0; i < batchList.size(); i++) {
					ModifyBatchEntity batch = batchList.get(i);
					ModifyBatchModel batchModel = new ModifyBatchModel();
					batchModel.setModifyBatch(batch);
					List<BatchItemModel> beanList = null;
					beanList = JsonMapper
							.nonEmptyMapper()
							.getMapper()
							.readValue(batch.getBatchItems(),
									new TypeReference<List<BatchItemModel>>() {
									});
					batchModel.setModifyBatchItems(beanList);
					newBatchModelLIst.add(batchModel);
				}
			}
			return newBatchModelLIst;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("获取修改批次信息错误", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReplenishBatchModel> getContractReplenishBatch(
			String contractNo, String rfid) {
		try {
			QueryCondition<ReplenishBatchEntity> condition = new QueryCondition<ReplenishBatchEntity>();
			condition.addCondition("contractId", contractNo);// 合同号
			condition.addCondition("rfidNo", rfid);// 备案号
			List<ReplenishBatchEntity> batchList = contractReplenishBatchDao
					.queryReplenishBatch(condition);
			List<ReplenishBatchModel> newBatchModelLIst = new ArrayList<ReplenishBatchModel>();
			if (batchList != null && batchList.size() > 0) {
				for (int i = 0; i < batchList.size(); i++) {
					ReplenishBatchEntity batch = batchList.get(i);
					ReplenishBatchModel batchModel = new ReplenishBatchModel();
					batchModel.setReplenishBatch(batch);
					List<BatchItemModel> beanList = null;
					beanList = JsonMapper
							.nonEmptyMapper()
							.getMapper()
							.readValue(batch.getBatchItems(),
									new TypeReference<List<BatchItemModel>>() {
									});
					batchModel.setReplenishBatchItems(beanList);
					newBatchModelLIst.add(batchModel);
				}
			}
			return newBatchModelLIst;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("获取补损批次信息错误", e);
		}
	}

	@Override
	@Transactional
	public void modifyBatch(ContractBatchModel model) throws ServiceException {
		try {
			String batchItems = JsonMapper.nonEmptyMapper().toJson(
					model.getBatchItems());
			ContractBatchEntity batch = model.getBatch();
			batch.setBatchItems(batchItems);
			List<ContractBatchEntity> list = new ArrayList<ContractBatchEntity>();
			list.add(batch);
			contractBatchDao.updateBatchs(list);
		} catch (Exception e) {
			throw new ServiceException("批次修改错误", e);
		}

	}

	/**
	 * 启用rfid(新增批次)
	 */
	@Override
	@Transactional
	public void addBatch(ContractBatchModel model, String id,
			MemberEntity member) throws ServiceException {
		try {
			if (model == null) {
				throw new ServiceException("系统错误");
			}
			ContractBatchEntity batch = model.getBatch();
			// 判断是否能启用
			int oldBatchCount = 0;
			ContractModel contract = getContractModelByContractNo(batch
					.getContractId());
			if (contract == null) {
				throw new ServiceException("系统错误");
			}
			if (contract.getBatchList() != null) {
				oldBatchCount = contract.getBatchList().size();
			}
			if (contract.getContract().getBatchCount() <= oldBatchCount) {
				throw new ServiceException("本合同批次已经全部启用完毕！");
			}

			// 执行启用
			String batchItems = null;
			if (model.getBatchItems() != null) {
				batchItems = JsonMapper.nonEmptyMapper().toJson(
						model.getBatchItems());
			}
			batch.setBatchItems(batchItems);
			List<ContractBatchEntity> list = new ArrayList<ContractBatchEntity>();
			list.add(batch);
			contractBatchDao.addBatchs(list);

			LogisticsRfidEntity logistics = new LogisticsRfidEntity();
			logistics.setId(id);
			logistics.setContractNo(batch.getContractId());
			logistics.setRfidState(RfidStateEnum.used);
			logistics.setBatchNo(batch.getBatchNo());
			logisticsRfidService.updateLogistics(logistics);

			// 申请关联
			LogisticsRefEntity ref = new LogisticsRefEntity();
			ref.setRfidNo(batch.getRfidNo());
			ref.setMemberNo(member.getMemberNo());
			ref.setMemberName(member.getName());
			if (member.getType().getId() == 1) {
				ref.setRfidType(RfidTypeEnum.glass);
			} else if (member.getType().getId() == 2) {
				ref.setRfidType(RfidTypeEnum.extrusions);
			}
			ref.setType(AssociationTypesEnum.APPLY);
			ref.setBatchNo(batch.getBatchNo());
			ref.setApplyDate(new Date());
			ref.setContractNo(batch.getContractId());
			ref.setState(AuditStateEnum.approval);
			logisticsRefService.add(ref);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("启用rfid错误", e);
		}

	}

	/**
	 * 补损rfid
	 */
	@Override
	@Transactional
	public void updateRfidLoss(String rfidNo, String contractNo,
			MemberEntity member, String newRfid) throws ServiceException {
		try {
			ContractBatchModel batchModel = getBatchByRfid(rfidNo);
			if (batchModel == null) {
				throw new ServiceException("该RFID没有对应的批次！");
			}
			ContractBatchEntity batchEntity = batchModel.getBatch();
			if (batchEntity == null) {
				throw new ServiceException("该RFID没有对应的批次！");
			}
			List<BatchItemModel> batchItems = batchModel.getBatchItems();
			if (batchItems == null || batchItems.size() == 0) {
				throw new ServiceException("该RFID没有对应的批次条目信息！");
			}

			if (batchModel.getBatch().getType() == 1) {
				batchEntity.setRfidNo(newRfid);
				contractBatchDao.updateBatch(batchEntity);
			} else if (batchModel.getBatch().getType() == 2) {
				ModifyBatchEntity modifyBatch = new ModifyBatchEntity();
				modifyBatch.setId(batchEntity.getId());
				modifyBatch.setRfidNo(newRfid);
				contractModifyBatchDao.updateBatch(modifyBatch);
			} else if (batchModel.getBatch().getType() == 3) {
				ReplenishBatchEntity replenishBatch = new ReplenishBatchEntity();
				replenishBatch.setId(batchEntity.getId());
				replenishBatch.setNewRfidNo(newRfid);
				contractReplenishBatchDao.updateBatch(replenishBatch);
			}
			// 更新旧的RFID
			LogisticsRfidEntity logistics = logisticsRfidService
					.getLogisticsByNo(rfidNo);
			if (logistics.getRfidState().equals(RfidStateEnum.damaged)) {
				throw new ServiceException("此RFID已经被补损，不能再次补损");
			}
			logistics.setRfidState(RfidStateEnum.damaged);
			logistics.setReplenishNo(newRfid);
			logisticsRfidService.updateLogistics(logistics);
			// 更新新的RFID
			LogisticsRfidEntity newLogisRfid = logisticsRfidService
					.getLogisticsByNo(newRfid);
			if (newLogisRfid == null) {
				throw new ServiceException("新RFID信息不存在");
			}
			if (!newLogisRfid.getRfidState().equals(RfidStateEnum.unused)) {
				throw new ServiceException("新RFID状态不是未使用状态，不能用于补损");
			}
			newLogisRfid.setContractNo(contractNo);
			newLogisRfid.setRfidState(RfidStateEnum.used);
			newLogisRfid.setBatchNo(batchModel.getBatch().getBatchNo());
			logisticsRfidService.updateLogistics(newLogisRfid);

			// 申请关联
			LogisticsRefEntity ref = new LogisticsRefEntity();
			ref.setRfidNo(newRfid);
			ref.setMemberNo(member.getMemberNo());
			ref.setMemberName(member.getName());
			if (member.getType().equals(MemberTypeEnum.glassFactory)) {
				ref.setRfidType(RfidTypeEnum.glass);
			} else if (member.getType().equals(MemberTypeEnum.genresFactory)) {
				ref.setRfidType(RfidTypeEnum.extrusions);
			}
			ref.setType(AssociationTypesEnum.RFID_ADD);
			ref.setBatchNo(batchModel.getBatch().getBatchNo());
			ref.setApplyDate(new Date());
			ref.setContractNo(contractNo);
			ref.setState(AuditStateEnum.noapproval);
			ref.setReplenishRfid(rfidNo);
			logisticsRefService.add(ref);

		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("RFID标签补损错误", e);
		}
	}

	/**
	 * 采购合同补损
	 */
	@Override
	public void updateContractLoss(String rfidNos, String contractNo,
			String recordNo, MemberEntity member, String newRfid)
			throws ServiceException {
		try {
			if (StringUtils.isEmpty(rfidNos)) {
				throw new ServiceException("rfid编号不能为空");
			}
			String[] rfidList = rfidNos.split(",");
			// 更新旧的RFID
			// for (int i = 0; i < rfidList.length; i++) {
			// if (StringUtils.isEmpty(rfidList[i])) {
			// continue;
			// }
			// LogisticsRfidEntity logistics = logisticsRfidService
			// .getLogisticsByNo(rfidList[i]);
			// if (logistics.getRfidState().equals(RfidStateEnum.damaged)) {
			// throw new ServiceException("此RFID已经被补损，不能再次补损");
			// }
			// logistics.setReplenishNo(newRfid);
			// logisticsRfidService.updateLogistics(logistics);
			// }
			// 更新新的RFID
			LogisticsRfidEntity newLogisRfid = logisticsRfidService
					.getLogisticsByNo(newRfid);
			if (newLogisRfid == null) {
				throw new ServiceException("新RFID信息不存在");
			}
			if (!newLogisRfid.getRfidState().equals(RfidStateEnum.unused)) {
				throw new ServiceException("新RFID状态不是未使用状态，不能用于补损");
			}
			newLogisRfid.setContractNo(contractNo);
			newLogisRfid.setRfidState(RfidStateEnum.used);
			// newLogisRfid.setBatchNo(batchModel.getBatch().getBatchNo());
			logisticsRfidService.updateLogistics(newLogisRfid);

			// 申请关联
			LogisticsRefEntity ref = new LogisticsRefEntity();
			ref.setRfidNo(newRfid);
			ref.setMemberNo(member.getMemberNo());
			ref.setMemberName(member.getName());
			if (member.getType().equals(MemberTypeEnum.glassFactory)) {
				ref.setRfidType(RfidTypeEnum.glass);
			} else if (member.getType().equals(MemberTypeEnum.genresFactory)) {
				ref.setRfidType(RfidTypeEnum.extrusions);
			}
			ref.setType(AssociationTypesEnum.CONTRACTOR_ADD);
			// ref.setBatchNo(batchModel.getBatch().getBatchNo());
			ref.setApplyDate(new Date());
			ref.setContractNo(contractNo);
			ref.setState(AuditStateEnum.noapproval);
			ref.setReplenishRfid(rfidNos);
			logisticsRefService.add(ref);
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("采购合同补损错误", e);
		}

	}

	@Override
	@Transactional
	public String getReplenish(String contractNo) {
		QueryCondition<ReplenishContractEntity> query = new QueryCondition<ReplenishContractEntity>();
		query.addCondition("contractId", contractNo);
		List<ReplenishContractEntity> replenishList = contractReplenishDao
				.queryReplenish(query);
		if (replenishList.size() > 0 && replenishList != null) {
			ReplenishContractEntity replenish = replenishList.get(0);
			if (replenish.getId() != null) {
				QueryCondition<ReplenishBatchEntity> query2 = new QueryCondition<ReplenishBatchEntity>();
				query2.addCondition("recordIds", replenish.getId());
				List<ReplenishBatchEntity> replenisBatch = contractReplenishBatchDao
						.queryReplenishBatch(query2);
				return replenisBatch.get(0).getNewRfidNo();
			}
		}
		return null;
	}

	@Override
	@Transactional
	public int getContractByZhaobiaoContractNo(String contractNo,
			String memberId) {
		try {
			ContractQuery query = new ContractQuery();
			query.setContractNo(contractNo);
			query.setMemberIdB(memberId);
			query.setContractType("0");
			List<ContractModel> res = queryContracts(query);
			return res.size();
		} catch (Exception e) {
			throw new ServiceException("获取合同信息错误", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContractEntity> getContractByRefContractNo(String refContractNo)
			throws ServiceException {
		try {
			ContractQuery query = new ContractQuery();
			query.setRefContractNo(refContractNo);
			List<ContractModel> list = queryContracts(query);
			List<ContractEntity> ContractList = new ArrayList<ContractEntity>();
			for (ContractModel contractModel : list) {
				ContractList.add(contractModel.getContract());
			}
			return ContractList;
		} catch (Exception e) {
			throw new ServiceException("获取合同信息错误", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ContractItemEntity> getContractItem(String contractNo)
			throws ServiceException {
		try {
			List<ContractItemEntity> itemList = contractItemDao
					.queryItems(contractNo);// 产品条目
			return itemList;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("获取合同信息错误", e);
		}
	}

	@Override
	@Transactional
	public void startWindowRfid(Integer startNum, String refContractNo,
			String minRfid, String maxRfid, String gRfid, String lRfid,
			WindowTypeEnum windowType) throws ServiceException {
		try {
			ContractModel refContract = getContractModelByContractNo(refContractNo);
			if (refContract == null) {
				throw new ServiceException("招标合同不存在");
			}
			List<ContractItemEntity> items = refContract.getItemList();
			if (items == null || items.size() == 0) {
				throw new ServiceException("招标合同条目不存在");
			}
			float itemQuantity = refContract.getContract().getItemQuantity();
			float useQuantity = refContract.getContract().getUseQuantity();
			long count = (long) itemQuantity;
			long useCount = (long) useQuantity;
			windowRfidService.startWindowRfid(count, useCount, refContractNo,
					minRfid, maxRfid, gRfid, lRfid, windowType);
			Map<String, Float> map = new HashMap<>();
			map.put("usequantity", (float) startNum + useQuantity);
			map.put("oldUseQuantity", useQuantity);
			int index = contractDao.updateContractRfid(map);
			if (index <= 0) {
				throw new ServiceException("请求超时，请重新启用");
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage(), e);
		}

	}

	/**
	 * 跟据rfid 获取补损批次
	 */
	@Override
	@Transactional(readOnly = true)
	public ContractReplenishModel getReplenishByRfid(String rfid) {
		try {
			if (StringUtils.isEmpty(rfid)) {
				return null;
			}
			ContractReplenishModel cModel = new ContractReplenishModel();
			QueryCondition<ReplenishBatchEntity> query = new QueryCondition<ReplenishBatchEntity>();
			query.addCondition("rfidNo", rfid);
			List<ReplenishBatchEntity> batchList = contractReplenishBatchDao
					.queryReplenishBatch(query);
			List<ReplenishBatchModel> replenishBatchList = new ArrayList<ReplenishBatchModel>();
			ReplenishBatchModel rbm = new ReplenishBatchModel();
			if (batchList != null && batchList.size() > 0) {
				ReplenishBatchEntity rce = batchList.get(0);
				List<BatchItemModel> bimList = new ArrayList<BatchItemModel>(0);
				rbm.setReplenishBatch(rce);
				bimList = JsonMapper
						.nonEmptyMapper()
						.getMapper()
						.readValue(rce.getBatchItems(),
								new TypeReference<List<BatchItemModel>>() {
								});
				ReplenishContractEntity rcentity = contractReplenishDao
						.getReplenish(rce.getReplenishId());
				rbm.setReplenishBatchItems(bimList);
				replenishBatchList.add(rbm);
				cModel.setReplenishContract(rcentity);
				cModel.setBatchItems(replenishBatchList);
			}
			return cModel;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage());
		}

	}

	/**
	 * 根据RFID获取批次
	 */
	@Override
	public ContractBatchModel getBatchByRfid(String rfidNo)
			throws ServiceException, SQLException {
		ContractBatchModel batchModel = new ContractBatchModel();
		ContractBatchEntity batch = contractBatchDao.getBacthsByRfid(rfidNo);
		LogisticsRfidQuery query = new LogisticsRfidQuery();
		query.setRfidNo(rfidNo);
		List<LogisticsRfidEntity> lr = logisticsRfidService
				.queryLogistics(query);
		if (batch != null) {
			if (lr != null && lr.size() > 0) {
				LogisticsRfidEntity logistics = lr.get(0);
				batch.setContractId(logistics.getContractNo());
			}
			batchModel.setBatch(batch);
			List<BatchItemModel> batchList = this.jsonChangeList(batch
					.getBatchItems());
			batchModel.setBatchItems(batchList);
		}

		return batchModel;
	}

	/**
	 * json转化list
	 * 
	 * @param json
	 * @return
	 */
	public List<BatchItemModel> jsonChangeList(String json) {
		List<BatchItemModel> bacthList = new ArrayList<BatchItemModel>();
		try {
			bacthList = JsonMapper.nonEmptyMapper().getMapper()
					.readValue(json, new TypeReference<List<BatchItemModel>>() {
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
		return bacthList;

	}

	/**
	 * 根据RFID删除批次
	 */
	@Override
	@Transactional
	public void deleteBatch(String rfidNo)throws ServiceException{
		try {
			QueryCondition<ContractBatchEntity> query = new QueryCondition<ContractBatchEntity>();
			query.addCondition("rfidNo", rfidNo);
			List<ContractBatchEntity>   batchList=contractBatchDao.queryBacths(query);
			if(batchList!=null && batchList.size()>0){
				ContractBatchEntity batch = batchList.get(0);
				if(batch.getUpdateState()==1){
					throw new ServiceException("该批次已被变更,不能删除!");
				}
				if(batch.getReplenishState()==1){
					throw new ServiceException("该批次已被补损,不能删除!");
				}
				contractBatchDao.deleteBatchs(batch.getId());
			}else{
				throw new ServiceException("找不到该批次");
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("删除批次出错", e);
		}
	}
}
