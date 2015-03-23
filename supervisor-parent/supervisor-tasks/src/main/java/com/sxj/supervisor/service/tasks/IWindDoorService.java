package com.sxj.supervisor.service.tasks;

import com.sxj.supervisor.entity.gather.WindDoorEntity;

public interface IWindDoorService {

	/**
	 * 门窗采集
	 */
	public void WindDoorGather();

	/**
	 * 获取信息
	 */
	public WindDoorEntity getInfoById(String id);
}
