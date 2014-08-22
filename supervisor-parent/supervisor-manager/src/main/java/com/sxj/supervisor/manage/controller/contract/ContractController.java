package com.sxj.supervisor.manage.controller.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ContractStateEnum;
import com.sxj.supervisor.entity.contract.ContractSureStateEnum;
import com.sxj.supervisor.entity.contract.ContractTypeEnum;
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
	public String queryContract(ContractQuery query, ModelMap model) {
		List<ContractModel> list = contractService.queryContracts(query);
		ContractTypeEnum[] contractType = ContractTypeEnum.values();// 合同类型
		ContractSureStateEnum[] contractSureState = ContractSureStateEnum.values();// 确认状态
		ContractStateEnum[] contractState = ContractStateEnum.values();// 状态
		model.put("contractType", contractType);
		model.put("contractSureState", contractSureState);
		model.put("contractState", contractState);
		model.put("query", query);
		model.put("list", list);
		return "manage/contract/contract-list";
	}

	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractId) {
		ContractModel contractModel = contractService.getContract(contractId);
		model.put("contractModel", contractModel);
		model.put("contractId", contractId);
		return "manage/contract/contract-info";
	}

	@RequestMapping("produced")
	public String producedContract(String recordId) {
		ModelMap model = new ModelMap();
		model.put("recordId", recordId);
		return "manage/contract/contract-add";
	}

	@RequestMapping("addContract")
	public String addContract(ContractControllerModel contract) {
		ModelMap model = new ModelMap();
		//contractService.addContract(contract, itemList, null);
		model.put("SUCCESS", "成功");
		return "manage/contract/contract-info";
	}
}
