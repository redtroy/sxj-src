package com.sxj.science.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.entity.export.DocEntity;
import com.sxj.science.entity.export.ItemEntity;

public class ItemModel extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2867629076440048128L;
    
    private ItemEntity item = new ItemEntity();
    
    private List<DocEntity> docList = new ArrayList<DocEntity>();
    
    public String getId()
    {
        return item.getId();
    }
    
    public void setId(String id)
    {
        item.setId(id);
    }
    
    public String getProjectId()
    {
        return item.getProjectId();
    }
    
    public void setProjectId(String projectId)
    {
        item.setProjectId(projectId);
    }
    
    public String getName()
    {
        return item.getName();
    }
    
    public void setName(String name)
    {
        item.setName(name);
    }
    
    public String getFilePath()
    {
        return item.getFilePath();
    }
    
    public void setFilePath(String filePath)
    {
        item.setFilePath(filePath);
    }
    
    public Integer getCount()
    {
        return item.getCount();
    }
    
    public void setCount(Integer count)
    {
        item.setCount(count);
    }
    
    public Date getUploadTime()
    {
        return item.getUploadTime();
    }
    
    public void setUploadTime(Date uploadTime)
    {
        item.setUploadTime(uploadTime);
    }
    
    public List<DocEntity> getDocList()
    {
        return docList;
    }
    
    public void setDocList(List<DocEntity> docList)
    {
        this.docList = docList;
    }
    
    public ItemEntity getItem()
    {
        return item;
    }
    
    public void setItem(ItemEntity item)
    {
        this.item = item;
    }
    
    public Integer getState()
    {
        return item.getState();
    }
    
    public void setState(Integer state)
    {
        item.setState(state);
    }
    
}
