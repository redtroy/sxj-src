package com.sxj.finance.dao.member;

import com.sxj.finance.entity.member.MemberInfoEntity;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;

public interface IMemberInfoDao {
	/**
	 * 添加基本信息
	 *
	 * @param account
	 **/
	@Insert
	public void addMemberInfo(MemberInfoEntity memberinfo);

	/**
	 * 修改基本信息
	 *
	 * @param account
	 **/
	@Update
	public void updateMemberInfo(MemberInfoEntity memberinfo);

	/**
	 * 获取基本信息
	 *
	 * @param id
	 **/
	public MemberInfoEntity getMemberInfo(String memberNo);
}
