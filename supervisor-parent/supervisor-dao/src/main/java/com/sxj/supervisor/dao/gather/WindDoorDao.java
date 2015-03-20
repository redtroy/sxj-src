package com.sxj.supervisor.dao.gather;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.gather.WindDoorEntity;

public interface WindDoorDao {

	/**
	 * 新增门窗采集信息
	 */
	@Insert
	public void addWindDoor(WindDoorEntity wd);
}
