package com.sxj.supervisor.service.gov;

import java.util.List;

import com.sxj.supervisor.entity.gov.GovEntity;
import com.sxj.util.exception.ServiceException;

public interface IGovService
{
    /**
     * 政务信息高级查询
     * @param query
     * @return GovEntity
     * @throws ServiceException
     * @author nist
     */
    public List<GovEntity> queryGovList(GovEntity query)
            throws ServiceException;
    
    /**
     * 新增政务信息
     * @param entity
     * @throws ServiceException
     * @author nist
     */
    public void addGov(GovEntity entity) throws ServiceException;
    
    /**
     * 修改政务信息
     * @param entity
     * @throws ServiceException
     * @author nist
     */
    public void updateGov(GovEntity entity) throws ServiceException;
    
    /**
     * 删除政务信息
     * @param id
     * @throws ServiceException
     * @author nist
     */
    public void delGov(String id) throws ServiceException;
    
    /**
     * 获取政务信息详情
     * @param id
     * @throws ServiceException
     * @author nist
     */
    public GovEntity getGov(String id) throws ServiceException;
    
}
