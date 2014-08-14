package com.sxj.supervisor.dao.contract;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractImgEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IContractImgDao {
	
	/**
	 * 新增合同图片
	 * @param image
	 */
	@Insert
	public void addImage(ContractImgEntity image);
	
	/**
	 * 删除合同图片
	 * @param id
	 */
	@Delete
	public void deleteImage(String id);
	
	/**
	 * 多条件查询合同图片
	 * @param query
	 */
	public void queryImages(QueryCondition<ContractImgEntity> query);
	
	/**
	 * 获取单个图片
	 * @param id
	 * @return
	 */
	@Get
	public ContractImgEntity getImage(String id);
	
}
