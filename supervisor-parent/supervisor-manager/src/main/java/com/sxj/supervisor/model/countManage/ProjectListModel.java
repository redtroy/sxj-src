package com.sxj.supervisor.model.countManage;

import java.io.Serializable;
import java.util.List;

import com.sxj.mybatis.pagination.Pagable;

public class ProjectListModel extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 5683560673643256850L;
    
    private List<ProjectModel> list;
    
    private ProjectModel query;

    public List<ProjectModel> getList()
    {
        return list;
    }

    public void setList(List<ProjectModel> list)
    {
        this.list = list;
    }

    public ProjectModel getQuery()
    {
        return query;
    }

    public void setQuery(ProjectModel query)
    {
        this.query = query;
    }
    
    
}
