package com.sxj.supervisor.dao.gather;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.gather.AlEntity;

public interface AlDao {

	/**
	 * 插入新数据
	 */
	@Insert
	public void addAl(AlEntity al);
}
