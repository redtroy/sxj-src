package com.sxj.science.dao.export;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IProjectDao
{
    @Insert
    public void addProject(ProjectEntity project) throws SQLException;
    
    @Update
    public void updateProject(ProjectEntity project) throws SQLException;
    
    @Delete
    public void deleteProject(String id) throws SQLException;
    
    @Get
    public ProjectEntity getProject(String id) throws SQLException;
    
    public List<ProjectEntity> query(QueryCondition<ProjectEntity> query)
            throws SQLException;
    
    public Integer queryFileCount(QueryCondition<ProjectEntity> query)
            throws SQLException;
}
