package com.sxj.science.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.science.dao.export.IWindowTypeDao;
import com.sxj.science.entity.export.WindowTypeEntity;
import com.sxj.science.service.IDownloadTemService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class DownloadTemServiceImpl implements IDownloadTemService
{
    @Autowired
    private IWindowTypeDao windowTypeDao;
    
    @Override
    public List<WindowTypeEntity> queryWindowType(WindowTypeEntity query)
    {
        try
        {
            if (query == null)
            {
                return null;
            }
            QueryCondition<WindowTypeEntity> condition = new QueryCondition<WindowTypeEntity>();
            condition.addCondition("area", query.getArea());// 地区
            condition.addCondition("companyName", query.getCompanyName());// 公司名称
            condition.addCondition("type", query.getType());// 类型
            condition.addCondition("series", query.getSeries());// 系列
            condition.addCondition("searchStr", query.getSearchStr());// 公司名称或系列
            condition.setPage(query);
            List<WindowTypeEntity> list = windowTypeDao.queryWindowType(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询备案信息错误", e);
        }
    }
    
    @Override
    public List<WindowTypeEntity> searchWindowType(WindowTypeEntity query)
    {
        try
        {
            if (query == null)
            {
                return null;
            }
            QueryCondition<WindowTypeEntity> condition = new QueryCondition<WindowTypeEntity>();
            Map<String, Object> temMap = new HashMap<String, Object>();
            condition.addCondition("area", query.getArea());// 地区
            temMap.put("area", query.getArea());
            if (!StringUtil.isBlank(query.getCompanyName()))
            {
                condition.addCondition("companyNameArr", query.getCompanyName()
                        .split(","));// 公司名称
                temMap.put("companyNameArr", query.getCompanyName().split(","));
            }
            if (!StringUtil.isBlank(query.getType()))
            {
                condition.addCondition("typeArr", query.getType().split(","));// 类型
                temMap.put("typeArr", query.getType().split(","));
            }
            if (!StringUtil.isBlank(query.getSeries()))
            {
                condition.addCondition("seriesArr", query.getSeries()
                        .split(","));// 系列
                temMap.put("seriesArr", query.getSeries().split(","));
            }
            if (!StringUtil.isBlank(query.getSearchStr()))
            {
                condition.addCondition("searchStr", query.getSearchStr());
                temMap.put("searchStr", query.getSearchStr());
            }
            //            condition.addCondition("searchStr", query.getSearchStr());// 公司名称或系列
            condition.setPage(query);
            String[] aaa = (String[]) condition.getCondition()
                    .get("companyNameArr");
            List<WindowTypeEntity> list = windowTypeDao.searchWindowType(temMap);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询备案信息错误", e);
        }
    }
    
    @Override
    public List<WindowTypeEntity> getWindowTypeByIds(String idArr)
    {
        String[] ids = idArr.split(",");
        List<WindowTypeEntity> list = new ArrayList<WindowTypeEntity>();
        for (int i = 0; i < ids.length; i++)
        {
            list.add(windowTypeDao.getWindowType(ids[i]));
        }
        return list;
    }
    
    @Override
    public List<WindowTypeEntity> openQueryWindowType(WindowTypeEntity query)
    {
        try
        {
            if (query == null)
            {
                return null;
            }
            QueryCondition<WindowTypeEntity> condition = new QueryCondition<WindowTypeEntity>();
            condition.addCondition("winId", query.getWinId());// 编号
            condition.addCondition("area", query.getArea());// 地区
            condition.addCondition("companyName", query.getCompanyName());// 公司名称
            condition.addCondition("type", query.getType());// 类型
            condition.addCondition("series", query.getSeries());// 系列
            condition.addCondition("name", query.getName());//名称
            condition.setPage(query);
            List<WindowTypeEntity> list = windowTypeDao.openQueryWindowType(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询备案信息错误", e);
        }
    }
    
    @Override
    public void addWindowType(WindowTypeEntity query)
    {
        try
        {
            this.windowTypeDao.addWindowType(query);
        }
        catch (Exception e)
        {
            throw new ServiceException("新增模板错误", e);
        }
    }
    
    @Override
    public void delCountTem(String id)
    {
        this.windowTypeDao.deleteWindowType(id);
    }
    
    @Override
    public WindowTypeEntity getType(String id)
    {
        return windowTypeDao.getWindowType(id);
    }
    
    @Override
    public List<WindowTypeEntity> autoWindowType(WindowTypeEntity query)
    {
        try {
            if (query == null) {
                return null;
            }
            QueryCondition<WindowTypeEntity> condition = new QueryCondition<WindowTypeEntity>();
            condition.addCondition("area", query.getArea());// 地区
            condition.addCondition("companyName", query.getCompanyName());// 公司名称
            condition.addCondition("type", query.getType());// 类型
            condition.addCondition("series", query.getSeries());// 系列
            condition.addCondition("searchStr", query.getSearchStr());// 公司名称或系列
            condition.setPage(query);
            List<WindowTypeEntity> list = windowTypeDao.autoWindowType(condition);
            query.setPage(condition);
            return list;
        } catch (Exception e) {
            throw new ServiceException("查询备案信息错误", e);
        }
    }

    @Override
    public WindowTypeEntity getWindowTypeById(String id)
    {
        return windowTypeDao.getWindowType(id);
    }

    @Override
    public void updateWindowType(WindowTypeEntity windowType)
    {
        windowTypeDao.updateWindowType(windowType);
        
    }
    
}
