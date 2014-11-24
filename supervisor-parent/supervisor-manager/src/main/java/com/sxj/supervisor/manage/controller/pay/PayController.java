package com.sxj.supervisor.manage.controller.pay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.enu.contract.PayContentStateEnum;
import com.sxj.supervisor.enu.contract.PayModeEnum;
import com.sxj.supervisor.enu.contract.PayStageEnum;
import com.sxj.supervisor.enu.contract.PayTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractPayModel;
import com.sxj.supervisor.service.contract.IContractPayService;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("pay")
public class PayController extends BaseController {

	@Autowired
	private IContractPayService payService;

	@Autowired
	private IContractService contractService;

	@RequestMapping("payList")
	public String payList(ModelMap map, ContractPayModel query)
			throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			List<PayRecordEntity> list = payService.queryPayList(query);
			PayContentStateEnum[] payStates = PayContentStateEnum.values();// 支付内容
			PayModeEnum[] payModes = PayModeEnum.values();// 支付方式
			PayTypeEnum[] payTypes = PayTypeEnum.values();// 类型
			PayStageEnum[] payStages = PayStageEnum.values();// 付款状态
			map.put("list", list);
			map.put("payStates", payStates);
			map.put("payModes", payModes);
			map.put("payTypes", payTypes);
			map.put("payStages", payStages);
			map.put("query", query);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException(e.getMessage());
		}
		return "manage/pay/pay-list";
	}

	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractNo,
			String recordNo) throws WebException {
		try {
			ContractModel contract = contractService
					.getContractModelByContractNo(contractNo);
			ContractModel contractModel = new ContractModel();
			if (contract.getContract() != null) {
				contractModel = contractService.getContract(contract
						.getContract().getId());
			}
			model.put("contractModel", contractModel);
			model.put("recordNo", recordNo);
			if (contractModel.getContract().getType().getId() == 0) {
				return "manage/pay/contract-info-zhaobiao";
			} else {
				return "manage/pay/contract-info";
			}
			// contractModel.getContract().getImgPath().split(",");
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}
}
