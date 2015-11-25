package com.sxj.supervisor.model.countManage;

import java.io.Serializable;
import java.util.List;

import com.sxj.mybatis.pagination.Pagable;

public class HistoryListModel extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -8333218121481647508L;
    
    private List<HistoryModel> list;

    public List<HistoryModel> getList()
    {
        return list;
    }

    public void setList(List<HistoryModel> list)
    {
        this.list = list;
    }
}
