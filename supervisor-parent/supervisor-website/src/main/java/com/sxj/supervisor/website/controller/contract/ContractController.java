package com.sxj.supervisor.website.controller.contract;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.contract.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/contract")
public class ContractController extends BaseController {
	/**
	 * 合同service
	 */
	@Autowired
	IContractService contractService;
	
	/**
	 * 备案service
	 */
	@Autowired
	IRecordService recordService;
	
	@RequestMapping("/query")
	public String to_apply(ModelMap map) {
		RecordQuery query = new RecordQuery();
		RecordTypeEnum[] rte = RecordTypeEnum.values();// 备案类型
		ContractTypeEnum[] cte = ContractTypeEnum.values(); // 合同类型
		RecordStateEnum[] rse = RecordStateEnum.values();// 备案状态
		query.setMemberId("1");
		List<RecordEntity> list = recordService.queryRecord(query);
		map.put("recordlist", list);
		map.put("recordType", rte);
		map.put("contractType", cte);
		return "site/contract/contract-list";
	}
	
	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractNo) {
		ContractModel  contract= contractService.getContractByContractNo(contractNo);
		ContractModel contractModel = new ContractModel();
		if(contract.getContract()!=null){
			 contractModel = contractService.getContract(contract.getContract().getId());
		}
		model.put("contractModel", contractModel);
		return "site/contract/contract-info";
	}

}
