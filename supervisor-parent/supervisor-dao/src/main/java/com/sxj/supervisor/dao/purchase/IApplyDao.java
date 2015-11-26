package com.sxj.supervisor.dao.purchase;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.purchase.ApplyEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IApplyDao {

	@Update
	public void updateApply(ApplyEntity appply);

	@Insert
	public void insertApply(ApplyEntity apply);

	public List<ApplyEntity> queryApplysList(QueryCondition<ApplyEntity> query);
}
