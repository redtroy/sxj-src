package com.sxj.supervisor.manage.controller.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.contract.ContractTypeEnum;
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
		contractService.addContract(contract.getContract(), contract.getItems(), contract.getRecordId());
		
		return "manage/contract/contract-list";
	}
	@RequestMapping("toModify")
	public String toModifyContract(String  contractId,ModelMap model) {
		ContractModel contractModel = contractService.getContract("1");
		model.put("contractModel", contractModel);
		model.put("contractId", contractId);
		return "manage/contract/contract-edit";
	}
	@RequestMapping("modify")
	public String modifyContract(ContractModel  contractModel,ModelMap model) {
		contractService.modifyContract(contractModel);
		
		return "manage/contract/contract-edit";
	}
	
	@RequestMapping("changes")
	public String changesContract(ModelMap model, String contractId) {
		ContractModel contractModel = contractService.getContract("1");
		model.put("contractModel", contractModel);
		model.put("contractId", contractId);
		return "manage/contract/contract-changes";
	}
}
