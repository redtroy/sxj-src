package com.sxj.supervisor.website.controller.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.model.statistics.AccountingModel;
import com.sxj.supervisor.service.contract.IContractPayService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("contract")
public class FinanceController extends BaseController {
	@Autowired
	private IContractPayService payService;

	@RequestMapping("finance")
	public String finance(ModelMap map, AccountingModel query,
			String startDate, String endDate) throws WebException {
		try {
			if (query == null) {
				query.setPagable(true);
			}
			ContractTypeEnum[] types = ContractTypeEnum.values();
			List<AccountingModel> list = payService.query_finance(query,
					startDate, endDate);
			map.put("list", list);
			map.put("types", types);
			map.put("query", query);
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
		return "site/contract/finance";
	}
}
