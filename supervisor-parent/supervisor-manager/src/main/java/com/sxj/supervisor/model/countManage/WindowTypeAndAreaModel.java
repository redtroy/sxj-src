package com.sxj.supervisor.model.countManage;

import java.io.Serializable;

import java.util.List;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.entity.system.AreaEntity;

public class WindowTypeAndAreaModel extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 449192591552945492L;
    
    private List<WindowTypeModel> list;
    
    private List<AreaEntity> areaList;
    
    private WindowTypeModel query;

    public WindowTypeModel getQuery()
    {
        return query;
    }

    public void setQuery(WindowTypeModel query)
    {
        this.query = query;
    }

    public List<WindowTypeModel> getList()
    {
        return list;
    }

    public void setList(List<WindowTypeModel> list)
    {
        this.list = list;
    }

    public List<AreaEntity> getAreaList()
    {
        return areaList;
    }

    public void setAreaList(List<AreaEntity> areaList)
    {
        this.areaList = areaList;
    }
    
}
