package com.sxj.supervisor.entity.developers;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.developers.IDevelopersDao;
import com.sxj.util.common.StringUtils;

/**
 * 省内开发商
 * @author Administrator
 *
 */
@Entity(mapper = IDevelopersDao.class)
@Table(name = "M_DEVELOPERS")
public class DevelopersEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -9052072364305080730L;
    
    /**
     * 主键
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 开发商名称
     */
    @Column(name = "NAME")
    private String name;
    
    /**
     * 城市
     */
    @Column(name = "CITY")
    private String city;
    
    /**
     * 链接地址
     */
    @Column(name = "URL")
    private String url;
    
    /**
     * 公司地址
     */
    @Column(name = "ADDRESS")
    private String address;
    
    /**
     * 电话
     */
    @Column(name = "TEL_PHONE")
    private String telPhone;
    /**
     * 更新时间
     */
    @Column(name = "ENTRY_TIME")
    private Date entryTime;
    
    public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

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
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getTelPhone()
    {
        return telPhone;
    }
    
    public void setTelPhone(String telPhone)
    {
        this.telPhone = telPhone;
    }
    
}
