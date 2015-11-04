package com.sxj.science.service;

import java.util.List;

import com.sxj.science.entity.export.DocEntity;
import com.sxj.science.model.DocModel;
import com.sxj.science.model.DocQuery;
import com.sxj.util.exception.ServiceException;

public interface IDocService
{
    
    public void addDocModel(DocModel doc) throws ServiceException;
    
    public void editDocModel(DocModel doc) throws ServiceException;
    
    public DocModel getDocModel(String docId) throws ServiceException;
    
    public List<DocModel> queryDocModel(DocQuery query) throws ServiceException;
    
    public List<DocEntity> queryDoc(DocQuery query) throws ServiceException;
    
    //    public List<DocModel> queryDocModel(String[] docIds)
    //            throws ServiceException;
    
}
