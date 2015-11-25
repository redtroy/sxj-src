package com.sxj.science.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.science.dao.export.IHistoryDao;
import com.sxj.science.entity.export.HistoryEntity;
import com.sxj.science.service.IHistoryService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class IHistoryServiceImpl implements IHistoryService
{
    @Autowired
    private IHistoryDao historyDao;

    @Override
    public List<HistoryEntity> queryHistory(String projectId)
    {
        try
        {
            QueryCondition<HistoryEntity> condition = new QueryCondition<>();
            condition.addCondition("projectId", projectId);
//            condition.setPage(query);
            List<HistoryEntity> list = historyDao.query(condition);
//            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程错误", e, this.getClass());
            throw new ServiceException("查询工程错误", e);
        }
    }
}
