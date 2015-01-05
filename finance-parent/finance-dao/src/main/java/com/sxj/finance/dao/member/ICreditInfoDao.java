package com.sxj.finance.dao.member;

import com.sxj.finance.entity.member.CreditInfoEntity;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;

public interface ICreditInfoDao {
	/**
	 * 添加信用信息
	 *
	 * @param account
	 **/
	@Insert
	public void addCreditInfo(CreditInfoEntity creditinfo);

	/**
	 * 修改信用信息
	 *
	 * @param account
	 **/
	@Update
	public void updateCreditInfo(CreditInfoEntity creditinfo);

	/**
	 * 获取信用信息
	 *
	 * @param id
	 **/
	@Get
	public CreditInfoEntity getCreditInfo(String id);

}
