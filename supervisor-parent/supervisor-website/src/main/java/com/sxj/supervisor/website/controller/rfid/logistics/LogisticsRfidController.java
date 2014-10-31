package com.sxj.supervisor.website.controller.rfid.logistics;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/logistics")
public class LogisticsRfidController extends BaseController {
	@Autowired
	private ILogisticsRfidService logisticsRfidService;

	@Autowired
	private IContractService contractService;

	@Autowired
	private IRecordService recordService;

	/**
	 * 查询列表
	 * 
	 * @param query
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("query")
	public String queryLogistics(LogisticsRfidQuery query, HttpSession session,
			ModelMap model) throws WebException {
		try {
			query.setPagable(true);
			SupervisorPrincipal loginInfo = getLoginInfo(session);
			String memberNo = loginInfo.getMember().getMemberNo();
			query.setMemberNo(memberNo);
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
			return "site/rfid/logistics/logistics-list";
		} catch (Exception e) {
			SxjLogger.error("查询门窗RFID错误", e, this.getClass());
			throw new WebException("查询门窗RFID错误");
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
			List<LogModel> logList = logisticsRfidService.getRfidStateLog(id);
			model.put("id", id);
			model.put("logList", logList);
			return "site/rfid/window/stateLog";
		} catch (Exception e) {
			SxjLogger.error("查询log动态信息错误", e, this.getClass());
			throw new WebException("查询log动态信息错误");
		}
	}

	/**
	 * 物流标签管理(乙方)
	 * 
	 * @param query
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("queryList")
	public String queryLogisticsB(LogisticsRfidQuery query,
			HttpSession session, ModelMap model) throws WebException {
		try {
			query.setPagable(true);
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			query.setMemberNo(userBean.getMember().getMemberNo());
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
			return "site/rfid/logisticsB/logistics-list";
		} catch (Exception e) {
			SxjLogger.error("查询物流RFID错误", e, this.getClass());
			throw new WebException("查询物流RFID错误");
		}
	}

	/**
	 * 跳转启用
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("to_start")
	public String toStart(String id, ModelMap model) throws WebException {
		try {
			LogisticsRfidEntity logistics = logisticsRfidService
					.getLogistics(id);
			model.put("logistics", logistics);
			return "site/rfid/logisticsB/start-rfid";
		} catch (Exception e) {
			SxjLogger.error("查询门窗RFID错误", e, this.getClass());
			throw new WebException("查询门窗RFID错误");
		}
	}

	/**
	 * 启用
	 * 
	 * @param batch
	 * @param logisticsId
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("saveRfid")
	public @ResponseBody Map<String, String> saveRfid(ContractBatchModel batch,
			String logisticsId, HttpSession session) throws WebException {
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			contractService.addBatch(batch, logisticsId, userBean.getMember());
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("启用物流错误", e, this.getClass());
			throw new WebException("启用物流错误");
		}
	}

	/**
	 * 跳转补损
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("to_loss")
	public String to_loss(String id, ModelMap model) throws WebException {
		try {
			LogisticsRfidEntity logistics = logisticsRfidService
					.getLogistics(id);
			List<ContractBatchModel> conBatch = contractService
					.getContractBatch(logistics.getContractNo(),
							logistics.getRfidNo());
			if (conBatch != null && conBatch.size() > 0) {
				model.put("batch", conBatch.get(0).getBatchItems());
			} else {
				model.put("batch", null);
			}
			model.put("logistics", logistics);

			return "site/rfid/logisticsB/loss-gysrfid";
		} catch (Exception e) {
			SxjLogger.error("查询物流错误", e, this.getClass());
			throw new WebException("查询物流错误");
		}
	}

	@RequestMapping("rfid_loss")
	public @ResponseBody Map<String, String> rfidLoss(String id, String rfidNo,
			String newRfid, String contractNo, HttpSession session)
			throws WebException {
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			contractService.updateRfid(id, rfidNo, contractNo,
					userBean.getMember(), newRfid);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("启用物流错误", e, this.getClass());
			throw new WebException("启用物流错误");
		}
	}

	@RequestMapping("autoContract")
	public @ResponseBody Map<String, String> autoContract(
			HttpServletRequest request, HttpServletResponse response,
			String keyword, HttpSession session) throws IOException {
		SupervisorPrincipal userBean = (SupervisorPrincipal) session
				.getAttribute("userinfo");
		ContractQuery cm = new ContractQuery();
		if (keyword != "" && keyword != null) {
			cm.setContractNo(keyword);
		}
		cm.setMemberId(userBean.getMember().getMemberNo());
		List<ContractModel> cmList = contractService.queryContracts(cm);
		List strlist = new ArrayList();
		String sb = "";
		for (ContractModel cmModel : cmList) {
			sb = "{\"title\":\"" + cmModel.getContract().getContractNo()
					+ "\",\"result\":\""
					+ cmModel.getContract().getContractNo() + "\"}";
			strlist.add(sb);
		}
		String json = "{\"data\":" + strlist.toString() + "}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		return null;
	}

	@RequestMapping("getRecord")
	public @ResponseBody Map<String, String> getRecord(String contractNo,
			HttpSession session) throws WebException {
		try {
			RecordQuery rq = new RecordQuery();
			rq.setContractNo(contractNo);
			rq.setRecordType(2);
			List<RecordEntity> reList = recordService.queryRecord(rq);
			String str = "";
			for (RecordEntity recordEntity : reList) {
				str += recordEntity.getRecordNo() + ",";
			}
			if (str != "") {
				str = str.substring(0, str.length() - 1);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			map.put("str", str);
			return map;
		} catch (Exception e) {
			SxjLogger.error("查询备案错误", e, this.getClass());
			throw new WebException("查询备案错误");
		}
	}

	@RequestMapping("getRecordInfo")
	public @ResponseBody Map<String, String> getRecordInfo(String recordNo,
			HttpSession session) throws WebException {
		try {
			RecordEntity record = recordService.getRecordByNo(recordNo);
			String str = record.getRfidNo();
			Map<String, String> map = new HashMap<String, String>();
			map.put("str", str);
			return map;
		} catch (Exception e) {
			SxjLogger.error("查询rfid错误", e, this.getClass());
			throw new WebException("查询rfid错误");
		}
	}

	/**
	 * 补损采购合同
	 * 
	 * @param recordNo
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("gysrfid_loss")
	public @ResponseBody Map<String, String> gysrfidLoss(String recordNo,
			HttpSession session) throws WebException {
		try {
			RecordEntity record = recordService.getRecordByNo(recordNo);
			String str = record.getRfidNo();
			Map<String, String> map = new HashMap<String, String>();
			map.put("str", str);
			return map;
		} catch (Exception e) {
			SxjLogger.error("采购合同补损错误", e, this.getClass());
			throw new WebException("采购合同补损错误");
		}
	}

	/**
	 * 查询是否已补损
	 * 
	 * @param recordNo
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("getLoss")
	public @ResponseBody Map<String, String> getLoss(String contractNo,
			HttpSession session) throws WebException {
		try {
			String record = contractService.getReplenish(contractNo);
			Map<String, String> map = new HashMap<String, String>();
			if (record == null) {
				map.put("isOK", "ok");
			} else {
				map.put("isOK", "no");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("采购合同补损错误", e, this.getClass());
			throw new WebException("采购合同补损错误");
		}
	}
}
