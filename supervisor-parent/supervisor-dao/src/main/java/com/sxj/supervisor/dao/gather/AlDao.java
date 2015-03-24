package com.sxj.supervisor.dao.gather;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.gather.AlEntity;

public interface AlDao {

	/**
	 * 插入新数据
	 */
	@Insert
	public void addAl(AlEntity al);

	/**
	 * 获取最新30条数据
	 */
	public List<AlEntity> getAl();
}
