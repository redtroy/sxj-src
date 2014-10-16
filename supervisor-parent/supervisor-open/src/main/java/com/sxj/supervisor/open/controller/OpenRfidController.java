package com.sxj.supervisor.open.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/rfid")
public class OpenRfidController {

	/**
	 * 获取批次信息
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "info/batch/{rfidNo}")
	public Map<String, Object> getRfidBatchInfo(@PathVariable String rfidNo) {
		return null;
	}
	

	/**
	 * 发货
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "send/{rfidNo}")
	public Map<String, Object> sendGoods(@PathVariable String rfidNo) {
		return null;
	}
	
	/**
	 * 获取合同规格信息
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "info/contract/{rfidNo}")
	public Map<String, Object> getRfidContractInfo(@PathVariable String rfidNo) {
		return null;
	}
	
	/**
	 * 验收
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "check/{rfidNo}")
	public Map<String, Object> checkAndAccept(@PathVariable String rfidNo){
		return null;
	}
	
	

}
