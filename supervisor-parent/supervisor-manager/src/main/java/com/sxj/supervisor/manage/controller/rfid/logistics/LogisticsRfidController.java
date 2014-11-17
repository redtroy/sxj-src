package com.sxj.supervisor.manage.controller.rfid.logistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.rfid.RfidLog;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/logistics")
public class LogisticsRfidController extends BaseController {
	@Autowired
	private ILogisticsRfidService logisticsRfidService;

	@Autowired
	private IContractService contractService;

	/**
	 * 查询列表
	 * 
	 * @param query
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("query")
	public String queryLogistics(LogisticsRfidQuery query, ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			List<LogisticsRfidEntity> list = logisticsRfidService
					.queryLogistics(query);
			LabelStateEnum[] Label = LabelStateEnum.values();
			RfidStateEnum[] rfid = RfidStateEnum.values();
			RfidTypeEnum[] type = RfidTypeEnum.values();
			model.put("Label", Label);
			model.put("rfid", rfid);
			model.put("type", type);
			model.put("query", query);
			model.put("list", list);
			return "manage/rfid/logistics/logistics-list";
		} catch (Exception e) {
			SxjLogger.error("查询门窗RFID错误", e, this.getClass());
			throw new WebException("查询门窗RFID错误");
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("delete")
	public @ResponseBody Map<String, String> delete(String id, ModelMap model)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			LogisticsRfidEntity win = new LogisticsRfidEntity();
			win.setId(id);
			win.setRfidState(RfidStateEnum.delete);
			logisticsRfidService.updateLogistics(win);
			map.put("isOK", "ok");
		} catch (Exception e) {
			SxjLogger.error("删除物流RFID错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 停用
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("disable")
	public @ResponseBody Map<String, String> disable(String id, ModelMap model)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			LogisticsRfidEntity win = new LogisticsRfidEntity();
			win.setId(id);
			win.setRfidState(RfidStateEnum.disable);
			logisticsRfidService.updateLogistics(win);
			map.put("isOK", "ok");
		} catch (Exception e) {
			SxjLogger.error("停用物流RFID错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 获取log动态
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("stateLog")
	public String getStateLog(ModelMap model, String id) throws WebException {
		try {
			List<RfidLog> logList = logisticsRfidService.getRfidStateLog(id);
			model.put("id", id);
			model.put("logList", logList);
			return "manage/rfid/window/stateLog";
		} catch (Exception e) {
			SxjLogger.error("查询log动态信息错误", e, this.getClass());
			throw new WebException("查询log动态信息错误");
		}
	}
	/**
	 * 获取合同详细
	 * 
	 * @param model
	 * @param contractNo
	 * @param rfid
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("contractInfo")
	public String queryContractInfo(ModelMap model, String contractNo, String id)
			throws WebException {
		try {
			ContractModel cmodel = contractService
					.getContractModelByContractNo(contractNo);
			ContractModel contractModel = contractService.getContract(cmodel
					.getContract().getId());
			model.put("contractModel", contractModel);
			model.put("contractId", id);
			return "manage/contract/contract-info";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}
}
