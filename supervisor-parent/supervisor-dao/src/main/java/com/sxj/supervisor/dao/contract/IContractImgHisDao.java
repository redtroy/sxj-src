package com.sxj.supervisor.dao.contract;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ContractImgHisEntity;
import com.sxj.util.persistent.QueryCondition;

/**
 * 变更合同扫面件
 * @author Ann
 *
 */
public interface IContractImgHisDao {
	/**
	 * 新增合同扫描件
	 *
	 * @param    image
	**/
	@Insert
	public void addImage(ContractImgHisEntity image);
	
	/**
	 * 删除合同扫描件
	 *
	 * @param    id
	**/
	@Delete
	public void deleteImage(String id);
	/**
	 * 多条件查询扫描件
	 */
	public List<ContractImgHisEntity> queryImages(QueryCondition<ContractImgHisEntity> query);
	/**
	 * 查询扫描件
	 * @param id
	 * @return
	 */
	@Get
	public ContractImgHisEntity getImage(String id);
	/**
	 * 更新扫描件
	 * @param image
	 */
	@Update
	public void updateImage(ContractImgHisEntity image);
}
