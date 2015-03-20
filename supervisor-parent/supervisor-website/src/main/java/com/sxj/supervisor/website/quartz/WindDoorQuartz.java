package com.sxj.supervisor.website.quartz;

import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.supervisor.service.tasks.IWindDoorService;

public class WindDoorQuartz {

	@Autowired
	private IWindDoorService wds;

	public void gather() {
		try {
			wds.WindDoorGather();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
