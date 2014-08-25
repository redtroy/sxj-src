package com.sxj.supervisor.manage.controller.record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.contract.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.record.IRecordService;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

	@Autowired
	IRecordService recordService;

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
		map.put("record", record);
		return "manage/record/record_banding";
	}
}
