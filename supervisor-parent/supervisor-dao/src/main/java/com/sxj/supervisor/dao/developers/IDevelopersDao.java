package com.sxj.supervisor.dao.developers;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.developers.DevelopersEntity;
import com.sxj.util.persistent.QueryCondition;

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
    public void addDevelopers(DevelopersEntity developer);
    
    /**
     * 更新开发商
     * @param developers
     */
    @Update
    public void updateDevelopers(DevelopersEntity developer);
    
    /**
     * 删除开发商
     * @param id
     */
    @Delete
    public void delDevelopers(String id);
    /**
	 * 查询开发商
	 * 
	 * @param id
	 * @return
	 */
	@Get
	public DevelopersEntity getDeveloper(String id);
	/**
	 * 开发商高级查询
	 * 
	 * @param query
	 * @return
	 */
	public List<DevelopersEntity> query(QueryCondition<DevelopersEntity> query);
	
	public List<DevelopersEntity> apiQueryDevelopers(QueryCondition<DevelopersEntity> query);
	
}
