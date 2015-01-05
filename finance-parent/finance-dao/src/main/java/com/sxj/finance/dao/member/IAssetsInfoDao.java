package com.sxj.finance.dao.member;

import com.sxj.finance.entity.member.AssetsInfoEntity;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;

public interface IAssetsInfoDao {

	/**
	 * 添加资产信息
	 *
	 * @param account
	 **/
	@Insert
	public void addAssetsInfo(AssetsInfoEntity assets);

	/**
	 * 修改资产信息
	 *
	 * @param account
	 **/
	@Update
	public void updateAssetsInfo(AssetsInfoEntity assets);

	/**
	 * 获取资产信息
	 *
	 * @param id
	 **/
	@Get
	public AssetsInfoEntity getAssetsInfo(String id);

}
