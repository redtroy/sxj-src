package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.member.FunctionEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IFunctionDao {
	
	/**
	 * 获取系统功能信息
	 * @param id
	 * @return
	 */
	@Get
	public FunctionEntity getFunction(String id);
	/**
	 * 添加系统功能
	 *
	 * @param    function
	**/
	@Insert
	public void addFunction(FunctionEntity function);
	
	/**
	 * 修改系统功能
	 *
	 * @param    function
	**/
	@Update
	public void updateFunction(FunctionEntity function);
	
	/**
	 * 删除系统功能
	 *
	 * @param    id
	**/
	@Delete
	public void deleteFunction(String id);
	
	/**
	 * 系统功能高级查询
	 *
	 * @param    function
	**/
	public List<FunctionEntity> queryFunction(QueryCondition<FunctionEntity> query);
	

}
