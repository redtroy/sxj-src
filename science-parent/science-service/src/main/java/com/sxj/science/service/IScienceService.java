package com.sxj.science.service;

import java.util.List;

import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.science.model.result.OptimizedModel;
import com.sxj.util.exception.ServiceException;

public interface IScienceService
{
    public List<ScienceEntity> getScienceList(String[] itemId)
            throws ServiceException;
    
    public OptimizedModel process(List<ScienceEntity> list, String projectId,
            String length, String interval) throws ServiceException;
}
