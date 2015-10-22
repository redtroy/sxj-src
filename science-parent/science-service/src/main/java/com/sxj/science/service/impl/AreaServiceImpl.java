package com.sxj.science.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.science.dao.system.IAreaDao;
import com.sxj.science.entity.system.AreaEntity;
import com.sxj.science.service.IAreaService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
public class AreaServiceImpl implements IAreaService
{
    
    @Autowired
    private IAreaDao areaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<AreaEntity> getChildrenAreas(String parentId)
            throws ServiceException
    {
        try
        {
            QueryCondition<AreaEntity> query = new QueryCondition<AreaEntity>();
            query.addCondition("parentId", parentId);
            List<AreaEntity> list = areaDao.getAreas(query);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询地理信息错误", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaEntity> getAreaByIdList(List<String> areaIdList)
    {
        try
        {
            List<AreaEntity> list=new ArrayList<AreaEntity>();
            for(int i=0;i<areaIdList.size();i++){
                QueryCondition<AreaEntity> query = new QueryCondition<AreaEntity>();
                query.addCondition("id", areaIdList.get(i));
                List<AreaEntity> temList = areaDao.getAreaById(query);
                if(temList!=null){
                    list.add(temList.get(0));
                }
            }
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询地理信息错误", e);
        }
    }
}
