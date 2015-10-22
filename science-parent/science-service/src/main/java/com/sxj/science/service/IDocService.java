package com.sxj.science.service;

import com.sxj.science.model.DocModel;
import com.sxj.util.exception.ServiceException;

public interface IDocService
{
    
    public void addDocModel(DocModel doc) throws ServiceException;
    
}
