package com.sxj.supervisor.service.impl.rfid.window;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.rfid.window.IWindowRfidDao;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.entity.rfid.windowRef.WindowRefEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.enu.rfid.windowRef.LinkStateEnum;
import com.sxj.supervisor.model.rfid.RfidLog;
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
			if (win == null) {
				throw new ServiceException("门窗RFID不存在");
			}
			windowRfidDao.updateWindowRfid(win);
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("更新门窗RFID错误", e);
		}
	}

	@Override
	@Transactional
	public void deleteWindowRfid(String id) throws ServiceException {
		try {
			WindowRfidEntity rfid = getWindowRfid(id);
			if (rfid == null) {
				throw new ServiceException("RFID不存在");
			}
			if (!rfid.getRfidState().equals(RfidStateEnum.unused)) {
				throw new ServiceException("RFID不是未使用状态，不可删除");
			}
			WindowRfidEntity win = new WindowRfidEntity();
			win.setId(id);
			win.setRfidState(RfidStateEnum.delete);
			updateWindowRfid(win);
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("删除门窗RFID错误");
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<RfidLog> getRfidStateLog(String id) throws ServiceException {
		try {
			WindowRfidEntity win = windowRfidDao.getWindowRfid(id);
			if (win == null) {
				return null;
			}
			return win.getLogList();
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
			if (useQuantity >= itemQuantity) {
				throw new ServiceException("此招标合同已经全部启用完毕");
			}
			WindowRfidQuery query2 = new WindowRfidQuery();
			query2.setContractNo(refContractNo);
			query2.setMinRfidNo(minRfid);
			query2.setMaxRfidNo(maxRfid);
			query2.setRfidState(RfidStateEnum.unused.getId());
			List<WindowRfidEntity> list = queryWindowRfid(query2);
			String memberNo = null;
			String memberName = null;
			if (list == null || list.size() == 0) {
				throw new ServiceException("此区间没有未启用的RFID");
			}
			int nowQuantity = list.size();
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

			// 判断是否去全部启用，如全部启用剩下未启用的设置为停用
			if (nowQuantity + useQuantity == itemQuantity) {
				WindowRfidQuery query3 = new WindowRfidQuery();
				query3.setContractNo(refContractNo);
				query2.setRfidState(RfidStateEnum.unused.getId());
				List<WindowRfidEntity> otherList = queryWindowRfid(query3);
				if (otherList != null && otherList.size() > 0) {
					for (WindowRfidEntity otherRfid : otherList) {
						if (otherRfid == null) {
							continue;
						}
						otherRfid.setRfidState(RfidStateEnum.disable);
					}
					windowRfidDao.batchUpdateWindowRfid(otherList
							.toArray(new WindowRfidEntity[otherList.size()]));
				}
			}

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
	@Transactional
	public void lossWindowRfid(String refContractNo, String minRfid,
			String maxRfid, String gRfid, String lRfid, String[] addRfid)
			throws ServiceException {
		try {
			if (addRfid == null || addRfid.length == 0) {
				throw new ServiceException("没有输入需要被补损的RFID");
			}
			WindowRfidQuery query2 = new WindowRfidQuery();
			query2.setMinRfidNo(minRfid);
			query2.setMaxRfidNo(maxRfid);
			query2.setContractNo(refContractNo);
			query2.setRfidState(RfidStateEnum.unused.getId());
			List<WindowRfidEntity> list = queryWindowRfid(query2);
			if (list == null || list.size() == 0) {
				throw new ServiceException("补损的RFID不存在");
			}
			if (list.size() != addRfid.length) {
				throw new ServiceException("补损的RFID数量与需要被补损的RFID数量不一致");
			}
			String memberNo = null;
			String memberName = null;
			WindowTypeEnum windowsNo = null;
			String replenishRfid = null;
			for (int i = 0; i < addRfid.length; i++) {
				WindowRfidEntity oldRfid = getWindowRfidByNo(addRfid[i]);
				if (oldRfid == null) {
					throw new ServiceException("编号为：" + addRfid[i]
							+ "的被补损RFID不存在");
				}
				WindowRfidEntity newRfid = list.get(i);
				if (newRfid == null) {
					throw new ServiceException("补损的RFID不存在");
				}
				if (!newRfid.getContractNo().equals(oldRfid.getContractNo())) {
					throw new ServiceException("编号为：" + addRfid[i]
							+ "的被补损RFID与编号为：" + newRfid.getRfidNo()
							+ "的新RFID招标合同不一致");
				}
				memberNo = newRfid.getMemberNo();
				memberName = newRfid.getMemberName();
				windowsNo = newRfid.getWindowType();
				if (i < addRfid.length - 1) {
					replenishRfid = replenishRfid + oldRfid.getRfidNo() + ",";
				} else {
					replenishRfid = replenishRfid + oldRfid.getRfidNo();
				}
				// 更新旧RFID
				oldRfid.setReplenishNo(newRfid.getRfidNo());
				oldRfid.setRfidState(RfidStateEnum.damaged);
				updateWindowRfid(oldRfid);

				// 设置新RFID
				newRfid.setGlassRfid(gRfid);
				newRfid.setProfileRfid(lRfid);
				newRfid.setWindowType(oldRfid.getWindowType());
				newRfid.setRfidState(RfidStateEnum.used);
				updateWindowRfid(newRfid);
			}
			// windowRfidDao.batchUpdateWindowRfid(list
			// .toArray(new WindowRfidEntity[list.size()]));
			// 生成关联单
			WindowRefEntity winRef = new WindowRefEntity();
			winRef.setMinRfidNo(minRfid);
			winRef.setMaxRfidNo(maxRfid);
			winRef.setMemberNo(memberNo);
			winRef.setMemberName(memberName);
			winRef.setType(LinkStateEnum.windowLoss);
			winRef.setWindowsNo(windowsNo);
			winRef.setGlassBatchNo(gRfid);
			winRef.setProfileBatchNo(lRfid);
			winRef.setApplyDate(new Date());
			winRef.setReplenishRfid(replenishRfid);
			winRef.setContractNo(refContractNo);
			winRef.setState(AuditStateEnum.noapproval);
			winRefService.addWindowRfidRef(winRef);
		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("批量补损RFID失败", e);
		}
	}

	@Override
	@Transactional
	public void lossWindowRfid(String rfidNo, String newRfidNo)
			throws ServiceException {
		try {
			WindowRfidEntity rfid = getWindowRfidByNo(rfidNo);
			if (rfid == null) {
				throw new ServiceException("被补损的RFID不存在");
			}
			WindowRfidEntity newRfid = getWindowRfidByNo(newRfidNo);
			if (newRfid == null) {
				throw new ServiceException("补损的RFID不存在");
			}
			rfid.setReplenishNo(newRfidNo);
			rfid.setRfidState(RfidStateEnum.damaged);
			updateWindowRfid(rfid);

			newRfid.setRfidState(RfidStateEnum.used);
			newRfid.setWindowType(rfid.getWindowType());
			newRfid.setContractNo(rfid.getContractNo());
			newRfid.setGlassRfid(rfid.getGlassRfid());
			newRfid.setProfileRfid(rfid.getProfileRfid());
			updateWindowRfid(newRfid);

			// 生成关联单
			WindowRefEntity winRef = new WindowRefEntity();
			winRef.setMinRfidNo(newRfidNo);
			winRef.setMaxRfidNo(newRfidNo);
			winRef.setMemberNo(newRfid.getMemberNo());
			winRef.setMemberName(newRfid.getMemberName());
			winRef.setType(LinkStateEnum.rfidLoss);
			winRef.setWindowsNo(newRfid.getWindowType());
			winRef.setGlassBatchNo(newRfid.getGlassRfid());
			winRef.setProfileBatchNo(newRfid.getProfileRfid());
			winRef.setApplyDate(new Date());
			winRef.setReplenishRfid(rfidNo);
			winRef.setContractNo(newRfid.getContractNo());
			winRef.setState(AuditStateEnum.noapproval);
			winRefService.addWindowRfidRef(winRef);

		} catch (ServiceException e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("补损RFID失败", e);
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
			if (!wind.getProgressState().equals(LabelProgressEnum.installed)) {
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
