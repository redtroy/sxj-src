package com.sxj.science.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.science.dao.export.IAloneOptimDao;
import com.sxj.science.dao.export.IDocDao;
import com.sxj.science.dao.export.IGlassDao;
import com.sxj.science.dao.export.IItemDao;
import com.sxj.science.dao.export.IPartsDao;
import com.sxj.science.dao.export.IProductDao;
import com.sxj.science.dao.export.IProjectDao;
import com.sxj.science.dao.export.IScienceDao;
import com.sxj.science.entity.export.DocEntity;
import com.sxj.science.entity.export.ItemEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.model.ItemModel;
import com.sxj.science.model.ProjectQuery;
import com.sxj.science.service.IProjectService;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class ProjectServiceImpl implements IProjectService
{
    
    @Autowired
    private IProjectDao projectDao;
    
    @Autowired
    private IItemDao itemDao;
    
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
    private IAloneOptimDao optimDao;
    
    private IProjectService self;
    
    @Autowired
    private ApplicationContext context;
    
    @PostConstruct
    public void init()
    {
        self = context.getBean(IProjectService.class);
    }
    
    @Override
    public void addProject(ProjectEntity project) throws ServiceException
    {
        try
        {
            project.setNoType("GC" + DateTimeUtils.getTime("yyMM"));
            project.setState(0);
            project.setBatchCount(1);
            project.setUploadTime(new Date());
            projectDao.addProject(project);
            
            ItemEntity item = new ItemEntity();
            item.setProjectId(project.getId());
            item.setName("第一批次");
            item.setCount(0);
            item.setUploadTime(new Date());
            item.setState(0);
            itemDao.addItem(item);
        }
        catch (Exception e)
        {
            SxjLogger.error("添加工程错误", e, this.getClass());
            throw new ServiceException("添加工程错误", e);
        }
        
    }
    
    @Override
    public void addItem(ItemEntity item) throws ServiceException
    {
        try
        {
            itemDao.addItem(item);
        }
        catch (Exception e)
        {
            SxjLogger.error("添加工程批次错误", e, this.getClass());
            throw new ServiceException("添加工程批次错误", e);
        }
        
    }
    
    @Override
    public List<ProjectEntity> query(ProjectQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<ProjectEntity> condition = new QueryCondition<>();
            condition.addAllCondition(query);
            condition.setPage(query);
            List<ProjectEntity> list = projectDao.query(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程错误", e, this.getClass());
            throw new ServiceException("查询工程错误", e);
        }
    }
    
    @Override
    public List<ItemModel> queryItems(String projectId) throws ServiceException
    {
        try
        {
            List<ItemModel> modelList = new ArrayList<ItemModel>();
            QueryCondition<ItemEntity> query = new QueryCondition<>();
            query.addCondition("projectId", projectId);
            List<ItemEntity> itemList = itemDao.query(query);
            if (itemList != null && itemList.size() > 0)
            {
                for (ItemEntity item : itemList)
                {
                    QueryCondition<DocEntity> queryItem = new QueryCondition<>();
                    queryItem.addCondition("itemId", item.getId());
                    List<DocEntity> docList = docDao.openQuery(queryItem);
                    for (DocEntity docEntity : docList)
                    {
                        if (docEntity.getState() == 0)
                        {
                            item.setState(0);
                        }
                        if (docEntity.getState() == 1)
                        {
                            item.setState(1);
                        }
                        if (docEntity.getState() == 2)
                        {
                            item.setState(2);
                        }
                    }
                    ItemModel model = new ItemModel();
                    model.setItem(item);
                    model.setDocList(docList);
                    modelList.add(model);
                }
            }
            return modelList;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程错误", e, this.getClass());
            throw new ServiceException("查询工程错误", e);
        }
        
    }
    
    @Override
    public void removeProject(String id) throws ServiceException
    {
        try
        {
            List<ItemModel> items = queryItems(id);
            for (ItemModel item : items)
            {
                self.removeItem(item.getId());
            }
            optimDao.deleteAloneOptimByProject(id);
            projectDao.deleteProject(id);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除工程错误", e, this.getClass());
            throw new ServiceException("删除工程错误", e);
        }
        
    }
    
    @Override
    public void removeItem(String itemId) throws ServiceException
    {
        try
        {
            List<String> docIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(itemId))
            {
                ItemEntity item = itemDao.getItem(itemId);
                ProjectEntity project = projectDao.getProject(item.getProjectId());
                project.setBatchCount(project.getBatchCount() - 1);
                projectDao.updateProject(project);
                
                itemDao.deleteItem(itemId);
                QueryCondition<DocEntity> query = new QueryCondition<>();
                query.addCondition("itemId", itemId);
                List<DocEntity> docList = docDao.query(query);
                for (DocEntity doc : docList)
                {
                    docIds.add(doc.getId());
                }
                
            }
            if (docIds.size() > 0)
            {
                docDao.deleteDoc(docIds.toArray(new String[docIds.size()]));
                glassDao.deleteGlass(docIds.toArray(new String[docIds.size()]));
                partsDao.deleteParts(docIds.toArray(new String[docIds.size()]));
                productDao.deleteProduct(docIds.toArray(new String[docIds.size()]));
                scienceDao.deleteScience(docIds.toArray(new String[docIds.size()]));
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("删除下料单错误", e, this.getClass());
            throw new ServiceException("删除下料单错误", e);
        }
        
    }
    
    @Override
    public void modifyProject(ProjectEntity project) throws ServiceException
    {
        try
        {
            projectDao.updateProject(project);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改工程错误", e, this.getClass());
            throw new ServiceException("修改工程错误", e);
        }
        
    }
    
    @Override
    public void modifyItem(ItemEntity item) throws ServiceException
    {
        try
        {
            itemDao.updateItem(item);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改工程批次错误", e, this.getClass());
            throw new ServiceException("修改工程批次错误", e);
        }
        
    }
    
    @Override
    public ProjectEntity getProject(String id) throws ServiceException
    {
        try
        {
            return projectDao.getProject(id);
        }
        catch (Exception e)
        {
            SxjLogger.error("获取工程信息错误", e, this.getClass());
            throw new ServiceException("获取工程信息错误", e);
        }
    }
    
    @Override
    public List<ItemEntity> getItem(String[] itemIds) throws ServiceException
    {
        try
        {
            if (itemIds == null || itemIds.length == 0)
            {
                throw new ServiceException("未选择下料单文件");
            }
            QueryCondition<ItemEntity> query = new QueryCondition<>();
            query.addCondition("ids", itemIds);
            return itemDao.query(query);
        }
        catch (Exception e)
        {
            SxjLogger.error("获取下料单错误", e, this.getClass());
            throw new ServiceException("获取下料单错误", e);
        }
    }
    
    @Override
    public Integer queryFileCount(ProjectQuery query) throws ServiceException
    {
        try
        {
            QueryCondition<ProjectEntity> condition = new QueryCondition<>();
            condition.addAllCondition(query);
            condition.setPage(query);
            Integer count = projectDao.queryFileCount(condition);
            return count;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程错误", e, this.getClass());
            throw new ServiceException("查询工程错误", e);
        }
    }
    
    @Override
    public List<ProjectEntity> openQueryProject(ProjectQuery query)
    {
        try
        {
            QueryCondition<ProjectEntity> condition = new QueryCondition<>();
            condition.addAllCondition(query);
            condition.setPage(query);
            List<ProjectEntity> list = projectDao.openQueryProject(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程错误", e, this.getClass());
            throw new ServiceException("查询工程错误", e);
        }
    }
    
    @Override
    public void deleteProject(String id) throws SQLException
    {
        this.projectDao.deleteProject(id);
    }
    
    @Override
    public void deleteProjectItem(String id) throws SQLException
    {
        this.itemDao.deleteItem(id);
    }
    
    @Override
    public void updateProject(ProjectEntity temPro) throws SQLException
    {
        projectDao.updateProject(temPro);
    }
    
    @Override
    public void updateItem(ItemEntity temItem) throws SQLException
    {
        itemDao.updateItem(temItem);
    }
    
    @Override
    public ItemEntity getItemById(String id)
    {
        return itemDao.getItem(id);
    }
    
}
