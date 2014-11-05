package com.sxj.supervisor.manage.controller.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.contract.ContractWindowTypeEnum;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractModifyModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.StateLogModel;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/contract")
public class ContractController extends BaseController {

	@Autowired
	private IContractService contractService;

	@Autowired
	private IRecordService recordService;

	@RequestMapping("query")
	public String queryContract(ContractQuery query, ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			List<ContractModel> list = contractService.queryContracts(query);
			for (ContractModel contractModel : list) {
				RecordQuery rq = new RecordQuery();
				rq.setContractNo(contractModel.getContract().getContractNo());
				rq.setSort("desc");
				rq.setSortColumn("APPLY_DATE");
				List<RecordEntity> recordList = recordService.queryRecord(rq);
				if (recordList.size() > 0) {
					RecordEntity recordEntity = recordList.get(0);
					if (contractModel.getContract().getType().getId() == 0) {
						contractModel.getContract().setStateLog("乙方已申请备案");
					} else {
						if (recordEntity.getType().getId() == 0) {
							if (recordEntity.getFlag().getId() == 0) {
								contractModel.getContract().setStateLog(
										"甲方已申请备案");
							} else {
								contractModel.getContract().setStateLog(
										"乙方已申请备案");
							}
						} else if (recordEntity.getType().getId() == 1) {
							contractModel.getContract()
									.setStateLog("甲方已申请变更备案");
						} else {
							contractModel.getContract()
									.setStateLog("甲方已申请补损备案");
						}
					}
				}
			}

			ContractTypeEnum[] contractType = ContractTypeEnum.values();// 合同类型
			ContractSureStateEnum[] contractSureState = ContractSureStateEnum
					.values();// 确认状态
			ContractStateEnum[] contractState = ContractStateEnum.values();// 状态
			model.put("contractType", contractType);
			model.put("contractSureState", contractSureState);
			model.put("contractState", contractState);
			model.put("query", query);
			model.put("list", list);
			return "manage/contract/contract-list";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	@RequestMapping("stateLog")
	public String getStateLog(ModelMap model, String contractNo, String id) {
		RecordQuery rq = new RecordQuery();
		rq.setContractNo(contractNo);
		rq.setSort("desc");
		rq.setSortColumn("APPLY_DATE");
		ContractModel contractModel = contractService
				.getContractModelByContractNo(contractNo);
		List<RecordEntity> recordList = recordService.queryRecord(rq);
		List<StateLogModel> slList = new ArrayList<StateLogModel>();
		if (recordList.size() > 0) {
			for (RecordEntity recordEntity : recordList) {
				StateLogModel sl = new StateLogModel();
				if (contractModel.getContract().getType().getId() == 0) {
					sl.setStateTitle("乙方已申请备案");
					sl.setModifyDate(recordEntity.getApplyDate());
				} else {
					if (recordEntity.getType().getId() == 0) {
						if (recordEntity.getFlag().getId() == 0) {
							sl.setStateTitle("甲方已申请备案");
							sl.setModifyDate(recordEntity.getApplyDate());
						} else {
							sl.setStateTitle("乙方已申请备案");
							sl.setModifyDate(recordEntity.getApplyDate());
						}
					} else if (recordEntity.getType().getId() == 1) {
						sl.setStateTitle("甲方已申请变更备案");
						sl.setModifyDate(recordEntity.getApplyDate());
					} else {
						sl.setStateTitle("甲方已申请补损备案");
						sl.setModifyDate(recordEntity.getApplyDate());
					}
				}
				slList.add(sl);
			}
		}
		model.put("stateLog", slList);
		model.put("id", id);
		model.put("contractNo", contractNo);

		return "manage/contract/contract-stateLog";
	}

	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractId)
			throws WebException {
		try {
			ContractModel contractModel = contractService
					.getContract(contractId);
			model.put("contractModel", contractModel);
			model.put("contractId", contractId);
			if (contractModel.getContract().getType().getId() == 0) {
				return "manage/contract/contract-info-zhaobiao";
			} else {
				return "manage/contract/contract-info";
			}
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	@RequestMapping("produced")
	public String producedContract(String recordId, ModelMap model) {
		RecordEntity record = recordService.getRecord(recordId);
		model.put("record", record);
		return "manage/contract/contract-add";
	}

	@RequestMapping("produced-kfs")
	public String producedKfsContract(String recordId, ModelMap model) {
		RecordEntity record = recordService.getRecord(recordId);
		ContractWindowTypeEnum[] type = ContractWindowTypeEnum.values();
		model.put("record", record);
		model.put("type", type);
		return "manage/contract/produced-kfs";
	}

	@RequestMapping("addContract")
	public @ResponseBody Map<String, String> addContract(
			ContractControllerModel contract, String recordId)
			throws WebException {
		try {
			contractService.addContract(contract.getContract(),
					contract.getItems(), contract.getRecordId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	@RequestMapping("toModify")
	public String toModifyContract(String contractId, ModelMap model) {
		ContractModel contractModel = contractService.getContract(contractId);
		model.put("contractModel", contractModel);
		model.put("contractId", contractId);
		return "manage/contract/contract-edit";
	}

	@RequestMapping("toKfsModify")
	public String toKfsModify(String contractId, ModelMap model) {
		ContractModel contractModel = contractService.getContract(contractId);
		ContractWindowTypeEnum[] type = ContractWindowTypeEnum.values();
		model.put("contractModel", contractModel);
		model.put("contractId", contractId);
		model.put("type", type);
		return "manage/contract/contract-kfs-edit";
	}

	@RequestMapping("modify")
	public @ResponseBody Map<String, Object> modifyContract(
			ContractModel contractModel, ModelMap model) throws WebException {
		try {
			contractService.modifyContract(contractModel);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}

	}

	@RequestMapping("delete")
	public @ResponseBody Map<String, Object> deleteContract(String id)
			throws WebException {
		try {
			contractService.deleteContract(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}

	}

	@RequestMapping("changes")
	public String changesContract(ModelMap model, String contractId,ModifyItemModel itemModel,
			String recordId) {
		RecordEntity record = recordService.getRecord(recordId);
		if (record != null) {
			ContractModel conEntity = contractService
					.getContractModelByContractNo(record.getContractNo());
			model.put("contractModel", conEntity);
			model.put("record", record);
			model.put("recordId", recordId);
		}

		return "manage/contract/contract-changes";
	}

	@RequestMapping("saveChanges")
	public @ResponseBody Map<String, Object> saveChanges(
			ContractModifyControllerModel contractModifyModel, String recordId)
			throws WebException {
		try {
			ContractModifyModel model = new ContractModifyModel();
			model.setModifyContract(contractModifyModel.getModifyContract());
			model.setModifyBatchList(contractModifyModel.getModifyBatchList());
			model.setModifyItemList(contractModifyModel.getModifyItemList());
			contractService.changeContract(recordId,
					contractModifyModel.getContractId(), model,
					contractModifyModel.getRecordNo(),
					contractModifyModel.getItemList());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	@RequestMapping("replenish")
	public String replenishContract(ModelMap model, String contractId,
			String recordId) {
		RecordEntity record = recordService.getRecord(recordId);
		if (record != null) {
			ContractModel conEntity = contractService
					.getContractModelByContractNo(record.getContractNo());
			model.put("contractModel", conEntity);
			model.put("record", record);
			model.put("recordId", recordId);

		}
		return "manage/contract/contract-replenish";
	}

	@RequestMapping("saveReplenish")
	public @ResponseBody Map<String, Object> saveReplenish(
			ContractReplenishControllerModel contractReplenish, String recordId)
			throws WebException {
		try {
			contractService.suppContract(recordId, contractReplenish
					.getReplenish().getContractId(), contractReplenish
					.getBatchList(), contractReplenish.getReplenish());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	@RequestMapping("check")
	public @ResponseBody Map<String, Object> check(String contractId)
			throws WebException {
		try {
			contractService.modifyCheckState(contractId,
					ContractStateEnum.noapproval);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	@RequestMapping("getRecordNo")
	public @ResponseBody Map<String, Object> getRecordNo(RecordQuery query)
			throws WebException {
		try {
			String[] recordNo = query.getRecordNo().split(",");
			int flag = 0;
			for (String str : recordNo) {
				query.setRecordNo(str);
				List<RecordEntity> list = recordService.queryRecord(query);
				if (list.size() > 0) {
					flag++;
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (recordNo.length == 1) {
				if (flag == 1) {
					map.put("flag", "true");
				} else {
					map.put("flag", "false");
				}
			} else if (recordNo.length == 2) {
				if (flag == 2 || flag == 1) {
					map.put("flag", "true");
				} else {
					map.put("flag", "false");
				}
			}

			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}
}
