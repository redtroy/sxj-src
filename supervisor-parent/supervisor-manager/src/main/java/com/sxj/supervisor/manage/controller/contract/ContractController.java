package com.sxj.supervisor.manage.controller.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.util.persistent.ResultList;

@Controller
@RequestMapping("/contract")
public class ContractController extends BaseController {

	@Autowired
	private IContractService contractService;
	
	@RequestMapping("query")
	public String queryContract(ModelMap model) {
		ContractQuery contractQuery = new ContractQuery();
		contractQuery.setContractNo("1");
		ResultList<ContractModel> rList =contractService.queryContracts(contractQuery);
		model.put("list", rList.getResults());
		return "manage/contract/contract-list";
	}

}
