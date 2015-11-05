package com.sxj.science.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.science.DocReportModel;
import com.sxj.science.dao.export.IItemDao;
import com.sxj.science.dao.export.IPartsDao;
import com.sxj.science.dao.export.IProductDao;
import com.sxj.science.entity.export.DocEntity;
import com.sxj.science.entity.export.PartsEntity;
import com.sxj.science.entity.export.ProductEntity;
import com.sxj.science.model.DocQuery;
import com.sxj.science.model.PartsModel;
import com.sxj.science.model.ProductModel;
import com.sxj.science.service.ICountReportService;
import com.sxj.science.service.IDocService;

@Service
@Transactional
public class CountReportServiceImpl implements ICountReportService
{
    @Autowired
    private IItemDao itemDao;
    
    @Autowired
    private IDocService docService;
    
    @Autowired
    private IProductDao productDao;
    
    @Autowired
    private IPartsDao partsDao;
    
    @Override
    public List<ProductModel> getProductReport(List<String> itemIds)
    {
        //        List<ItemEntity> items=new ArrayList<ItemEntity>();
        //        for(int i=0;i<itemIds.size();i++){
        //            items.add(itemDao.getItem(itemIds.get(i)));
        //        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String[] itemIdsStr = itemIds.toArray(new String[itemIds.size()]);
        DocQuery query = new DocQuery();
        query.setItemIds(itemIdsStr);
        List<DocEntity> docList = docService.queryDoc(query);
        List<String> docIds = new ArrayList<String>();
        Map<String, String> windowCodeMap = new HashMap<String, String>();
        for (int i = 0; i < docList.size(); i++)
        {
            docIds.add(docList.get(i).getId());
            windowCodeMap.put(docList.get(i).getId(), docList.get(i)
                    .getWindowCode());
        }
        String[] docIdsStr = docIds.toArray(new String[docIds.size()]);
        paramMap.put("docIds", docIdsStr);
        List<ProductEntity> temProList = productDao.getProductByDocIds(paramMap);
        List<ProductModel> proList = new ArrayList<ProductModel>();
        for (int i = 0; i < temProList.size(); i++)
        {
            ProductModel temPro = new ProductModel();
            temPro.setId(temProList.get(i).getId());
            temPro.setName(temProList.get(i).getName());
            String width = temProList.get(i).getWidth();
            temPro.setWidth(width);
            String height = temProList.get(i).getHeight();
            temPro.setHeight(height);
            temPro.setQuantity(temProList.get(i).getQuantity());
            if (!StringUtils.isBlank(width) && !StringUtils.isBlank(height))
            {
                Double temArea = (Double.parseDouble(width) * Double.parseDouble(height)) / 1000000;
                temPro.setArea(temArea);
            }
            else
            {
                temPro.setArea(0.0);
            }
            temPro.setWindowCode(windowCodeMap.get(temProList.get(i).getDocId()));
            temPro.setDocId(temProList.get(i).getDocId());
            proList.add(temPro);
        }
        
        return proList;
    }
    
    @Override
    public List<PartsModel> getPartsReport(List<String> itemIds)
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String[] itemIdsStr = itemIds.toArray(new String[itemIds.size()]);
        DocQuery query = new DocQuery();
        query.setItemIds(itemIdsStr);
        List<DocEntity> docList = docService.queryDoc(query);
        List<String> docIds = new ArrayList<String>();
        Map<String, String> windowCodeMap = new HashMap<String, String>();
        for (int i = 0; i < docList.size(); i++)
        {
            docIds.add(docList.get(i).getId());
            windowCodeMap.put(docList.get(i).getId(), docList.get(i)
                    .getWindowCode());
        }
        String[] docIdsStr = docIds.toArray(new String[docIds.size()]);
        paramMap.put("docIds", docIdsStr);
        List<PartsEntity> temList = partsDao.getPartsByDocIds(paramMap);
        List<PartsModel> partsList = new ArrayList<PartsModel>();
        for (int i = 0; i < temList.size(); i++)
        {
            PartsEntity temEntity = temList.get(i);
            PartsModel temModel = new PartsModel();
            temModel.setId(temEntity.getId());
            temModel.setName(temEntity.getName());
            temModel.setDocId(temEntity.getDocId());
            temModel.setIndex(i + 1);
            temModel.setQuantity(temEntity.getQuantity());
            temModel.setTechonlogy(temEntity.getTechonlogy());
            temModel.setType(temEntity.getType());
            temModel.setUnit(temEntity.getUnit());
            temModel.setUsed(temEntity.getUsed());
            partsList.add(temModel);
        }
        return partsList;
    }
    
    @Override
    public List<DocReportModel> getDocReport(List<String> itemIds)
    {
        String[] itemIdsStr = itemIds.toArray(new String[itemIds.size()]);
        DocQuery query = new DocQuery();
        query.setItemIds(itemIdsStr);
        List<DocEntity> temDocList = docService.queryDoc(query);
        List<DocReportModel> docList = new ArrayList<DocReportModel>();
        for (int i = 0; i < temDocList.size(); i++)
        {
            DocEntity temDoc = temDocList.get(i);
            DocReportModel temMod = new DocReportModel();
            temMod.setId(temDoc.getId());
            temMod.setIndex(i + 1);
            temMod.setSeries(temDoc.getSeries());
            String width = temDoc.getWidth();
            temMod.setWidth(temDoc.getWidth());
            String height = temDoc.getHeight();
            temMod.setHeight(temDoc.getHeight());
            temMod.setQuantity(temDoc.getQuantity());
            if (!StringUtils.isBlank(width) && !StringUtils.isBlank(height))
            {
                Double temArea = (Double.parseDouble(temDoc.getWidth()) * Double.parseDouble(temDoc.getHeight())) / 1000000;
                temMod.setSumArea(temArea);
            }
            else
            {
                temMod.setSumArea(0.0);
            }
            docList.add(temMod);
        }
        return docList;
    }
}
