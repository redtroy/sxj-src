package com.sxj.science.service;

import java.util.List;

import com.sxj.science.model.DocModel;
import com.sxj.util.exception.ServiceException;

public interface IDocService
{
    
    public void addDocModel(DocModel doc) throws ServiceException;
    
    public void editDocModel(DocModel doc) throws ServiceException;
    
    public DocModel getDocModel(String docId) throws ServiceException;
    
    public List<DocModel> queryDocModel(String itemId) throws ServiceException;
    
    public List<DocModel> queryDocModel(String[] docIds)
            throws ServiceException;
    
}
