package com.sxj.science.entity.export;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.dao.export.IGlassDao;
import com.sxj.util.common.StringUtils;

/**
     * 
     */
@Entity(mapper = IGlassDao.class)
@Table(name = "S_GLASS")
public class GlassEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2260837266554298019L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "WIDTH")
    private String width;
    
    @Column(name = "HEIGHT")
    private String height;
    
    @Column(name = "QUANTITY")
    private String quantity;
    
    @Column(name = "TYPE")
    private String type;
    
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
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
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
        String res = this.getHeight() + this.getWidth() + this.getQuantity()
                + this.getType();
        if (StringUtils.isEmpty(res) || "nullnullnullnull".equals(res))
        {
            return 0;
        }
        else
        {
            return result;
        }
    }
}
