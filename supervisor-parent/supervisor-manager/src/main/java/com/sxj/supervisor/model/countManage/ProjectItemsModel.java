package com.sxj.supervisor.model.countManage;

import java.io.Serializable;
import java.util.List;

import com.sxj.mybatis.pagination.Pagable;

public class ProjectItemsModel extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -228086778932230460L;
    
    private List<ItemModel> projectItems;
    
    private ProjectEntity project;

    public List<ItemModel> getProjectItems()
    {
        return projectItems;
    }

    public void setProjectItems(List<ItemModel> projectItems)
    {
        this.projectItems = projectItems;
    }

    public ProjectEntity getProject()
    {
        return project;
    }

    public void setProject(ProjectEntity project)
    {
        this.project = project;
    }

    
    
}
