package com.sxj.science.service;

import java.util.List;

import com.sxj.science.entity.export.ItemEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.model.ItemModel;
import com.sxj.science.model.ProjectQuery;
import com.sxj.util.exception.ServiceException;

public interface IProjectService
{
    public void addProject(ProjectEntity project) throws ServiceException;
    
    public void addItem(ItemEntity item) throws ServiceException;
    
    public void modifyProject(ProjectEntity project) throws ServiceException;
    
    public void modifyItem(ItemEntity project) throws ServiceException;
    
    public ProjectEntity getProject(String id) throws ServiceException;
    
    public List<ProjectEntity> query(ProjectQuery query)
            throws ServiceException;
    
    public Integer queryFileCount(ProjectQuery query) throws ServiceException;
    
    public List<ItemModel> queryItems(String projectId) throws ServiceException;
    
    public List<ItemEntity> getItem(String[] projectItemIds)
            throws ServiceException;
    
    public void removeProject(String id) throws ServiceException;
    
    public void removeItem(String itemId) throws ServiceException;
}
