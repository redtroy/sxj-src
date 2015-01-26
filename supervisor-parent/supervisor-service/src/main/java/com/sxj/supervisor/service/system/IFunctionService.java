package com.sxj.supervisor.service.system;

import java.util.List;

import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.model.system.FunctionModel;
import com.sxj.util.exception.ServiceException;

public interface IFunctionService
{
    
    /**
     * 读取所有菜单(按照层级)
     * 
     * @return
     */
    public List<FunctionModel> queryFunctions() throws ServiceException;
    
    /**
     * 读取所有子菜单
     * 
     * @return
     */
    public List<FunctionEntity> queryChildrenFunctions(String parentId)
            throws ServiceException;
    
    /**
     * 根据获取系统功能信息
     * 
     * @param id
     * @return
     */
    public FunctionEntity getFunction(String id) throws ServiceException;
}
