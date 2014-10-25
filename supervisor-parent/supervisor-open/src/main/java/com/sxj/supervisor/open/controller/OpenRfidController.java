package com.sxj.supervisor.open.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rfid")
public class OpenRfidController {

	/**
	 * 获取批次信息
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "info/batch/{rfidNo}")
	public @ResponseBody Map<String, Object> getRfidBatchInfo(
			@PathVariable String rfidNo) {
		return null;
	}

	/**
	 * 获取合同规格信息
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "info/contract/{rfidNo}")
	public @ResponseBody Map<String, Object> getRfidContractInfo(
			@PathVariable String rfidNo) {
		return null;
	}

	/**
	 * 发货
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "send/{rfidNo}")
	public @ResponseBody Map<String, Object> sendGoods(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("contractNo", "CT1410250001");
		map.put("batchNo", "00001");
		return map;
	}

	/**
	 * 验收
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "check/{rfidNo}")
	public @ResponseBody Map<String, Object> checkAndAccept(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("contractNo", "CT1410250001");
		map.put("batchNo", "00001");
		return map;
	}

	/**
	 * 质检
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "test/{rfidNo}")
	public @ResponseBody Map<String, Object> testRfid(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("contractNo", "CT1410250001");
		map.put("batchNo", "00001");
		return map;

	}

}
