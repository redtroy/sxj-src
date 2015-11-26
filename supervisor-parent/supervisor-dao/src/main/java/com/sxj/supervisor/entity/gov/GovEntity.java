package com.sxj.supervisor.entity.gov;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.gov.IGovDao;

/**
 * 政务信息
 * @author nishaotang
 *
 */
@Entity(mapper = IGovDao.class)
@Table(name = "M_GOV")
public class GovEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -9052072364305080730L;
    
    /**
     * 主键
     */
    @Id(column = "GOV_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String govId;
    
    /**
     * 标题
     */
    @Column(name = "TITLE")
    private String title;
    
    /**
     * 链接地址
     */
    @Column(name = "URL")
    private String url;
    
    /**
     * 更新时间
     */
    @Column(name = "CREATE_DATE")
    private Date createDate;
    
    public String getGovId()
    {
        return govId;
    }
    
    public void setGovId(String govId)
    {
        this.govId = govId;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
}
