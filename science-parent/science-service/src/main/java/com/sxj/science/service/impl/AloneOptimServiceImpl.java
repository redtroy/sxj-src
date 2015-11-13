package com.sxj.science.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.SheetsReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.science.dao.export.IAloneOptimDao;
import com.sxj.science.entity.export.AloneOptimEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.science.model.AloneOptimQuery;
import com.sxj.science.service.IAloneOptimService;
import com.sxj.science.service.IProjectService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class AloneOptimServiceImpl implements IAloneOptimService
{
    
    @Autowired
    private IAloneOptimDao optimDao;
    
    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Value("${excel.aloneOptim}")
    private String xmlConfig;
    
    @Override
    public void addAloneOptim(AloneOptimEntity alone) throws ServiceException
    {
        try
        {
            ProjectEntity project = projectService.getProject(alone.getProjectId());
            project.setFileCount(project.getFileCount() + 1);
            projectService.modifyProject(project);
            optimDao.addAloneOptim(alone);
        }
        catch (Exception e)
        {
            SxjLogger.error("添加单独优化文件错误", e, this.getClass());
            throw new ServiceException("添加单独优化文件错误", e);
        }
        
    }
    
    @Override
    public List<AloneOptimEntity> query(AloneOptimQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<AloneOptimEntity> condition = new QueryCondition<>();
            condition.addAllCondition(query);
            List<AloneOptimEntity> list = optimDao.query(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询单独优化文件错误", e, this.getClass());
            throw new ServiceException("查询单独优化文件错误", e);
        }
    }
    
    @Override
    public List<ScienceEntity> readExcel(String[] ids) throws ServiceException
    {
        try
        {
            AloneOptimQuery query = new AloneOptimQuery();
            query.setIds(ids);
            List<AloneOptimEntity> list = query(query);
            List<ScienceEntity> allScience = new ArrayList<ScienceEntity>();
            for (AloneOptimEntity aloneOptim : list)
            {
                String group = aloneOptim.getFilePath().substring(0,
                        aloneOptim.getFilePath().indexOf("/"));
                String id = aloneOptim.getFilePath()
                        .substring(aloneOptim.getFilePath().indexOf("/") + 1,
                                aloneOptim.getFilePath().length());
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                storageClientService.downloadFile(group, id, output);
                InputStream xmlInputStream1 = new FileInputStream(xmlConfig);
                SheetsReader reader1 = (SheetsReader) ReaderBuilder.buildSheetsReaderFromXML(xmlInputStream1);
                InputStream xlsInputStream1 = new ByteArrayInputStream(
                        output.toByteArray());
                output.close();
                List<ScienceEntity> scienceList = new ArrayList<ScienceEntity>();
                Map<String, Object> beans1 = new HashMap<>();
                beans1.put("scienceList", scienceList);
                reader1.read(xlsInputStream1, beans1);
                int sheetCount = reader1.getSheetCount();
                for (int i = 0; i < sheetCount; i++)
                {
                    List<Object> resList1 = reader1.getResultByIdx(i);
                    if (resList1.size() > 0)
                    {
                        for (Object o : resList1)
                        {
                            ScienceEntity scienceEntity = (ScienceEntity) o;
                            if (scienceEntity.hashCode() != 0)
                            {
                                allScience.add(scienceEntity);
                            }
                            
                        }
                    }
                }
            }
            return allScience;
        }
        catch (Exception e)
        {
            SxjLogger.error("读取单独优化文件错误", e, this.getClass());
            throw new ServiceException("读取单独优化文件错误", e);
        }
    }
    
    @Override
    public void removeAloneOptim(String id) throws ServiceException
    {
        try
        {
            AloneOptimEntity aloneOptim = optimDao.getAloneOptim(id);
            ProjectEntity project = projectService.getProject(aloneOptim.getProjectId());
            project.setFileCount(project.getFileCount() - 1);
            projectService.modifyProject(project);
            optimDao.deleteAloneOptim(id);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除单独优化文件错误", e, this.getClass());
            throw new ServiceException("删除单独优化文件错误", e);
        }
        
    }
    
    @Override
    public AloneOptimEntity getAloneOptim(String id) throws ServiceException
    {
        try
        {
            return optimDao.getAloneOptim(id);
        }
        catch (Exception e)
        {
            SxjLogger.error("获取单独优化文件错误", e, this.getClass());
            throw new ServiceException("获取单独优化文件错误", e);
        }
    }

    @Override
    public void updateAloneOptim(AloneOptimEntity temAlone)
    {
        this.optimDao.updateAloneOptim(temAlone);
    }
}
