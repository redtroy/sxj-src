package com.sxj.supervisor.manage.controller.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.service.contract.IContractService;

@Controller
@RequestMapping("/contract")
public class ContractController extends BaseController {

	@Autowired
	private IContractService contractService;
	
	@RequestMapping("query")
	public String queryContract(ModelMap model) {
		ContractQuery contractQuery = new ContractQuery();
		contractQuery.setContractNo("1");
		List<ContractModel> list =contractService.queryContracts(contractQuery);
		model.put("list", list);
		return "manage/contract/contract-list";
	}

	@RequestMapping("info")
	public String queryContractInfo(ModelMap model,String contractId ) {
		ContractModel contractModel = contractService.getContract(contractId);
		model.put("contractModel", contractModel);
		return "manage/contract/contract-info";
	}
}
