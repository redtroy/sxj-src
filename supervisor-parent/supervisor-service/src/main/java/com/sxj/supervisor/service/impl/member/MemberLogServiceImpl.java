package com.sxj.supervisor.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IMemberLogDao;
import com.sxj.supervisor.entity.member.MemberLogEntity;
import com.sxj.supervisor.model.member.LogQuery;
import com.sxj.supervisor.service.member.IMemberLogService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class MemberLogServiceImpl implements IMemberLogService
{
    
    @Autowired
    private IMemberLogDao dao;
    
    @Override
    @Transactional
    public void addLog(MemberLogEntity entity) throws ServiceException
    {
        try
        {
            dao.addLog(entity);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MemberLogEntity> queryLogs(LogQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<MemberLogEntity> condition = new QueryCondition<>();
            if (query != null)
            {
                condition.addCondition("memberNo", query.getMemberNo());
                condition.addCondition("currentPage", query.getCurrentPage());
                condition.addCondition("endTime", query.getEndTime());
                condition.addCondition("startTime", query.getStartTime());
                condition.addCondition("nextpage", query.getNextpage());
                condition.addCondition("nowPage", query.getNowPage());
                condition.addCondition("prePage", query.getPrePage());
                condition.setPage(query);
            }
            List<MemberLogEntity> list = dao.queryLogs(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
