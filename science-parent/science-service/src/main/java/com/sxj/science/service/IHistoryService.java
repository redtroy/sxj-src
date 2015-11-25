package com.sxj.science.service;

import java.util.List;

import com.sxj.science.entity.export.HistoryEntity;

public interface IHistoryService
{

    public List<HistoryEntity> queryHistory(String projectId);
    
}
