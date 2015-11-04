package com.sxj.science.dao.export;

import java.util.List;
import java.util.Map;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.WindowTypeEntity;
import com.sxj.util.persistent.QueryCondition;
//            QueryCondition<WindowTypeEntity> condition);
public interface IWindowTypeDao
{
    @Insert
    public void addWindowType(WindowTypeEntity doc);
    
    @Delete
    public void deleteWindowType(String id);
    
    @Update
    public void updateWindowType(WindowTypeEntity doc);
    
    @Get
    public WindowTypeEntity getWindowType(String id);

    public List<WindowTypeEntity> queryWindowType(
            QueryCondition<WindowTypeEntity> condition);

    public List<WindowTypeEntity> searchWindowType(
            Map<String,Object> map);
//            QueryCondition<WindowTypeEntity> condition);

    public List<WindowTypeEntity> openQueryWindowType(
            QueryCondition<WindowTypeEntity> condition);

    public List<WindowTypeEntity> autoWindowType(
            QueryCondition<WindowTypeEntity> condition);

}