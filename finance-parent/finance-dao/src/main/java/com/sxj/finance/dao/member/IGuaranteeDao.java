package com.sxj.finance.dao.member;

import com.sxj.finance.entity.member.GuaranteeEntity;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;

public interface IGuaranteeDao {
	/**
	 * 添加担保信息
	 *
	 * @param account
	 **/
	@Insert
	public void addGuarantee(GuaranteeEntity guarantee);

	/**
	 * 修改担保信息
	 *
	 * @param account
	 **/
	@Update
	public void updateGuarantee(GuaranteeEntity guarantee);

	/**
	 * 获取担保信息
	 *
	 * @param id
	 **/
	public GuaranteeEntity getGuarantee(String memberNo);

}
