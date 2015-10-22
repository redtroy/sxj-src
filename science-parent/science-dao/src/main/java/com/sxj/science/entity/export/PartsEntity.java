
package com.sxj.science.entity.export;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.science.dao.export.IPartsDao;
import com.sxj.util.common.StringUtils;

@Entity(mapper = IPartsDao.class)
@Table(name = "S_PARTS")
public class PartsEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8696815258230369870L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "USED")
    private String used;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "QUANTITY")
    private String quantity;
    
    @Column(name = "UNIT")
    private String unit;
    
    @Column(name = "TECHONLOGY")
    private String techonlogy;
    
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
    
    public String getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    
    public String getUnit()
    {
        return unit;
    }
    
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    public String getTechonlogy()
    {
        return techonlogy;
    }
    
    public void setTechonlogy(String techonlogy)
    {
        this.techonlogy = techonlogy;
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
                + this.getUnit() + this.getQuantity() + this.getTechonlogy();
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
