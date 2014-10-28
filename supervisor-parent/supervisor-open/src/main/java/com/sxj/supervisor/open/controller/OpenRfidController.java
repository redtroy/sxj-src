package com.sxj.supervisor.open.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;

@Controller
@RequestMapping("/rfid")
public class OpenRfidController {

	/**
	 * 登陆
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "login")
	public @ResponseBody Map<String, Object> login(String userId,
			String password) {
		return null;
	}

	/**
	 * 注销
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "logout")
	public @ResponseBody Map<String, Object> logout(String userId) {
		return null;
	}

	/**
	 * 获取批次信息
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "info/batch/{rfidNo}")
	public @ResponseBody Map<String, Object> getRfidBatchInfo(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		ContractModel model = new ContractModel();
		ContractEntity contract = new ContractEntity();
		contract.setContractNo("CT000001");
		model.setContract(contract);
		List<ContractBatchModel> batchList = new ArrayList<>();

		ContractBatchModel batch1 = new ContractBatchModel();
		ContractBatchEntity batchEn1 = new ContractBatchEntity();
		List<BatchItemModel> batchItems1 = new ArrayList<BatchItemModel>();
		BatchItemModel item1 = new BatchItemModel();
		item1.setProductModel("中空玻璃0001");
		item1.setQuantity(new Float("2211.22"));
		batchItems1.add(item1);

		BatchItemModel item2 = new BatchItemModel();
		item2.setProductModel("中空玻璃0002");
		item2.setQuantity(new Float("1000"));
		batchItems1.add(item2);

		batchEn1.setBatchNo("1");
		batch1.setBatch(batchEn1);
		batch1.setBatchItems(batchItems1);

		ContractBatchModel batch2 = new ContractBatchModel();
		ContractBatchEntity batchEn2 = new ContractBatchEntity();
		List<BatchItemModel> batchItems2 = new ArrayList<BatchItemModel>();
		BatchItemModel item11 = new BatchItemModel();
		item11.setProductModel("型材玻璃0001");
		item11.setQuantity(new Float("1111.11"));
		batchItems2.add(item11);

		BatchItemModel item12 = new BatchItemModel();
		item12.setProductModel("型材玻璃0002");
		item12.setQuantity(new Float("2000"));
		batchItems2.add(item12);

		batchEn2.setBatchNo("2");
		batch2.setBatch(batchEn2);
		batch2.setBatchItems(batchItems2);

		batchList.add(batch1);
		batchList.add(batch2);

		model.setBatchList(batchList);
		map.put("model", model);
		return map;
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contratcNo", "CT000001");
		map.put("rfidNo", "AAAA00001");
		map.put("winType", "C120 60*120");
		return map;
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
		return null;

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
		map.put("contractNo", "CT1410250001");
		String[] batchNos = new String[2];
		batchNos[0] = "AAAA0001";
		batchNos[1] = "AAAA0002";
		map.put("batchNo", batchNos);
		return map;

	}

	/**
	 * 安装
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "setup/{rfidNo}")
	public @ResponseBody Map<String, Object> setupRfid(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("contractNo", "CT1410250001");
		map.put("batchNo", "00001");
		return map;

	}

}
