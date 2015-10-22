package com.sxj.science.service;

import java.util.List;

import com.sxj.science.entity.system.AreaEntity;
import com.sxj.util.exception.ServiceException;

public interface IAreaService
{
    
    public List<AreaEntity> getChildrenAreas(String parentId)
            throws ServiceException;

    public List<AreaEntity> getAreaByIdList(List<String> areaIdList);
    
}
