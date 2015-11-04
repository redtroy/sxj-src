package com.sxj.science.entity.export;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.dao.export.IProductDao;
import com.sxj.util.common.StringUtils;

@Entity(mapper = IProductDao.class)
@Table(name = "S_P_PRODUCT")
public class ProductEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5931484307346883384L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "WIDTH")
    private String width;
    
    @Column(name = "HEIGHT")
    private String height;
    
    @Column(name = "QUANTITY")
    private String quantity;
    
    @Column(name = "REMARK")
    private String remark;
    
    @Column(name = "DOC_ID")
    private String docId;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getWidth()
    {
        return width;
    }
    
    public void setWidth(String width)
    {
        this.width = width;
    }
    
    public String getHeight()
    {
        return height;
    }
    
    public void setHeight(String height)
    {
        this.height = height;
    }
    
    public String getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getDocId()
    {
        return docId;
    }
    
    public void setDocId(String docId)
    {
        this.docId = docId;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        String res = this.getName() + this.getWidth() + this.getHeight()
                + this.getQuantity() + this.getRemark();
        if (StringUtils.isEmpty(res) || "nullnullnullnullnull".equals(res))
        {
            return 0;
        }
        else
        {
            return result;
        }
    }
}