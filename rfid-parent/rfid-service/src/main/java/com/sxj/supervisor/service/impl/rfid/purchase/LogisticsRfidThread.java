package com.sxj.supervisor.service.impl.rfid.purchase;

import java.util.List;
import java.util.concurrent.Callable;

import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;

public class LogisticsRfidThread implements Callable<Integer> {

	private List<LogisticsRfidEntity> batch;

	private ILogisticsRfidService rfidService;

	public LogisticsRfidThread(List<LogisticsRfidEntity> batch,
			ILogisticsRfidService service) {
		super();
		this.batch = batch;
		rfidService = service;
	}

	@Override
	public Integer call() throws Exception {
		return rfidService.batchAddLogistics(batch
				.toArray(new LogisticsRfidEntity[batch.size()]));
	}
}
