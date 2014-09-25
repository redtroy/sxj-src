package com.sxj.supervisor.manage.controller.rfid.windowRef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.windowRef.WindowRefEntity;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.windowRef.WindowRefQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.windowRef.IWindowRfidRefService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/window-link")
public class WindowRefController extends BaseController {
	@Autowired
	private IWindowRfidRefService windowRefService;
	
	@Autowired
	private IContractService contractService;
	/**
	 * 查询列表
	 * @param query
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("query")
	public String queryWindowRfid(WindowRefQuery query, ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			List<WindowRefEntity> winList = windowRefService.queryWindowRfidRef(query);
//			LabelProgressEnum[] Label = LabelProgressEnum.values();
//			RfidStateEnum[] rfid = RfidStateEnum.values();
//			WindowTypeEnum[] winType = WindowTypeEnum.values();
//			model.put("Label", Label);
//			model.put("rfid", rfid);
//			model.put("winType", winType);
			model.put("query", query);
			model.put("winList", winList);
			return "manage/rfid/windowRef/window-link";
		} catch (Exception e) {
			SxjLogger.error("查询门窗RFID关联错误", e, this.getClass());
			throw new WebException("查询门窗RFID关联错误");
		}
	}
	/**
	 * 删除
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("delete")
	public @ResponseBody Map<String, String> delete(String id, ModelMap model)
			throws WebException {
		try {
			WindowRefEntity win = new WindowRefEntity();
			win.setId(id);
			win.setDelState(true);
			windowRefService.updateWindowRfidRef(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("删除门窗RFID关联错误", e, this.getClass());
			throw new WebException("删除门窗RFID关联错误");
		}
	}
	/**
	 * 审核
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("audit")
	public @ResponseBody Map<String, String> audit(String id, ModelMap model)
			throws WebException {
		try {
			WindowRefEntity win = new WindowRefEntity();
			win.setId(id);
			win.setState(AuditStateEnum.noapproval);
			windowRefService.updateWindowRfidRef(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("停用门窗RFID错误", e, this.getClass());
			throw new WebException("停用门窗RFID错误");
		}
	}
	/**
	 * 详情
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("info")
	public String getInfo(String id, ModelMap model)
			throws WebException {
		try {
			WindowRefEntity win = windowRefService.getWindowRfidRef(id);
			WindowTypeEnum[] type = WindowTypeEnum.values();
			model.put("type", type);
			model.put("win", win);
			return "manage/rfid/windowRef/window-link-info";
		} catch (Exception e) {
			SxjLogger.error("获取门窗RFID错误", e, this.getClass());
			throw new WebException("获取门窗RFID错误");
		}
	}
	
	/**
	 * 修改
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("saveModify")
	public @ResponseBody Map<String, String> saveModify(WindowRefEntity win, ModelMap model)
			throws WebException {
		try {
			windowRefService.updateWindowRfidRef(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("修改门窗RFID错误", e, this.getClass());
			throw new WebException("修改门窗RFID错误");
		}
	}
//	/**
//	 * 获取合同详细
//	 * @param model
//	 * @param contractNo
//	 * @param rfid
//	 * @return
//	 * @throws WebException
//	 */
//	@RequestMapping("contractInfo")
//	public String queryContractInfo(ModelMap model, String contractNo,String rfid)
//			throws WebException {
//		try {
//			ContractModel cmodel=contractService.getContractByContractNo(contractNo);
//			ContractModel contractModel = contractService
//					.getContract(cmodel.getContract().getId());
//			model.put("contractModel", contractModel);
//			model.put("rfid", rfid);
//			return "manage/rfid/window/contract-info";
//		} catch (Exception e) {
//			SxjLogger.error("查询合同信息错误", e, this.getClass());
//			throw new WebException("查询合同信息错误");
//		}
//	}
//	
//	@RequestMapping("contractBatch")
//	public String getContractBatch(ModelMap model, String contractNo,String rfidNo,String id)
//			throws WebException {
//		try {
//			List<ContractBatchModel> conBatch=contractService.getContractBatch(contractNo, rfidNo);
//			model.put("conBatch", conBatch);
//			model.put("id", id);
//			model.put("contractNo", contractNo);
//			return "manage/rfid/window/contract-batch";
//		} catch (Exception e) {
//			SxjLogger.error("查询合同信息错误", e, this.getClass());
//			throw new WebException("查询合同信息错误");
//		}
//	}
//	/**
//	 * 获取log动态
//	 * @param model
//	 * @param id
//	 * @return
//	 * @throws WebException
//	 */
//	@RequestMapping("stateLog")
//	public String getStateLog(ModelMap model,String id)
//			throws WebException {
//		try {
//			List<LogModel> logList=windowRfidService.getRfidStateLog(id);
//			model.put("id", id);
//			model.put("logList", logList);
//			return "manage/rfid/window/stateLog";
//		} catch (Exception e) {
//			SxjLogger.error("查询log动态信息错误", e, this.getClass());
//			throw new WebException("查询log动态信息错误");
//		}
//	}
}
