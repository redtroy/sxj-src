package com.sxj.science.service;

import java.util.List;

import com.sxj.science.entity.export.AloneOptimEntity;
import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.science.model.AloneOptimQuery;
import com.sxj.util.exception.ServiceException;

public interface IAloneOptimService
{
    public void addAloneOptim(AloneOptimEntity alone) throws ServiceException;
    
    public List<AloneOptimEntity> query(AloneOptimQuery query)
            throws ServiceException;
    
    public AloneOptimEntity getAloneOptim(String id) throws ServiceException;
    
    public List<ScienceEntity> readExcel(String[] ids) throws ServiceException;
    
    public void removeAloneOptim(String id) throws ServiceException;
}
