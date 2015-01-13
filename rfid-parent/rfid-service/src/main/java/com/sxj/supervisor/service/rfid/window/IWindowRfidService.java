package com.sxj.supervisor.service.rfid.window;

import java.util.List;

import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.model.rfid.RfidLog;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.util.exception.ServiceException;

public interface IWindowRfidService {
	/**
	 * 根据条件高级查询
	 * 
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public List<WindowRfidEntity> queryWindowRfid(WindowRfidQuery query)
			throws ServiceException;

	/**
	 * 更新
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	public void updateWindowRfid(WindowRfidEntity win) throws ServiceException;

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws ServiceException
	 */
	public void deleteWindowRfid(String id) throws ServiceException;

	/**
	 * 启用门窗RFID
	 * 
	 * @throws ServiceException
	 */
	public void startWindowRfid(Long itemQuantity, Long useQuantity,
			String refContractNo, Integer count, String gRfid, String lRfid,
			WindowTypeEnum windowType) throws ServiceException;

	/**
	 * 批量新增
	 * 
	 * @param rfids
	 * @throws ServiceException
	 */
	public Integer batchAddWindowRfid(WindowRfidEntity[] rfids)
			throws ServiceException;

	/**
	 * 批量更新
	 * 
	 * @param rfids
	 * @throws ServiceException
	 */
	public void batchUpdateWindowRfid(WindowRfidEntity[] rfids)
			throws ServiceException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public List<RfidLog> getRfidStateLog(String id) throws ServiceException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public WindowRfidEntity getWindowRfid(String id) throws ServiceException;

	/**
	 * 
	 * @param no
	 * @return
	 * @throws ServiceException
	 */
	public WindowRfidEntity getWindowRfidByNo(String rfidNo)
			throws ServiceException;

	/**
	 * 
	 * @param contractNo
	 * @param count
	 * @return
	 * @throws ServiceException
	 */
	public String[] getStartMaxRfidNo(String contractNo, Long count)
			throws ServiceException;

	/**
	 * 
	 * @param contractNo
	 * @param count
	 * @return
	 * @throws ServiceException
	 */
	public String[] getLossMaxRfidNo(String contractNo, Long count)
			throws ServiceException;

	/**
	 * 补损RFID标签
	 */
	public void lossWindowRfid(String refContractNo, Integer count,
			String gRfid, String lRfid, String[] addRfid)
			throws ServiceException;

	/**
	 * 补损RFID标签
	 */
	public void lossWindowRfid(String rfidNo, String newRfidNo)
			throws ServiceException;

	/**
	 * 安装
	 */
	public int stepWindow(String gid) throws ServiceException;

	/**
	 * 质检
	 */
	public int testWindow(String contractNo, String[] gids)
			throws ServiceException;

	void updateGid(List<WindowRfidEntity> winList,String id) throws ServiceException;
}
