package com.sxj.supervisor.dao.record;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IRecordDao {
	/**
	 * 新增备案
	 * 
	 * @param record
	 */
	@Insert
	public void addRecord(RecordEntity record);

	/**
	 * 修改备案
	 * 
	 * @param record
	 */
	@Update
	public void updateRecord(RecordEntity record);

	/**
	 * 查询备案
	 * 
	 * @param id
	 * @return
	 */
	@Get
	public RecordEntity getRecord(String id);

	/**
	 * 删除备案
	 * 
	 * @param id
	 */
	@Delete
	public void deleteRecord(String id);

	/**
	 * 备案高级查询
	 * 
	 * @param query
	 * @return
	 */
	public List<RecordEntity> queryRecord(QueryCondition<RecordEntity> query);

	/**
	 * 合同进度
	 * 
	 * @param contractNo
	 * @return
	 */
	public String getProgress(String contractNo);

	/**
	 * 批量更新备案确认状态
	 * 
	 * @param contractNo
	 */
	public void batchUpdateConfirmState(String contractNo);

	/**
	 * 批量更新备案审核状态
	 * 
	 * @param contractNo
	 */
	public void batchUpdateCheckState(String contractNo);

	public RecordEntity queryUserRecord(String contractNo);
	
	/**
	 * 查询合同下备案绑定状态
	 * @param contratcNo
	 * @return
	 */
	public int getBindingState(String contractNo)throws SQLException;
	
	/**
	 * 清除备案与合同关联数据回滚状态
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	public int updateContractByRecordNo(String... record)throws SQLException;
	
}
