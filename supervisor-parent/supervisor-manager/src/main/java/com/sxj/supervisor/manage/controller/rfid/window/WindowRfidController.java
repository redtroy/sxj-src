package com.sxj.supervisor.manage.controller.rfid.window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.rfid.RfidLog;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/window")
public class WindowRfidController extends BaseController {
	@Autowired
	private IWindowRfidService windowRfidService;

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
	public String queryWindowRfid(WindowRfidQuery query, ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			List<WindowRfidEntity> winList = windowRfidService
					.queryWindowRfid(query);
			LabelProgressEnum[] Label = LabelProgressEnum.values();
			RfidStateEnum[] rfid = RfidStateEnum.values();
			WindowTypeEnum[] winType = WindowTypeEnum.values();
			model.put("Label", Label);
			model.put("rfid", rfid);
			model.put("winType", winType);
			model.put("query", query);
			model.put("winList", winList);
			return "manage/rfid/window/windowRfid-list";
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
		try {
			WindowRfidEntity win = new WindowRfidEntity();
			win.setId(id);
			win.setRfidState(RfidStateEnum.delete);
			windowRfidService.updateWindowRfid(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("删除门窗RFID错误", e, this.getClass());
			throw new WebException("删除门窗RFID错误");
		}
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
		try {
			WindowRfidEntity win = new WindowRfidEntity();
			win.setId(id);
			win.setRfidState(RfidStateEnum.disable);
			windowRfidService.updateWindowRfid(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("停用门窗RFID错误", e, this.getClass());
			throw new WebException("停用门窗RFID错误");
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
			model.put("id", id);
			return "manage/rfid/window/contract-info";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	@RequestMapping("contractBatch")
	public String getContractBatch(ModelMap model, String contractNo,
			String rfidNo, String id) throws WebException {
		try {
			List<ContractBatchModel> conBatch = contractService
					.getContractBatch(contractNo, rfidNo);
			model.put("conBatch", conBatch);
			model.put("id", id);
			model.put("contractNo", contractNo);
			return "manage/rfid/window/contract-batch";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
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
			List<RfidLog> logList = windowRfidService.getRfidStateLog(id);
			model.put("id", id);
			model.put("logList", logList);
			return "manage/rfid/window/stateLog";
		} catch (Exception e) {
			SxjLogger.error("查询log动态信息错误", e, this.getClass());
			throw new WebException("查询log动态信息错误");
		}
	}
}
