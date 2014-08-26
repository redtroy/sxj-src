package com.sxj.supervisor.manage.controller.record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.contract.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

	@Autowired
	IRecordService recordService;

	IContractService contractService;

	/**
	 * 备案管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/recordList")
	public String recordList(RecordQuery query, ModelMap map) {
		ContractTypeEnum[] cte = ContractTypeEnum.values(); // 合同类型
		RecordStateEnum[] rse = RecordStateEnum.values();// 备案状态
		RecordTypeEnum[] rte = RecordTypeEnum.values();// 备案类型
		List<RecordEntity> list = recordService.queryRecord(query);
		map.put("cte", cte);
		map.put("rse", rse);
		map.put("rte", rte);
		map.put("list", list);
		return "manage/record/record";
	}

	/**
	 * 备案修改
	 * 
	 * @return
	 */
	@RequestMapping("/record_edit")
	public String record_edit(String id, ModelMap map) {
		RecordEntity record = recordService.getRecord(id);
		ContractTypeEnum[] cte = ContractTypeEnum.values(); // 合同类型
		RecordTypeEnum[] rte = RecordTypeEnum.values();// 备案类型
		map.put("cte", cte);
		map.put("rte", rte);
		map.put("record", record);
		return "manage/record/record_edit";
	}

	/**
	 * 保存备案修改
	 * 
	 * @param record
	 * @param map
	 * @return
	 */
	@RequestMapping("/record_save")
	public String record_save(RecordEntity record, ModelMap map) {
		recordService.modifyRecord(record);
		ContractTypeEnum[] cte = ContractTypeEnum.values(); // 合同类型
		RecordStateEnum[] rse = RecordStateEnum.values();// 备案状态
		RecordTypeEnum[] rte = RecordTypeEnum.values();// 备案类型
		RecordQuery query = new RecordQuery();
		List<RecordEntity> list = recordService.queryRecord(query);
		map.put("cte", cte);
		map.put("rse", rse);
		map.put("rte", rte);
		map.put("list", list);
		return "manage/record/record";
	}

	/**
	 * 办绑定备案页面
	 * 
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping("/banding_edit")
	public String banding_edit(ModelMap map, String id) {
		RecordEntity record = recordService.getRecord(id);
		RecordFlagEnum[] flag = RecordFlagEnum.values();
		map.put("record", record);
		map.put("flag", flag);
		return "manage/record/record_banding";
	}

	@RequestMapping("/banding_save")
	public @ResponseBody Map<String, String> bandingSave(String contractNo,
			String refContractNo, String recordNo, String recordNo2) {
		Map<String, String> map = new HashMap<String, String>();
		recordService.bindingContract(contractNo, refContractNo, recordNo,
				recordNo2);
		return map;
	}

	/**
	 * 根据合同号查询
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("/queryRecordNo")
	public @ResponseBody Map<String, Object> queryRecordNo(RecordQuery query) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<RecordEntity> list = recordService.queryRecord(query);
		ContractModel cm = contractService.getContractByContractNo(query
				.getContractNo());
		map.put("record", list.get(0));
		map.put("refContractNo", cm.getContract().getRefContractNo());
		return map;
	}

	/**
	 * 根据ID删除备案
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delRecord")
	public @ResponseBody Map<String, String> delRecord(String id) {
		Map<String, String> map = new HashMap<String, String>();
		recordService.deleteRecord(id);
		map.put("isOk", "ok");
		return map;
	}
}
