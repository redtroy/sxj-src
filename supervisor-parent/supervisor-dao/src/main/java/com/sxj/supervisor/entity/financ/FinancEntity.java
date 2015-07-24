package com.sxj.supervisor.entity.financ;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.financ.IFinancDao;

@Entity(mapper = IFinancDao.class)
@Table(name = "M_FIANC_INFO")
public class FinancEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1554336156814240409L;
    
    /**
     * 主键标识
     **/
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    @Column(name = "CONTACTS_NAME")
    private String contactsName;
    
    @Column(name = "CONTACTS_TEL")
    private String contactsTel;
    
    @Column(name = "PROJECT_NAME")
    private String projectName;
    
    @Column(name = "CAPITAL")
    private String capital;
    
    @Column(name = "PROJECT_REMARK")
    private String projectRemark;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getContactsName()
    {
        return contactsName;
    }
    
    public void setContactsName(String contactsName)
    {
        this.contactsName = contactsName;
    }
    
    public String getContactsTel()
    {
        return contactsTel;
    }
    
    public void setContactsTel(String contactsTel)
    {
        this.contactsTel = contactsTel;
    }
    
    public String getProjectName()
    {
        return projectName;
    }
    
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
    
    public String getCapital()
    {
        return capital;
    }
    
    public void setCapital(String capital)
    {
        this.capital = capital;
    }
    
    public String getProjectRemark()
    {
        return projectRemark;
    }
    
    public void setProjectRemark(String projectRemark)
    {
        this.projectRemark = projectRemark;
    }
    
}
