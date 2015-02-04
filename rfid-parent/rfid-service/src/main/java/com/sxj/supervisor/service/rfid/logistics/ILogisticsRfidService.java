package com.sxj.supervisor.service.rfid.logistics;

import java.util.List;

import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.model.rfid.RfidLog;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.util.exception.ServiceException;

public interface ILogisticsRfidService
{
    /**
     * 根据条件高级查询
     * 
     * @param query
     * @return
     * @throws ServiceException
     */
    public List<LogisticsRfidEntity> queryLogistics(LogisticsRfidQuery query)
            throws ServiceException;
    
    /**
     * 根据条件高级查询 后台 乙方（供应链物流管理）
     * 
     * @param query
     * @return
     * @throws ServiceException
     */
    public List<LogisticsRfidEntity> queryLogistics_A(LogisticsRfidQuery query)
            throws ServiceException;
    
    /**
     * 根据条件高级查询 后台 甲方（供应链物流管理）
     * 
     * @param query
     * @return
     * @throws ServiceException
     */
    public List<LogisticsRfidEntity> queryLogistics_B(LogisticsRfidQuery query)
            throws ServiceException;
    
    /**
     * 更新
     * 
     * @param rfid
     * @throws ServiceException
     */
    public void updateLogistics(LogisticsRfidEntity rfid)
            throws ServiceException;
    
    /**
     * 批量添加
     * 
     * @param rfids
     * @throws ServiceException
     */
    public Integer batchAddLogistics(LogisticsRfidEntity[] rfids)
            throws ServiceException;
    
    /**
     * 批量更新
     * 
     * @param rfids
     * @throws ServiceException
     */
    public void batchUpdateLogistics(LogisticsRfidEntity[] rfids)
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
    public LogisticsRfidEntity getLogistics(String id) throws ServiceException;
    
    /**
     * 
     * @param rfidNo
     * @return
     * @throws ServiceException
     */
    public LogisticsRfidEntity getLogisticsByNo(String rfidNo)
            throws ServiceException;
    
    void updateGid(List<LogisticsRfidEntity> list, String id)
            throws ServiceException;

	int getRfidState(String rfidNo);
}
