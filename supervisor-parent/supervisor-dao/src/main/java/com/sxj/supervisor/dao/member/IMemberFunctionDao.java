package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IMemberFunctionDao {
	/**
	 * 获取会员功能信息
	 * 
	 * @param id
	 * @return
	 */
	@Get
	public MemberFunctionEntity getFunction(String id);

	/**
	 * 添加会员功能
	 *
	 * @param function
	 **/
	@Insert
	public void addFunction(MemberFunctionEntity function);

	/**
	 * 修改会员功能
	 *
	 * @param function
	 **/
	@Update
	public void updateFunction(MemberFunctionEntity function);

	/**
	 * 删除会员功能
	 *
	 * @param id
	 **/
	@Delete
	public void deleteFunction(String id);

	/**
	 * 会员功能高级查询
	 *
	 * @param function
	 **/
	public List<MemberFunctionEntity> queryFunctions(
			QueryCondition<MemberFunctionEntity> query);

}
