package com.sxj.supervisor.website.quartz;

import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.supervisor.service.tasks.IAlGather;

public class AlGatherQuartz {

	@Autowired
	private IAlGather ag;

	public void gather() {
		try {
			ag.gather();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
