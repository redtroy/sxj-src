package com.sxj.supervisor.model.financ;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class FinanceQuery extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -4761799518689590252L;
    
    /**
     * 联系人姓名
     */
    private String contactsName;
    
    /**
     * 联系电话
     */
    private String contactsTel;
    
    /**
     * 项目名称
     */
    private String projectName;

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
    
}
