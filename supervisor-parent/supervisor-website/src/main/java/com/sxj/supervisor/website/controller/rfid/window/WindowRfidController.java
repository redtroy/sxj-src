package com.sxj.supervisor.website.controller.rfid.window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.supervisor.website.controller.BaseController;
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
	public String queryWindowRfid(WindowRfidQuery query, HttpSession session,
			ModelMap model) throws WebException {
		try {
			query.setPagable(true);
			SupervisorPrincipal loginInfo = getLoginInfo(session);
			String memberNo = loginInfo.getMember().getMemberNo();
			query.setMemberNo(memberNo);
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
			return "site/rfid/window/manage/windowRfid-list";
		} catch (Exception e) {
			SxjLogger.error("查询认证信息错误", e, this.getClass());
			throw new WebException("查询门认证信息错误");
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
			SxjLogger.error("删除认证信息错误", e, this.getClass());
			throw new WebException("删除认证信息错误");
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
			SxjLogger.error("停用认证信息错误", e, this.getClass());
			throw new WebException("停用认证信息错误");
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
	public String queryContractInfo(ModelMap model, String contractNo,
			String rfid) throws WebException {
		try {
			ContractModel cmodel = contractService
					.getContractModelByContractNo(contractNo);
			ContractModel contractModel = contractService.getContract(cmodel
					.getContract().getId());
			model.put("contractModel", contractModel);
			model.put("rfid", rfid);
			return "site/rfid/window/manage/contract-info";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	@RequestMapping("contractBatch")
	public String getContractBatch(ModelMap model, String contractNo,
			String rfidNo, String id,String type) throws WebException {
		try {
			ContractBatchModel conBatch = contractService.getBatchByRfid(rfidNo);
			model.put("conBatch", conBatch);
			model.put("id", id);
			model.put("type", type);
			model.put("contractNo", contractNo);
			return "site/rfid/window/manage/contract-batch";
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
			List<LogModel> logList = windowRfidService.getRfidStateLog(id);
			model.put("id", id);
			model.put("logList", logList);
			return "site/rfid/window/manage/stateLog";
		} catch (Exception e) {
			SxjLogger.error("查询log动态信息错误", e, this.getClass());
			throw new WebException("查询log动态信息错误");
		}
	}

	/**
	 * 跳转标签补损
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("toLableLosses")
	public String toLableLosses(ModelMap model, String id) throws WebException {
		try {
			WindowRfidEntity windowRfid = windowRfidService.getWindowRfid(id);
			model.put("id", id);
			model.put("windowRfid", windowRfid);
			return "site/rfid/window/manage/lable-losses";
		} catch (Exception e) {
			SxjLogger.error("查询标签补损错误", e, this.getClass());
			throw new WebException("查询标签补损错误");
		}
	}

	/**
	 * 标签补损
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("lableLosses")
	public @ResponseBody Map<String, String> lableLosses(String id,
			String replenishNo) throws WebException {
		try {
			WindowRfidEntity windowRfid = windowRfidService.getWindowRfid(id);
			windowRfid.setReplenishNo(replenishNo);
			windowRfidService.updateWindowRfid(windowRfid);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("标签补损错误", e, this.getClass());
			throw new WebException("标签补损错误");
		}
	}
}
