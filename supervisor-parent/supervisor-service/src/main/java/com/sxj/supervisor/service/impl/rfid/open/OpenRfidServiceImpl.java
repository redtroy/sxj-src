package com.sxj.supervisor.service.impl.rfid.open;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.contract.IContractBatchDao;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.contract.IContractModifyBatchDao;
import com.sxj.supervisor.dao.contract.IContractReplenishBatchDao;
import com.sxj.supervisor.dao.rfid.logistics.ILogisticsRfidDao;
import com.sxj.supervisor.dao.rfid.ref.ILogisticsRefDao;
import com.sxj.supervisor.dao.rfid.window.IWindowRfidDao;
import com.sxj.supervisor.dao.rfid.windowRef.IWindowRfidRefDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ModifyBatchEntity;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.ref.LogisticsRefEntity;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.entity.rfid.windowRef.WindowRefEntity;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.open.Bacth;
import com.sxj.supervisor.model.open.BatchModel;
import com.sxj.supervisor.model.open.BatchNo;
import com.sxj.supervisor.model.open.ContractNo;
import com.sxj.supervisor.model.open.WinTypeModel;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.service.rfid.open.IOpenRfidService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class OpenRfidServiceImpl implements IOpenRfidService {
	/**
	 * 批次DAO
	 */
	@Autowired
	private IContractBatchDao contractBatchDao;
	/**
	 * 变更合同批次DAO
	 */
	@Autowired
	private IContractModifyBatchDao contractModifyBatchDao;
	/**
	 * 补损合同批次
	 */
	@Autowired
	private IContractReplenishBatchDao contractReplenishBatchDao;

	@Autowired
	private ILogisticsRfidDao logisticsDao;

	@Autowired
	private ILogisticsRefDao logisticsRefDao;

	@Autowired
	private IWindowRfidDao windowRfidDao;

	@Autowired
	private IContractDao contractDao;

	@Autowired
	private IWindowRfidRefDao windowRfidRefDao;

	@Override
	public BatchModel getBatchByRfid(String rfid) throws ServiceException,
			SQLException {
		BatchModel batchModel = new BatchModel();
		Bacth batch = new Bacth();
		QueryCondition<LogisticsRfidEntity> logisticsQuery = new QueryCondition<LogisticsRfidEntity>();
		logisticsQuery.addCondition("rfidNo", rfid);
		List<LogisticsRfidEntity> ref = logisticsDao
				.queryLogisticsRfidList(logisticsQuery);
		QueryCondition<LogisticsRefEntity> logisticsRefQuery = new QueryCondition<LogisticsRefEntity>();
		logisticsRefQuery.addCondition("rfidNo", rfid);
		List<LogisticsRefEntity> logisticsRef = logisticsRefDao
				.queryList(logisticsRefQuery);
		if (logisticsRef != null && logisticsRef.size() > 0) {
			LogisticsRefEntity lRef = logisticsRef.get(0);
			if (lRef.getState().getId() == 1) {//
				if (ref != null && ref.size() > 0) {
					LogisticsRfidEntity le = ref.get(0);
					ContractNo contract = new ContractNo();
					contract.setContractNo(le.getContractNo());
					batchModel.setContract(contract);// 封装合同号
					if (le.getProgressState().getId() == 0) {

						QueryCondition<ContractBatchEntity> query = new QueryCondition<ContractBatchEntity>();
						query.addCondition("rfidNo", rfid);
						query.addCondition("state", 1);// 是否已变更
						List<ContractBatchEntity> cbatchList = contractBatchDao
								.queryBacths(query);// 合同批次
						QueryCondition<ModifyBatchEntity> modifyQuery = new QueryCondition<ModifyBatchEntity>();
						modifyQuery.addCondition("rfidNo", rfid);
						modifyQuery.addCondition("state", 1);// 是否已变更
						List<ModifyBatchEntity> modifyBatch = contractModifyBatchDao
								.queryBacths(modifyQuery);// 变更批次
						QueryCondition<ReplenishBatchEntity> replenishQuery = new QueryCondition<ReplenishBatchEntity>();
						replenishQuery.addCondition("newRfidNo", rfid);
						// 补损批次
						List<ReplenishBatchEntity> batchList = contractReplenishBatchDao
								.queryReplenishBatch(replenishQuery);

						if (cbatchList != null && cbatchList.size() > 0) {
							ContractBatchEntity cbe = cbatchList.get(0);
							BatchNo BatchNo = new BatchNo();
							BatchNo.setBatchNo(cbe.getBatchNo());
							batch.setBatch(BatchNo);
							List<BatchItemModel> batchModelList = this
									.jsonChangeList(cbe.getBatchItems());
							batch.setBatchItems(batchModelList);
						}
						if (modifyBatch != null && modifyBatch.size() > 0) {
							ModifyBatchEntity modiy = modifyBatch.get(0);
							Bacth bacth = new Bacth();
							BatchNo BatchNo = new BatchNo();
							BatchNo.setBatchNo(modiy.getBatchNo());
							bacth.setBatch(BatchNo);
							List<BatchItemModel> batchModelList = this
									.jsonChangeList(modiy.getBatchItems());
							batch.setBatchItems(batchModelList);
						}
						if (batchList != null && batchList.size() > 0) {
							ReplenishBatchEntity rbe = batchList.get(0);
							Bacth bacth = new Bacth();
							BatchNo BatchNo = new BatchNo();
							BatchNo.setBatchNo(rbe.getBatchNo());
							bacth.setBatch(BatchNo);
							List<BatchItemModel> batchModelList = this
									.jsonChangeList(rbe.getBatchItems());
							batch.setBatchItems(batchModelList);
						}
						if (batch.getBatchItems() != null) {
							batch.setState("1");
						} else {
							batch.setState("2");
						}
					} else {
						batch.setState("4");
					}

				}
			} else {
				batch.setState("3");
			}
			batchModel.setBatchList(batch);
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
	 * 获取门窗
	 */
	@Override
	public WinTypeModel getWinTypeByRfid(String rfid) throws ServiceException,
			SQLException {
		QueryCondition<WindowRfidEntity> query = new QueryCondition<WindowRfidEntity>();
		query.addCondition("rfidNo", rfid);

		List<WindowRfidEntity> win = windowRfidDao.queryWindowRfidList(query);
		QueryCondition<WindowRefEntity> refQuery = new QueryCondition<WindowRefEntity>();
		refQuery.addCondition("rfidNo", rfid);
		List<WindowRefEntity> winRef = windowRfidRefDao
				.queryWindowRfidRefList(refQuery);

		WinTypeModel wtm = new WinTypeModel();
		if (winRef != null && winRef.size() > 0) {
			WindowRefEntity windowRef = winRef.get(0);
			if (windowRef.getState().getId() == 1) {
				if (win != null && win.size() > 0) {
					WindowRfidEntity wre = win.get(0);
					wtm.setContratcNo(wre.getContractNo());
					wtm.setRfidNo(wre.getRfidNo());
					if (wre.getWindowType() != null) {
						wtm.setWinType(wre.getWindowType().getName());
					}
					wtm.setState("1");// 成功
				} else {
					wtm.setState("2");// 未启用
				}
			} else {
				wtm.setState("3");// 未审核
			}
		}else {
			wtm.setState("2");// 未启用
		}
		return wtm;
	}

	/**
	 * 获取合同地址
	 */
	@Override
	public String getAddress(String contractNo) throws ServiceException,
			SQLException {
		QueryCondition<ContractEntity> query = new QueryCondition<ContractEntity>();
		query.addCondition("contractType", "0");
		query.addCondition("contractNo", contractNo);
		List<ContractEntity> ceList = contractDao.queryContract(query);
		String address = "";
		if (ceList != null && ceList.size() > 0) {
			address = ceList.get(0).getEngAddress();
		}
		return address;
	}

	/**
	 * 发货
	 */
	@Override
	@Transactional
	public int shipped(String rfid) throws ServiceException, SQLException,
			JsonParseException, JsonMappingException, IOException {
		QueryCondition<LogisticsRfidEntity> logisticsQuery = new QueryCondition<LogisticsRfidEntity>();
		logisticsQuery.addCondition("rfidNo", rfid);
		List<LogisticsRfidEntity> ref = logisticsDao
				.queryLogisticsRfidList(logisticsQuery);
		List<LogModel> logList = new ArrayList<LogModel>();
		if (ref != null && ref.size() > 0) {
			LogisticsRfidEntity le = ref.get(0);
			logList = JsonMapper
					.nonEmptyMapper()
					.getMapper()
					.readValue(le.getLog(),
							new TypeReference<List<LogModel>>() {
							});
			if (le.getProgressState().getId() == 0) {
				le.setProgressState(LabelStateEnum.shipped);
				LogModel log = new LogModel();
				log.setState(LabelStateEnum.shipped.getName());
				log.setDate(DateFormatUtils.format(new Date(),
						"yyyy-MM-dd HH:mm:ss"));
				logList.add(log);
				String josn = JsonMapper.nonEmptyMapper().toJson(logList);
				le.setLog(josn);
				logisticsDao.updateLogisticsRfid(le);
				return 1;
			}

		}
		return 0;
	}

	/**
	 * 验收
	 */
	@Override
	@Transactional
	public int accepting(String rfid) throws ServiceException, SQLException,
			JsonParseException, JsonMappingException, IOException {
		QueryCondition<LogisticsRfidEntity> logisticsQuery = new QueryCondition<LogisticsRfidEntity>();
		logisticsQuery.addCondition("rfidNo", rfid);
		List<LogisticsRfidEntity> ref = logisticsDao
				.queryLogisticsRfidList(logisticsQuery);
		List<LogModel> logList = new ArrayList<LogModel>();
		if (ref != null && ref.size() > 0) {
			LogisticsRfidEntity le = ref.get(0);
			logList = JsonMapper
					.nonEmptyMapper()
					.getMapper()
					.readValue(le.getLog(),
							new TypeReference<List<LogModel>>() {
							});
			if (le.getProgressState().getId() == 3) {
				le.setProgressState(LabelStateEnum.hasQuality);
				LogModel log = new LogModel();
				log.setState(LabelStateEnum.hasQuality.getName());
				log.setDate(DateFormatUtils.format(new Date(),
						"yyyy-MM-dd HH:mm:ss"));
				logList.add(log);
				String josn = JsonMapper.nonEmptyMapper().toJson(logList);
				le.setLog(josn);
				logisticsDao.updateLogisticsRfid(le);
				return 1;
			}

		}
		return 0;
	}
}
