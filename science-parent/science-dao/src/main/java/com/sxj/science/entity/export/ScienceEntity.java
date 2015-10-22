package com.sxj.science.entity.export;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.dao.export.IScienceDao;
import com.sxj.util.common.StringUtils;

@Entity(mapper = IScienceDao.class)
@Table(name = "S_SCIENCE")
public class ScienceEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6428979670131707293L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "USED")
    private String used;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "LENGTH")
    private String length;
    
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
    
    public String getUsed()
    {
        return used;
    }
    
    public void setUsed(String used)
    {
        this.used = used;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getLength()
    {
        return length;
    }
    
    public void setLength(String length)
    {
        this.length = length;
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
        String res = this.getUsed() + this.getName() + this.getType()
                + this.getLength() + this.getQuantity() + this.getRemark();
        if (StringUtils.isEmpty(res) || "nullnullnullnullnullnull".equals(res))
        {
            return 0;
        }
        else
        {
            return result;
        }
    }
}
