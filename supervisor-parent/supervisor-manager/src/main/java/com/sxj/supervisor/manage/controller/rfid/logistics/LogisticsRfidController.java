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
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.window.RfidTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
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
			LabelProgressEnum[] Label = LabelProgressEnum.values();
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
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("delete")
	public @ResponseBody Map<String, String> delete(String id, ModelMap model)
			throws WebException {
		try {
			LogisticsRfidEntity win = new LogisticsRfidEntity();
			win.setId(id);
			win.setRfidState(RfidStateEnum.delete);
			logisticsRfidService.updateLogistics(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("删除物流RFID错误", e, this.getClass());
			throw new WebException("删除物流RFID错误");
		}
	}
	/**
	 * 停用
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("disable")
	public @ResponseBody Map<String, String> disable(String id, ModelMap model)
			throws WebException {
		try {
			LogisticsRfidEntity win = new LogisticsRfidEntity();
			win.setId(id);
			win.setRfidState(RfidStateEnum.disable);
			logisticsRfidService.updateLogistics(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("停用物流RFID错误", e, this.getClass());
			throw new WebException("停用物流RFID错误");
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
}
