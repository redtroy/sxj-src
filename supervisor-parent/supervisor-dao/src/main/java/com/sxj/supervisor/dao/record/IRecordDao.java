package com.sxj.supervisor.dao.record;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRecordDao {
	/**
	 * 新增备案
	 * @param record
	 */
	@Insert
	public void addRecord(RecordEntity record);

	/**
	 * 修改备案
	 * @param record
	 */
	@Update
	public void updateRecord(RecordEntity record);

	/**
	 * 查询备案
	 * @param id
	 * @return
	 */
	@Get
	public RecordEntity getRecord(String id);

	/**
	 * 删除备案
	 * @param id
	 */
	@Delete
	public void deleteRecord(String id);

	/**
	 * 备案高级查询
	 * @param query
	 * @return
	 */
	public List<RecordEntity> queryRecord(QueryCondition<RecordEntity> query);
}
