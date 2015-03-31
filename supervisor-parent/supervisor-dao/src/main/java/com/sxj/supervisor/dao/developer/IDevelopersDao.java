package com.sxj.supervisor.dao.developer;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.developers.DevelopersEntity;

/**
 * 省内开发商DAO 
 * @author Administrator
 *
 */
public interface IDevelopersDao
{
    /**
     * 添加开发商
     * @param developers
     */
    @Insert
    public void addDevelopers(DevelopersEntity developers);
    
    /**
     * 更新开发商
     * @param developers
     */
    @Update
    public void updateDevelopers(DevelopersEntity developers);
    
    /**
     * 删除开发商
     * @param id
     */
    @Delete
    public void delDevelopers(String id);
}
