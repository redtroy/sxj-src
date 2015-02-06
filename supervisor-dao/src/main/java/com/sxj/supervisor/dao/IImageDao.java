package com.sxj.supervisor.dao;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.supervisor.entity.ImageEntity;

public interface IImageDao
{
    
    @BatchInsert
    public void addImages(List<ImageEntity> images) throws SQLException;
    
    public ImageEntity getImage(String md5) throws SQLException;
    
}
