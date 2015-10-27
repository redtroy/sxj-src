package com.sxj.science.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import com.sxj.science.service.IDocService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class DocServiceImpl implements IDocService
{
    
    @Autowired
    private IDocDao docDao;
    
    @Autowired
    private IProjectDao projectDao;
    
    @Autowired
    private IItemDao itemDao;
    
    @Autowired
    private IGlassDao glassDao;
    
    @Autowired
    private IPartsDao partsDao;
    
    @Autowired
    private IProductDao productDao;
    
    @Autowired
    private IScienceDao scienceDao;
    
    private IDocService self;
    
    @Autowired
    private ApplicationContext context;
    
    @PostConstruct
    public void init()
    {
        self = context.getBean(IDocService.class);
    }
    
    @Override
    public void addDocModel(DocModel docModel) throws ServiceException
    {
        try
        {
            if (docModel == null)
            {
                throw new ServiceException("工程批次不能为空");
            }
            List<GlassEntity> glassList = docModel.getGlassList();
            List<ScienceEntity> scienceList = docModel.getScienceList();
            List<ProductEntity> productList = docModel.getProductList();
            List<PartsEntity> partsList = docModel.getPartsList();
            DocEntity doc = docModel.getDoc();
            ProjectEntity project = projectDao.getProject(doc.getProjectId());
            project.setBatchCount(project.getBatchCount() + 1);
            project.setState(1);
            projectDao.updateProject(project);
            
            ItemEntity item = itemDao.getItem(doc.getItemId());
            item.setCount(item.getCount() + 1);
            item.setState(1);
            itemDao.updateItem(item);
            //doc.setMemberNo(memberNo);
            //doc.setProjectName(project.getName());
            // doc.setProjectId(project.getId());
            // doc.setItemId(item.getId());
            docDao.addDoc(doc);
            
            if (glassList != null && glassList.size() > 0)
            {
                for (Iterator<GlassEntity> iterator2 = glassList.iterator(); iterator2.hasNext();)
                {
                    GlassEntity glassEntity = iterator2.next();
                    glassEntity.setDocId(doc.getId());
                }
                glassDao.addGlassList(glassList);
            }
            if (scienceList != null && scienceList.size() > 0)
            {
                for (Iterator<ScienceEntity> iterator2 = scienceList.iterator(); iterator2.hasNext();)
                {
                    ScienceEntity scienceEntity = iterator2.next();
                    scienceEntity.setDocId(doc.getId());
                }
                scienceDao.addScienceList(scienceList);
            }
            if (productList != null && productList.size() > 0)
            {
                for (Iterator<ProductEntity> iterator2 = productList.iterator(); iterator2.hasNext();)
                {
                    ProductEntity productEntity = iterator2.next();
                    productEntity.setDocId(doc.getId());
                }
                productDao.addProductList(productList);
            }
            if (partsList != null && partsList.size() > 0)
            {
                for (Iterator<PartsEntity> iterator2 = partsList.iterator(); iterator2.hasNext();)
                {
                    PartsEntity partsEntity = iterator2.next();
                    partsEntity.setDocId(doc.getId());
                    
                }
                partsDao.addPartsList(partsList);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("新增工程批次错误", e, this.getClass());
            throw new ServiceException("新增工程批次错误", e);
        }
        
    }
    
    @Override
    public void editDocModel(DocModel doc) throws ServiceException
    {
        try
        {
            if (doc == null)
            {
                throw new ServiceException("工程批次不能为空");
            }
            docDao.deleteDocById(doc.getId());
            glassDao.deleteGlassByDocId(doc.getId());
            partsDao.deletePartsByDocId(doc.getId());
            productDao.deleteProductByDocId(doc.getId());
            scienceDao.deleteScienceByDocId(doc.getId());
            doc.setId("");
            self.addDocModel(doc);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除工程批次错误", e, this.getClass());
            throw new ServiceException("删除工程批次错误", e);
        }
        
    }
    
    @Override
    public List<DocModel> queryDocModel(String[] docIds)
            throws ServiceException
    {
        try
        {
            QueryCondition<DocEntity> query = new QueryCondition<>();
            query.addCondition("ids", docIds);
            List<DocEntity> docList = docDao.query(query);
            List<DocModel> list = new ArrayList<DocModel>();
            for (DocEntity docEntity : docList)
            {
                DocModel model = new DocModel();
                QueryCondition<GlassEntity> g_query = new QueryCondition<>();
                g_query.addCondition("docId", docEntity.getId());
                List<GlassEntity> glassList = glassDao.query(g_query);
                
                QueryCondition<PartsEntity> p_query = new QueryCondition<>();
                p_query.addCondition("docId", docEntity.getId());
                List<PartsEntity> partsList = partsDao.query(p_query);
                
                QueryCondition<ProductEntity> pd_query = new QueryCondition<>();
                pd_query.addCondition("docId", docEntity.getId());
                List<ProductEntity> productList = productDao.query(pd_query);
                
                QueryCondition<ScienceEntity> s_query = new QueryCondition<>();
                s_query.addCondition("docId", docEntity.getId());
                List<ScienceEntity> scienceList = scienceDao.query(s_query);
                model.setDoc(docEntity);
                model.setScienceList(scienceList);
                model.setPartsList(partsList);
                model.setProductList(productList);
                model.setGlassList(glassList);
                list.add(model);
            }
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询下料信息错误", e, this.getClass());
            throw new ServiceException("查询下料信息错误", e);
        }
    }
    
    @Override
    public List<DocModel> queryDocModel(String itemId) throws ServiceException
    {
        try
        {
            QueryCondition<DocEntity> query = new QueryCondition<>();
            query.addCondition("itemId", itemId);
            List<DocEntity> docList = docDao.query(query);
            List<DocModel> list = new ArrayList<DocModel>();
            for (DocEntity docEntity : docList)
            {
                DocModel model = new DocModel();
                QueryCondition<GlassEntity> g_query = new QueryCondition<>();
                g_query.addCondition("docId", docEntity.getId());
                List<GlassEntity> glassList = glassDao.query(g_query);
                
                QueryCondition<PartsEntity> p_query = new QueryCondition<>();
                p_query.addCondition("docId", docEntity.getId());
                List<PartsEntity> partsList = partsDao.query(p_query);
                
                QueryCondition<ProductEntity> pd_query = new QueryCondition<>();
                pd_query.addCondition("docId", docEntity.getId());
                List<ProductEntity> productList = productDao.query(pd_query);
                
                QueryCondition<ScienceEntity> s_query = new QueryCondition<>();
                s_query.addCondition("docId", docEntity.getId());
                List<ScienceEntity> scienceList = scienceDao.query(s_query);
                model.setDoc(docEntity);
                model.setScienceList(scienceList);
                model.setPartsList(partsList);
                model.setProductList(productList);
                model.setGlassList(glassList);
                list.add(model);
            }
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询下料信息错误", e, this.getClass());
            throw new ServiceException("查询下料信息错误", e);
        }
    }
    
    @Override
    public DocModel getDocModel(String docId) throws ServiceException
    {
        try
        {
            DocModel model = new DocModel();
            DocEntity doc = docDao.getDoc(docId);
            QueryCondition<GlassEntity> g_query = new QueryCondition<>();
            g_query.addCondition("docId", doc.getId());
            List<GlassEntity> glassList = glassDao.query(g_query);
            
            QueryCondition<PartsEntity> p_query = new QueryCondition<>();
            p_query.addCondition("docId", doc.getId());
            List<PartsEntity> partsList = partsDao.query(p_query);
            
            QueryCondition<ProductEntity> pd_query = new QueryCondition<>();
            pd_query.addCondition("docId", doc.getId());
            List<ProductEntity> productList = productDao.query(pd_query);
            
            QueryCondition<ScienceEntity> s_query = new QueryCondition<>();
            s_query.addCondition("docId", doc.getId());
            List<ScienceEntity> scienceList = scienceDao.query(s_query);
            model.setDoc(doc);
            model.setScienceList(scienceList);
            model.setPartsList(partsList);
            model.setProductList(productList);
            model.setGlassList(glassList);
            return model;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询下料信息错误", e, this.getClass());
            throw new ServiceException("查询下料信息错误", e);
        }
    }
    
}
