package com.sxj.supervisor.service.impl.rfid.open;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.sxj.supervisor.dao.rfid.window.IWindowRfidDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ModifyBatchEntity;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.ref.LogisticsRefEntity;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.open.Bacth;
import com.sxj.supervisor.model.open.BatchModel;
import com.sxj.supervisor.model.open.BatchNo;
import com.sxj.supervisor.model.open.ContractNo;
import com.sxj.supervisor.model.open.WinTypeModel;
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
	private IWindowRfidDao windowRfidDao;
	
	@Autowired
	private IContractDao contractDao;

	@Override
	public BatchModel getBatchByRfid(String rfid) throws ServiceException,
			SQLException {
		BatchModel batchModel = new BatchModel();
		QueryCondition<LogisticsRfidEntity> logisticsQuery = new QueryCondition<LogisticsRfidEntity>();
		List<LogisticsRfidEntity> ref = logisticsDao.queryLogisticsRfidList(logisticsQuery);
		if (ref != null && ref.size() > 0) {
			LogisticsRfidEntity le = ref.get(0);
			ContractNo contract = new ContractNo();
			contract.setContractNo(le.getContractNo());
			batchModel.setContract(contract);// 封装合同号
		}
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
		Bacth batch = new Bacth();
		if (cbatchList != null && cbatchList.size() > 0) {
			ContractBatchEntity cbe = cbatchList.get(0);
			BatchNo BatchNo = new BatchNo();
			BatchNo.setBatchNo(cbe.getBatchNo());
			batch.setBatch(BatchNo);
			List<BatchItemModel> batchModelList = this.jsonChangeList(cbe
					.getBatchItems());
			batch.setBatchItems(batchModelList);
		}
		if (modifyBatch != null && modifyBatch.size() > 0) {
			ModifyBatchEntity modiy = modifyBatch.get(0);
			Bacth bacth = new Bacth();
			BatchNo BatchNo = new BatchNo();
			BatchNo.setBatchNo(modiy.getBatchNo());
			bacth.setBatch(BatchNo);
			List<BatchItemModel> batchModelList = this.jsonChangeList(modiy
					.getBatchItems());
			batch.setBatchItems(batchModelList);
		}
		if (batchList != null && batchList.size() > 0) {
			ReplenishBatchEntity rbe = batchList.get(0);
			Bacth bacth = new Bacth();
			BatchNo BatchNo = new BatchNo();
			BatchNo.setBatchNo(rbe.getBatchNo());
			bacth.setBatch(BatchNo);
			List<BatchItemModel> batchModelList = this.jsonChangeList(rbe
					.getBatchItems());
			batch.setBatchItems(batchModelList);
		}
		batchModel.setBatchList(batch);
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
	public WinTypeModel getWinTypeByRfid(String rfid) throws ServiceException, SQLException{
		QueryCondition<WindowRfidEntity> query = new QueryCondition<WindowRfidEntity>();
		query.addCondition("rfidNo", rfid);
		
		List<WindowRfidEntity> win=windowRfidDao.queryWindowRfidList(query);
		WinTypeModel wtm = new WinTypeModel();
		if(win!=null&&win.size()>0){
			WindowRfidEntity  wre=win.get(0);
			wtm.setContratcNo(wre.getContractNo());
			wtm.setRfidNo(wre.getRfidNo());
			if(wre.getWindowType()!=null){
				return null;
			}
			wtm.setWinType(wre.getWindowType().getName());
			
		}
		return wtm;
	}
	/**
	 * 获取合同地址
	 */
	@Override
	public String  getAddress(String contractNo) throws ServiceException, SQLException{
		QueryCondition<ContractEntity> query = new QueryCondition<ContractEntity>();
		query.addCondition("contractNo", contractNo);
		List<ContractEntity> ceList= contractDao.queryContract(query);
		String address="";
		if(ceList!=null&&ceList.size()>0){
			address =ceList.get(0).getEngAddress();
		}
		return address;
	}
	
	public String  shipped(String rfid) throws ServiceException, SQLException{
		QueryCondition<LogisticsRfidEntity> logisticsQuery = new QueryCondition<LogisticsRfidEntity>();
		List<LogisticsRfidEntity> ref = logisticsDao.queryLogisticsRfidList(logisticsQuery);
		if (ref != null && ref.size() > 0) {
			LogisticsRfidEntity le = ref.get(0);
			le.getLog();
		}
		return "";
	}
	
}