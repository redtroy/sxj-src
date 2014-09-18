package com.sxj.supervisor.service.impl.rfid.window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.rfid.window.IWindowRfidDao;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;


@Service
@Transactional
public class WindowRfidServiceImpl implements IWindowRfidService {

	@Autowired
	private IWindowRfidDao windowRfidDao;
 
	@Override
	public List<WindowRfidEntity> queryWindowRfid(WindowRfidQuery query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<WindowRfidEntity> condition = new QueryCondition<WindowRfidEntity>();
			condition.addCondition("rfidNo", query.getRfidNo());
			condition.addCondition("contractNo", query.getContractNo());
			condition.addCondition("purchaseNo", query.getPurchaseNo());
			condition.addCondition("windowType", query.getWindowType());
			condition.addCondition("rfid", query.getRfid());
			condition.addCondition("startImportDate", query.getStartImportDate());
			condition.addCondition("endImportDate", query.getEndImportDate());
			condition.addCondition("rfidState", query.getRfidState());
			condition.addCondition("progressState", query.getProgressState());
			condition.setPage(query);
			List<WindowRfidEntity> rfidList=windowRfidDao.queryWindowRfidList(condition);
			query.setPage(condition);
			return rfidList;
		} catch (Exception e) {
			throw new ServiceException("查询门窗RFID错误", e);
		}
	}

	@Override
	public void updateWindowRfid(WindowRfidEntity win) throws ServiceException {
		try {
			windowRfidDao.updateWindowRfid(win);
		} catch (Exception e) {
			throw new ServiceException("更新门窗RFID错误", e);
		}
		
	}
	
	@Override
	public List<LogModel> getRfidStateLog(String id) throws ServiceException {
		try {
			List<LogModel> logList = new ArrayList<LogModel>();
			WindowRfidEntity win=windowRfidDao.getWindowRfid(id);
			if(win.getLog()!=null){
				try {
					logList =JsonMapper.nonEmptyMapper().getMapper().readValue(win.getLog(),new TypeReference<List<LogModel>>() {
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
			}
			return logList;
		} catch (Exception e) {
			throw new ServiceException("获取stateLog错误", e);
		}
		
		
	}

	@Override
	public WindowRfidEntity getWindowRfid(String id) throws ServiceException {
		try{
			WindowRfidEntity windowRfid= windowRfidDao.getWindowRfid(id);
		return windowRfid;
	} catch (Exception e) {
		throw new ServiceException("获取门窗RFID错误", e);
	}
	}
}
