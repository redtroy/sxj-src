package com.sxj.mybatis.shard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sxj.mybatis.shard.entity.Blog;
import com.sxj.mybatis.shard.entity.BlogExample;

public interface BlogMapper {
    /**
     * 获取符合条件的记录数
     * @param example 查询条件对象
     * @return 记录数
     */
    int countByExample(BlogExample example);

    /**
     * 批量删除符合条件的记录
     * @param example 查询条件对象
     * @return 成功删除的数量
     */
    int deleteByExample(BlogExample example);

    /**
     * 根据主键删除
     * @param id 主键值
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增记录
     * @param record 待新增的对象
     */
    int insert(Blog record);

    /**
     * 新增记录 <font color='blue'>（该操作为选择性的，只对record中不为空的属性生成sql语句）</font>
     * @param record 待新增的对象
     */
    int insertSelective(Blog record);

    List<Blog> selectByExampleWithBLOBs(BlogExample example);

    /**
     * 批量查询
     * @param example 查询条件对象
     */
    List<Blog> selectByExample(BlogExample example);

    /**
     * 根据主键查询
     * @param id 主键值
     */
    Blog selectByPrimaryKey(Integer id);

    /**
     * 指量更新,将符合条件的记录统一更新为record的值 <font color='blue'>（该操作为选择性的，只对record中不为空的属性生成sql语句）</font>
     * @param record 待更新到数据库的值
     * @param example 查询条件对象
     * @return 成功操作的记录数
     */
    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExampleWithBLOBs(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * 指量更新,将符合条件的记录统一更新为record的值 
     * @param record 待更新到数据库的值
     * @param example 查询条件对象
     * @return 成功操作的记录数
     */
    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    /**
     * 根据主键更新记录 <font color='blue'>（该操作为选择性的，只对record中不为空的属性生成sql语句）</font>
     * @param record 记录
     */
    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    /**
     * 根据主键更新记录
     * @param record 记录
     */
    int updateByPrimaryKey(Blog record);
    
    
    
}