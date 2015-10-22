package com.sxj.science.service;

import java.util.List;

import com.sxj.science.model.ItemModel;
import com.sxj.util.exception.ServiceException;

public interface IExeclOperator
{
    public List<ItemModel> readExecl(byte[] excelFile) throws ServiceException;
    
    public void uploadExcel(String memberNo, String fileName, String filePath,
            String projectId, byte[] excelFile) throws ServiceException;
}
