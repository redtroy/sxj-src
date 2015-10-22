package com.sxj.science.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.entity.export.DocEntity;
import com.sxj.science.entity.export.GlassEntity;
import com.sxj.science.entity.export.PartsEntity;
import com.sxj.science.entity.export.ProductEntity;
import com.sxj.science.entity.export.ScienceEntity;

public class DocModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private DocEntity doc = new DocEntity();
    
    private List<ScienceEntity> scienceList = new ArrayList<>();
    
    private List<GlassEntity> glassList = new ArrayList<>();
    
    private List<ProductEntity> productList = new ArrayList<>();
    
    private List<PartsEntity> partsList = new ArrayList<>();
    
    public String getId()
    {
        return doc.getId();
    }
    
    public void setId(String id)
    {
        doc.setId(id);
    }
    
    public String getProjectId()
    {
        return doc.getProjectId();
    }
    
    public void setProjectId(String projectId)
    {
        doc.setProjectId(projectId);
    }
    
    public int getCurrentPage()
    {
        return doc.getCurrentPage();
    }
    
    public String getWindowId()
    {
        return doc.getWindowId();
    }
    
    public void setWindowId(String windowId)
    {
        doc.setWindowId(windowId);
    }
    
    public void setCurrentPage(int currentPage)
    {
        doc.setCurrentPage(currentPage);
    }
    
    public String getProjectName()
    {
        return doc.getProjectName();
    }
    
    public void setProjectName(String projectName)
    {
        doc.setProjectName(projectName);
    }
    
    public String getMemberNo()
    {
        return doc.getMemberNo();
    }
    
    public void setMemberNo(String memberNo)
    {
        doc.setMemberNo(memberNo);
    }
    
    public void setPagable(boolean pagable)
    {
        doc.setPagable(pagable);
    }
    
    public String getFilePath()
    {
        return doc.getFilePath();
    }
    
    public void setFilePath(String filePath)
    {
        doc.setFilePath(filePath);
    }
    
    public void setPage(Pagable pagable)
    {
        doc.setPage(pagable);
    }
    
    public String getWindowCode()
    {
        return doc.getWindowCode();
    }
    
    public void setWindowCode(String windowCode)
    {
        doc.setWindowCode(windowCode);
    }
    
    public String getColor()
    {
        return doc.getColor();
    }
    
    public void setColor(String color)
    {
        doc.setColor(color);
    }
    
    public String getQuantity()
    {
        return doc.getQuantity();
    }
    
    public void setQuantity(String quantity)
    {
        doc.setQuantity(quantity);
    }
    
    public String getSeries()
    {
        return doc.getSeries();
    }
    
    public void setSeries(String series)
    {
        doc.setSeries(series);
    }
    
    public String getWindowFaca()
    {
        return doc.getWindowFaca();
    }
    
    public void setWindowFaca(String windowFaca)
    {
        doc.setWindowFaca(windowFaca);
    }
    
    public List<ScienceEntity> getScienceList()
    {
        return scienceList;
    }
    
    public void setScienceList(List<ScienceEntity> scienceList)
    {
        this.scienceList = scienceList;
    }
    
    public List<GlassEntity> getGlassList()
    {
        return glassList;
    }
    
    public void setGlassList(List<GlassEntity> glassList)
    {
        this.glassList = glassList;
    }
    
    public List<ProductEntity> getProductList()
    {
        return productList;
    }
    
    public void setProductList(List<ProductEntity> productList)
    {
        this.productList = productList;
    }
    
    public List<PartsEntity> getPartsList()
    {
        return partsList;
    }
    
    public void setPartsList(List<PartsEntity> partsList)
    {
        this.partsList = partsList;
    }
    
    public DocEntity getDoc()
    {
        return doc;
    }
    
    public void setDoc(DocEntity doc)
    {
        this.doc = doc;
    }
    
    public String getItemId()
    {
        return doc.getItemId();
    }
    
    public void setItemId(String itemId)
    {
        doc.setItemId(itemId);
    }
    
}
