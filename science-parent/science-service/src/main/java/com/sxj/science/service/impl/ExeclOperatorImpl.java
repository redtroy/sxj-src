package com.sxj.science.service.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.SheetsReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.science.dao.export.IDocDao;
import com.sxj.science.dao.export.IGlassDao;
import com.sxj.science.dao.export.IItemDao;
import com.sxj.science.dao.export.IPartsDao;
import com.sxj.science.dao.export.IProductDao;
import com.sxj.science.dao.export.IProjectDao;
import com.sxj.science.dao.export.IScienceDao;
import com.sxj.science.entity.export.DocEntity;
import com.sxj.science.entity.export.GlassEntity;
import com.sxj.science.entity.export.ItemEntity;
import com.sxj.science.entity.export.PartsEntity;
import com.sxj.science.entity.export.ProductEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.science.model.DocModel;
import com.sxj.science.model.ItemModel;
import com.sxj.science.service.IExeclOperator;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
public class ExeclOperatorImpl implements IExeclOperator
{
    
    @Value("${excel.tem1}")
    private String xmlConfig;
    
    @Value("${excel.tem2}")
    private String xmlConfig2;
    
    @Value("${excel.tem3}")
    private String xmlConfig3;
    
    @Autowired
    private IDocDao docDao;
    
    @Autowired
    private IGlassDao glassDao;
    
    @Autowired
    private IPartsDao partsDao;
    
    @Autowired
    private IProductDao productDao;
    
    @Autowired
    private IScienceDao scienceDao;
    
    @Autowired
    private IProjectDao projectdao;
    
    @Autowired
    private IItemDao itemDao;
    
    @Override
    public List<ItemModel> readExecl(byte[] excelFile) throws ServiceException
    {
        try
        {
            InputStream xmlInputStream1 = new FileInputStream(xmlConfig);
            InputStream xmlInputStream2 = new FileInputStream(xmlConfig2);
            InputStream xmlInputStream3 = new FileInputStream(xmlConfig3);
            
            SheetsReader reader1 = (SheetsReader) ReaderBuilder.buildSheetsReaderFromXML(xmlInputStream1);
            SheetsReader reader2 = (SheetsReader) ReaderBuilder.buildSheetsReaderFromXML(xmlInputStream2);
            SheetsReader reader3 = (SheetsReader) ReaderBuilder.buildSheetsReaderFromXML(xmlInputStream3);
            //            XLSReader reader1 = ReaderBuilder.buildFromXML(xmlInputStream1);
            //            XLSReader reader2 = ReaderBuilder.buildFromXML(xmlInputStream2);
            //            XLSReader reader3 = ReaderBuilder.buildFromXML(xmlInputStream3);
            
            InputStream xlsInputStream1 = new ByteArrayInputStream(excelFile);
            InputStream xlsInputStream2 = new ByteArrayInputStream(excelFile);
            InputStream xlsInputStream3 = new ByteArrayInputStream(excelFile);
            
            List<ItemModel> itemList = new ArrayList<ItemModel>();
            List<DocModel> docList1 = new ArrayList<DocModel>();
            List<DocModel> docList2 = new ArrayList<DocModel>();
            List<DocModel> docList3 = new ArrayList<DocModel>();
            //DocModel docModel = new DocModel();
            Map<String, Object> beans1 = new HashMap<>();
            Map<String, Object> beans2 = new HashMap<>();
            Map<String, Object> beans3 = new HashMap<>();
            beans1.put("docList", docList1);
            beans2.put("docList", docList2);
            beans3.put("docList", docList3);
            
            reader1.read(xlsInputStream1, beans1);
            reader2.read(xlsInputStream2, beans2);
            reader3.read(xlsInputStream3, beans3);
            
            int sheetCount = reader1.getSheetCount();
            for (int i = 0; i < sheetCount; i++)
            {
                ItemModel item = new ItemModel();
                item.setName(reader1.getSheetName(i));
                List<Object> resList1 = reader1.getResultByIdx(i);
                List<Object> resList2 = reader2.getResultByIdx(i);
                List<Object> resList3 = reader3.getResultByIdx(i);
                
                List<DocModel> allDoc = new ArrayList<DocModel>();
                if (resList1.size() > 0)
                {
                    int index = 0;
                    for (Object o : resList1)
                    {
                        DocModel docModel = (DocModel) o;
                        docModel.setScienceList(((DocModel) resList2.get(index)).getScienceList());
                        List<PartsEntity> partsList = docModel.getPartsList();
                        partsList.addAll(((DocModel) resList3.get(index)).getPartsList());
                        docModel.setPartsList(partsList);
                        allDoc.add(docModel);
                        index++;
                    }
                }
                //item.setDocList(allDoc);
                itemList.add(item);
            }
            return itemList;
        }
        catch (Exception e)
        {
            SxjLogger.error("读取excel文件错误", e, this.getClass());
            throw new ServiceException("读取excel文件错误", e);
        }
    }
    
    @Override
    @Transactional
    public void uploadExcel(String memberNo, String fileName, String filePath,
            String projectId, byte[] excelFile) throws ServiceException
    {
        try
        {
            ProjectEntity project = projectdao.getProject(projectId);
            if (project == null)
            {
                throw new ServiceException("工程信息不存在");
            }
            project.setFileCount(project.getFileCount() + 1);
            projectdao.updateProject(project);
            
            List<ItemModel> itemlList = readExecl(excelFile);
            List<GlassEntity> glassList = new ArrayList<GlassEntity>();
            List<ScienceEntity> scienceList = new ArrayList<ScienceEntity>();
            List<ProductEntity> productList = new ArrayList<ProductEntity>();
            List<PartsEntity> partsList = new ArrayList<PartsEntity>();
            ItemEntity projectItem = new ItemEntity();
            //projectItem.setFileName(fileName);
            projectItem.setFilePath(filePath);
            projectItem.setProjectId(projectId);
            projectItem.setUploadTime(new Date());
            projectItem.setCount(itemlList.size());
            //projectItemDao.addItem(projectItem);
            for (Iterator<ItemModel> iterator = itemlList.iterator(); iterator.hasNext();)
            {
                ItemModel itemModel = iterator.next();
                if (itemModel == null)
                {
                    continue;
                }
                ItemEntity item = itemModel.getItem();
                //item.setProjectItemId(projectItem.getId());
                itemDao.addItem(item);
                
                //List<DocModel> docList = itemModel.getDocList();
                List<DocModel> docList = new ArrayList<DocModel>();
                for (DocModel docModel : docList)
                {
                    DocEntity doc = docModel.getDoc();
                    doc.setMemberNo(memberNo);
                    doc.setProjectName(project.getName());
                    doc.setProjectId(project.getId());
                    doc.setItemId(item.getId());
                    docDao.addDoc(doc);
                    
                    if (docModel.getGlassList() != null
                            && docModel.getGlassList().size() > 0)
                    {
                        for (Iterator<GlassEntity> iterator2 = docModel.getGlassList()
                                .iterator(); iterator2.hasNext();)
                        {
                            GlassEntity glassEntity = iterator2.next();
                            glassEntity.setDocId(doc.getId());
                            if (glassEntity.hashCode() == 0)
                            {
                                iterator2.remove();
                            }
                            
                        }
                        glassList.addAll(docModel.getGlassList());
                    }
                    if (docModel.getScienceList() != null
                            && docModel.getScienceList().size() > 0)
                    {
                        for (Iterator<ScienceEntity> iterator2 = docModel.getScienceList()
                                .iterator(); iterator2.hasNext();)
                        {
                            ScienceEntity scienceEntity = iterator2.next();
                            scienceEntity.setDocId(doc.getId());
                            if (scienceEntity.hashCode() == 0)
                            {
                                iterator2.remove();
                            }
                            
                        }
                        scienceList.addAll(docModel.getScienceList());
                    }
                    if (docModel.getProductList() != null
                            && docModel.getProductList().size() > 0)
                    {
                        for (Iterator<ProductEntity> iterator2 = docModel.getProductList()
                                .iterator(); iterator2.hasNext();)
                        {
                            ProductEntity productEntity = iterator2.next();
                            productEntity.setDocId(doc.getId());
                            if (productEntity.hashCode() == 0)
                            {
                                iterator2.remove();
                            }
                            
                        }
                        productList.addAll(docModel.getProductList());
                    }
                    if (docModel.getPartsList() != null
                            && docModel.getPartsList().size() > 0)
                    {
                        for (Iterator<PartsEntity> iterator2 = docModel.getPartsList()
                                .iterator(); iterator2.hasNext();)
                        {
                            PartsEntity partsEntity = iterator2.next();
                            partsEntity.setDocId(doc.getId());
                            if (partsEntity.hashCode() == 0)
                            {
                                iterator2.remove();
                            }
                            
                        }
                        partsList.addAll(docModel.getPartsList());
                    }
                }
            }
            glassDao.addGlassList(glassList);
            scienceDao.addScienceList(scienceList);
            productDao.addProductList(productList);
            partsDao.addPartsList(partsList);
        }
        catch (Exception e)
        {
            SxjLogger.error("导入excel文件错误", e, this.getClass());
            throw new ServiceException("导入excel文件错误", e);
        }
        
    }
}
