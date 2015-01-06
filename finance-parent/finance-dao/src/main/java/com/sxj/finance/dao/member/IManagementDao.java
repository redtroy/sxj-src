package com.sxj.finance.dao.member;

import com.sxj.finance.entity.member.ManagementEntity;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;

public interface IManagementDao {
	/**
	 * 添加经营情况
	 *
	 * @param account
	 **/
	@Insert
	public void addManagement(ManagementEntity management);

	/**
	 * 修改经营情况
	 *
	 * @param account
	 **/
	@Update
	public void updateManagement(ManagementEntity management);

	/**
	 * 获取经营情况
	 *
	 * @param id
	 **/
	public ManagementEntity getManagement(String memberNo);

}
