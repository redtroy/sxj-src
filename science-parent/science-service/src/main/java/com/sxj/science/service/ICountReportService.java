package com.sxj.science.service;

import java.util.List;

import com.sxj.science.DocReportModel;
import com.sxj.science.model.PartsModel;
import com.sxj.science.model.ProductModel;

public interface ICountReportService
{

    public List<ProductModel> getProductReport(List<String> itemIds);

    public List<PartsModel> getPartsReport(List<String> temList);

    public List<DocReportModel> getDocReport(List<String> temList);
    
}
