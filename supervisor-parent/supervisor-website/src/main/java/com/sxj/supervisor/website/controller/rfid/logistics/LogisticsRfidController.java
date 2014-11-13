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
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;
import com.sxj.supervisor.model.contract.BatchItemModel;
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
import com.sxj.util.common.StringUtils;
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
					.queryLogistics_B(query);
			LabelStateEnum[] Label = LabelStateEnum.values();
			RfidStateEnum[] rfid = RfidStateEnum.values();
			RfidTypeEnum[] type = RfidTypeEnum.values();
			model.put("Label", Label);
			model.put("rfid", rfid);
			model.put("type", type);
			model.put("query", query);
			model.put("list", list);
			return "site/rfid/logistics/manage/logistics-list";
		} catch (Exception e) {
			SxjLogger.error("查询物流RFID错误", e, this.getClass());
			throw new WebException("查询物流RFID错误");
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
			return "site/rfid/logistics/manage/stateLog";
		} catch (Exception e) {
			SxjLogger.error("查询log动态信息错误", e, this.getClass());
			throw new WebException("查询log动态信息错误");
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
			return "site/rfid/logistics/manage/start-rfid";
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
		Map<String, String> map = new HashMap<String, String>();
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			contractService.addBatch(batch, logisticsId, userBean.getMember());
			map.put("isOK", "ok");
		} catch (Exception e) {
			SxjLogger.error("启用物流错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 通过批次号联想查询批次详情
	 */
	@RequestMapping("batchInfo")
	public @ResponseBody Map<String, Object> queryBatchInfo(String rfidNo)
			throws WebException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(rfidNo)) {
				throw new WebException("RFID编号不能为空！");
			}
			ContractBatchModel batchModel = contractService
					.getBatchByRfid(rfidNo);
			if (batchModel == null) {
				throw new WebException("该RFID没有对应的批次！");
			}
			List<BatchItemModel> batchList = batchModel.getBatchItems();
			if (batchList == null || batchList.size() == 0) {
				throw new WebException("该RFID没有对应的批次信息！");
			}
			map.put("batch", batchModel.getBatch());
			map.put("batchItem", batchList);
		} catch (Exception e) {
			SxjLogger.error("联想查询RFID对应批次错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 通过合同号联想查询
	 */
	@RequestMapping("lx_query")
	public @ResponseBody Map<Object, Object> lx_query(String keyword)
			throws WebException {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			ContractQuery query = new ContractQuery();
			query.setKeyword(keyword);
			List<ContractModel> list = contractService.queryContracts(query);
			if (list == null) {
				return map;
			}
			List<Map<String, Object>> addlist = new ArrayList<Map<String, Object>>();
			for (ContractModel contract : list) {
				Map<String, Object> cmap = new HashMap<String, Object>();
				cmap.put("title", contract.getContract().getContractNo());
				if (contract.getContract().getBatchCount() == null) {
					cmap.put("batchCount", 0);
				} else {
					cmap.put("batchCount", contract.getContract()
							.getBatchCount());
				}
				addlist.add(cmap);
			}
			map.put("data", addlist);
			return map;
		} catch (Exception e) {
			SxjLogger.error("联想查询错误", e, this.getClass());
			throw new WebException("联想查询错误");
		}
	}

	/**
	 * 通过合同号查询批次号
	 */
	@RequestMapping("getBatchNo")
	public @ResponseBody Map<Object, Object> getBatch(String contractNo,
			HttpSession session) throws WebException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			SupervisorPrincipal userInfo = getLoginInfo(session);
			if (userInfo == null) {
				throw new WebException("登陆超时，请重新登陆！");
			}
			map.put("batchNo", 0);
			if (StringUtils.isEmpty(contractNo)) {
				throw new WebException("请输入合同号！");
			}
			ContractModel contract = contractService
					.getContractModelByContractNo(contractNo.trim());
			if (contract == null || contract.getContract() == null) {
				throw new WebException("合同不存在！");
			}
			if (contract.getContract().getType()
					.equals(ContractTypeEnum.bidding)) {
				throw new WebException("该合同不是采购合同！");
			}
			if (!contract.getContract().getMemberIdB()
					.equals(userInfo.getMember().getMemberNo())) {
				throw new WebException("该合同不属于当前会员！");
			}
			if (contract.getContract().getBatchCount() == null
					|| contract.getContract().getBatchCount() == 0) {
				throw new WebException("该合同没有可启用的批次！");
			}
			map.put("sumBatch", contract.getContract().getBatchCount());
			map.put("type", contract.getContract().getType().getId());

			List<ContractBatchModel> list = contract.getBatchList();
			if (list == null || list.size() == 0) {
				map.put("batchNo", 1);
			} else {
				int oldBatchCount = list.size();
				if (contract.getContract().getBatchCount() == oldBatchCount) {
					throw new WebException("该合同所有的批次已经启用完毕！");
				} else {
					map.put("batchNo", oldBatchCount + 1);
				}
			}
		} catch (Exception e) {
			SxjLogger.error("查询启用批次号错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
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
	public String to_loss(String id, String rfidNo, ModelMap model)
			throws WebException {
		try {
			model.put("id", id);
			model.put("newRfidNo", rfidNo);
			return "site/rfid/logistics/manage/loss-gysrfid";
		} catch (Exception e) {
			SxjLogger.error("查询物流错误", e, this.getClass());
			throw new WebException("查询物流错误");
		}
	}

	@RequestMapping("rfid_loss")
	public @ResponseBody Map<String, String> rfidLoss(String contractNo,
			String newRfidNo, String rfidNo, HttpSession session)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			contractService.updateRfidLoss(rfidNo, contractNo,
					userBean.getMember(), newRfidNo);
			map.put("isOK", "ok");
		} catch (Exception e) {
			SxjLogger.error("启用物流错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	@RequestMapping("contratc_loss")
	public @ResponseBody Map<String, String> contractLoss(String contractNo,
			String newRfidNo, String rfidNos, HttpSession session)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			contractService.updateContractLoss(rfidNos, contractNo,
					userBean.getMember(), newRfidNo);
			map.put("isOK", "ok");
		} catch (Exception e) {
			SxjLogger.error("启用物流错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
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
	public @ResponseBody Map<String, Object> getRecord(String contractNo,
			HttpSession session) throws WebException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(contractNo)) {
				throw new WebException("采购合同号不能为空！");
			}
			RecordQuery rq = new RecordQuery();
			rq.setContractNo(contractNo);
			rq.setRecordType(RecordTypeEnum.supplement.getId());
			List<RecordEntity> reList = recordService.queryRecord(rq);
			if (reList == null || reList.size() == 0) {
				throw new WebException("该合同没有补损备案！");
			}
			map.put("record", reList);
		} catch (Exception e) {
			SxjLogger.error("查询备案错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
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
