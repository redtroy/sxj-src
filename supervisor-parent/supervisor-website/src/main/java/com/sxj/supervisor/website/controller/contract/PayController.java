package com.sxj.supervisor.website.controller.contract;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.enu.contract.PayStageEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractPayModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.service.contract.IContractPayService;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/contract")
public class PayController extends BaseController {
	@Autowired
	private IContractPayService payService;

	/**
	 * 合同service
	 */
	@Autowired
	private IContractService contractService;

	/**
	 * 获取付款信息列表
	 * 
	 * @param map
	 * @param query
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("paylist")
	public String paylist(ModelMap map, ContractPayModel query,
			HttpSession session) throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			SupervisorPrincipal info = getLoginInfo(session);
			String memberNo = info.getMember().getMemberNo();
			query.setMemberNo(memberNo);
			List<PayRecordEntity> list = payService.queryPayList(query);
			PayStageEnum[] payState = PayStageEnum.values();
			map.put("list", list);
			if (info.getMember().getType().getId() == 0) {
				map.put("state", "a");
			} else {
				map.put("state", "b");
			}
			map.put("payState", payState);
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
		return "site/contract/pay";
	}

	/**
	 * 根据合同号获取详细信息
	 * 
	 * @param model
	 * @param contractNo
	 * @param recordNo
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractNo)
			throws WebException {
		try {
			ContractModel contract = contractService
					.getContractByContractNo(contractNo);
			ContractModel contractModel = new ContractModel();
			if (contract.getContract() != null) {
				contractModel = contractService.getContract(contract
						.getContract().getId());
			}
			model.put("contractModel", contractModel);
			if (contractModel.getContract().getType().getId() == 0) {
				return "site/record/contract-info-zhaobiao";
			} else {
				return "site/record/contract-info";
			}
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}
}
