package com.sxj.supervisor.service.impl.rfid.window;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.rfid.window.IWindowRfidDao;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.entity.rfid.windowRef.WindowRefEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.enu.rfid.windowRef.LinkStateEnum;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.supervisor.service.rfid.windowRef.IWindowRfidRefService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.CustomDecimal;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class WindowRfidServiceImpl implements IWindowRfidService {

	@Autowired
	private IWindowRfidDao windowRfidDao;

	@Autowired
	private IWindowRfidRefService winRefService;

	@Override
	@Transactional(readOnly = true)
	public List<WindowRfidEntity> queryWindowRfid(WindowRfidQuery query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<WindowRfidEntity> condition = new QueryCondition<WindowRfidEntity>();
			condition.addCondition("applyNo", query.getApplyNo());
			condition.addCondition("rfidNo", query.getRfidNo());
			condition.addCondition("minRfidNo", query.getMinRfidNo());
			condition.addCondition("maxRfidNo", query.getMaxRfidNo());
			condition.addCondition("contractNo", query.getContractNo());
			condition.addCondition("purchaseNo", query.getPurchaseNo());
			condition.addCondition("windowType", query.getWindowType());
			condition.addCondition("memberNo", query.getMemberNo());
			condition.addCondition("rfid", query.getRfid());
			condition.addCondition("startImportDate",
					query.getStartImportDate());
			condition.addCondition("endImportDate", query.getEndImportDate());
			condition.addCondition("rfidState", query.getRfidState());
			condition.addCondition("progressState", query.getProgressState());
			condition.addCondition("webFlag", query.getWebsiteFlag());
			condition.setPage(query);
			List<WindowRfidEntity> rfidList = windowRfidDao
					.queryWindowRfidList(condition);
			query.setPage(condition);
			return rfidList;
		} catch (Exception e) {
			throw new ServiceException("查询门窗RFID错误", e);
		}
	}

	@Override
	@Transactional
	public void updateWindowRfid(WindowRfidEntity win) throws ServiceException {
		try {
			windowRfidDao.updateWindowRfid(win);
		} catch (Exception e) {
			throw new ServiceException("更新门窗RFID错误", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<LogModel> getRfidStateLog(String id) throws ServiceException {
		try {
			List<LogModel> logList = new ArrayList<LogModel>();
			WindowRfidEntity win = windowRfidDao.getWindowRfid(id);
			if (win.getLog() != null) {
				try {
					logList = JsonMapper
							.nonEmptyMapper()
							.getMapper()
							.readValue(win.getLog(),
									new TypeReference<List<LogModel>>() {
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
	@Transactional(readOnly = true)
	public WindowRfidEntity getWindowRfid(String id) throws ServiceException {
		try {
			WindowRfidEntity windowRfid = windowRfidDao.getWindowRfid(id);
			return windowRfid;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("获取门窗RFID错误", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public WindowRfidEntity getWindowRfidByNo(String rfidNo)
			throws ServiceException {
		try {
			if (StringUtils.isEmpty(rfidNo)) {
				return null;
			}
			WindowRfidQuery query = new WindowRfidQuery();
			query.setRfidNo(rfidNo);
			List<WindowRfidEntity> list = queryWindowRfid(query);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("获取门窗RFID错误", e);
		}
		return null;
	}

	@Override
	@Transactional
	public String[] getMaxRfidNo(String contractNo, Long count)
			throws ServiceException {
		try {
			String[] arr = new String[2];
			Long nowMax = windowRfidDao.getMaxRfidNo(contractNo);
			String minNo = CustomDecimal.getDecimalString(4, new BigDecimal(
					nowMax));
			String maxMo = CustomDecimal.getDecimalString(4, new BigDecimal(
					nowMax + (count - 1)));
			arr[0] = minNo;
			arr[1] = maxMo;
			return arr;
		} catch (Exception e) {
			SxjLogger.error("计算RFID号区间错误", e, this.getClass());
			throw new ServiceException("计算RFID号区间错误", e);
		}

	}

	@Override
	@Transactional
	public Integer batchAddWindowRfid(WindowRfidEntity[] rfids)
			throws ServiceException {
		try {
			if (rfids != null) {
				return windowRfidDao.batchAddWindowRfid(rfids);
			} else {
				return 0;
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("批量新增门窗RFID错误", e);
		}

	}

	@Override
	@Transactional
	public void batchUpdateWindowRfid(WindowRfidEntity[] rfids)
			throws ServiceException {
		try {
			if (rfids != null) {
				windowRfidDao.batchUpdateWindowRfid(rfids);
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("批量修改门窗RFID错误", e);
		}

	}

	@Override
	@Transactional
	public void startWindowRfid(Long itemQuantity, Long useQuantity,
			String refContractNo, String minRfid, String maxRfid, String gRfid,
			String lRfid, WindowTypeEnum windowType) throws ServiceException {
		try {
			WindowRfidQuery query = new WindowRfidQuery();
			query.setContractNo(refContractNo);
			query.setRfidState(RfidStateEnum.used.getId());

			if (useQuantity >= itemQuantity) {
				throw new ServiceException("此招标合同已经全部启用完毕");
			}

			WindowRfidQuery query2 = new WindowRfidQuery();
			query2.setMinRfidNo(minRfid);
			query2.setMaxRfidNo(maxRfid);
			query2.setRfidState(RfidStateEnum.unused.getId());
			List<WindowRfidEntity> list = queryWindowRfid(query2);
			String memberNo = null;
			String memberName = null;
			for (Iterator<WindowRfidEntity> iterator = list.iterator(); iterator
					.hasNext();) {
				WindowRfidEntity windowRfid = iterator.next();
				if (windowRfid == null) {
					continue;
				}
				memberNo = windowRfid.getMemberNo();
				memberName = windowRfid.getMemberName();
				windowRfid.setGlassRfid(gRfid);
				windowRfid.setProfileRfid(lRfid);
				windowRfid.setWindowType(windowType);
				windowRfid.setRfidState(RfidStateEnum.used);
			}
			windowRfidDao.batchUpdateWindowRfid(list
					.toArray(new WindowRfidEntity[list.size()]));

			// 生成关联单
			WindowRefEntity winRef = new WindowRefEntity();
			winRef.setMinRfidNo(minRfid);
			winRef.setMaxRfidNo(maxRfid);
			winRef.setMemberNo(memberNo);
			winRef.setMemberName(memberName);
			winRef.setType(LinkStateEnum.windowApply);
			winRef.setWindowsNo(windowType);
			winRef.setGlassBatchNo(gRfid);
			winRef.setProfileBatchNo(lRfid);
			winRef.setApplyDate(new Date());
			// winRef.setReplenishRfid(replenishRfid);
			winRef.setContractNo(refContractNo);
			winRef.setState(AuditStateEnum.noapproval);
			winRefService.addWindowRfidRef(winRef);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("批量启用RFID失败", e);
		}

	}

	@Override
	public void lossWindowRfid(String refContractNo, String minRfid,
			String maxRfid, String gRfid, String lRfid, String[] addRfid,
			Long count) throws ServiceException {
		try {
			windowRfidDao.update_rfid(addRfid);
			WindowRfidQuery query = new WindowRfidQuery();
			query.setRfidNo(addRfid[0]);
			List<WindowRfidEntity> list = queryWindowRfid(query);
			// startWindowRfid(count, refContractNo, minRfid, maxRfid, gRfid,
			// lRfid, list.get(0).getWindowType());
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("批量补损RFID失败", e);
		}

	}

	/**
	 * 安装
	 */
	@Override
	@Transactional
	public int stepWindow(String rfidNo) throws ServiceException {
		try {
			WindowRfidEntity wind = windowRfidDao.selectByRfidNo(rfidNo);
			LabelProgressEnum[] label = LabelProgressEnum.values();
			if (!wind.getProgressState().equals(LabelProgressEnum.installed)) {
				// List<LogModel> list = JsonMapper
				// .nonEmptyMapper()
				// .getMapper()
				// .readValue(wind.getLog(),
				// new TypeReference<List<LogModel>>() {
				// });

				// Map<String, String> map = new HashMap<String, String>();
				// List<Map<String, String>> modelList = new
				// ArrayList<Map<String, String>>();
				// map.put("date", (new Date()).toString());
				// map.put("state", label[3].getName());
				// modelList.add(map);
				LogModel l = new LogModel();
				l.setDate((new Date()).toString());
				l.setState(label[3].getName());
				// list.add(l);
				// String log = JsonMapper.nonEmptyMapper().toJson(list);
				wind.setLogList(l);
				wind.setProgressState(LabelProgressEnum.installed);
				updateWindowRfid(wind);
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			return 0;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int testWindow(String contractNo, String[] rfidNos)
			throws ServiceException {
		try {
			for (String rfidNo : rfidNos) {
				WindowRfidEntity wind = windowRfidDao.selectByRfidNo(rfidNo);
				if (contractNo.equals(wind.getContractNo())
						&& (!wind.getProgressState().equals(
								LabelProgressEnum.hasQuality))) {
					// List<List<Map<String, String>>> list = JsonMapper
					// .nonEmptyMapper()
					// .getMapper()
					// .readValue(
					// wind.getLog(),
					// new TypeReference<List<Map<String, String>>>() {
					// });
					LabelProgressEnum[] label = LabelProgressEnum.values();
					// Map<String, String> map = new HashMap<String, String>();
					// List<Map<String, String>> modelList = new
					// ArrayList<Map<String, String>>();
					// map.put("date", (new Date()).toString());
					// map.put("state", label[4].getName());
					// modelList.add(map);
					// list.add(modelList);
					// String log = JsonMapper.nonEmptyMapper().toJson(list);
					LogModel l = new LogModel();
					l.setDate((new Date()).toString());
					l.setState(label[4].getName());
					wind.setLogList(l);
					wind.setProgressState(LabelProgressEnum.hasQuality);
					updateWindowRfid(wind);
				} else {
					return 0;
				}
			}
			return 1;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			return 0;
		}
	}
}
