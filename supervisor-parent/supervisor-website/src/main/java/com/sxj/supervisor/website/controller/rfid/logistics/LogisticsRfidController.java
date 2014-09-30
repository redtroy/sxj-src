package com.sxj.supervisor.website.controller.rfid.logistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;
import com.sxj.supervisor.enu.rfid.window.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.window.RfidTypeEnum;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.supervisor.website.login.SupervisorPrincipal;
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
			List<LogisticsRfidEntity> list = logisticsRfidService.queryLogistics(query);
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
	 * @param model
	 * @param id
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("stateLog")
	public String getStateLog(ModelMap model,String id)
			throws WebException {
		try {
			List<LogModel> logList=logisticsRfidService.getRfidStateLog(id);
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
	 * @param query
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("queryList")
	public String queryLogisticsB(LogisticsRfidQuery query, HttpSession session,ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
			query.setMemberNo(userBean.getMember().getMemberNo());
			List<LogisticsRfidEntity> list = logisticsRfidService.queryLogistics(query);
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
			SxjLogger.error("查询门窗RFID错误", e, this.getClass());
			throw new WebException("查询门窗RFID错误");
		}
	}
	/**
	 * 跳转启用
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("to_start")
	public String toStart(String id ,ModelMap model)
			throws WebException {
		try {
			LogisticsRfidEntity logistics =  logisticsRfidService.getLogistics(id);
			model.put("logistics", logistics);
			return "site/rfid/logisticsB/start-rfid";
		} catch (Exception e) {
			SxjLogger.error("查询门窗RFID错误", e, this.getClass());
			throw new WebException("查询门窗RFID错误");
		}
	}
	/**
	 * 启用
	 * @param batch
	 * @param logisticsId
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("saveRfid")
	public @ResponseBody Map<String, String> saveRfid(ContractBatchModel batch,String logisticsId,HttpSession session)
			throws WebException {
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
			contractService.addBatch(batch,logisticsId,userBean.getMember());
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
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("to_loss")
	public String to_loss(String id ,ModelMap model)
			throws WebException {
		try {
			LogisticsRfidEntity logistics =  logisticsRfidService.getLogistics(id);
			List<ContractBatchModel> conBatch=contractService.getContractBatch(logistics.getContractNo(), logistics.getRfidNo());
			model.put("logistics", logistics);
			model.put("batch",conBatch.get(0).getBatchItems());
			return "site/rfid/logisticsB/loss-gysrfid";
		} catch (Exception e) {
			SxjLogger.error("查询物流错误", e, this.getClass());
			throw new WebException("查询物流错误");
		}
	}
}
