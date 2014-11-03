package com.sxj.supervisor.website.controller.rfid.window;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.logistics.LabelStateEnum;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.rfid.base.LogModel;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/window/logistics")
public class LogisticsController extends BaseController {
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
			return "site/rfid/window/logistics/logistics-list";
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
			return "site/rfid/window/logistics/stateLog";
		} catch (Exception e) {
			SxjLogger.error("查询log动态信息错误", e, this.getClass());
			throw new WebException("查询log动态信息错误");
		}
	}
}
