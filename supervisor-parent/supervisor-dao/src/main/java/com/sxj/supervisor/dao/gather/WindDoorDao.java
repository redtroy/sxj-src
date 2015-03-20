package com.sxj.supervisor.dao.gather;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.gather.WindDoorEntity;

public interface WindDoorDao {

	/**
	 * 新增门窗采集信息
	 */
	@Insert
	public void addWindDoor(WindDoorEntity wd);

	/**
	 * 最新数据的排序查询
	 */
	public List<WindDoorEntity> getMaxWindDoor();
}
