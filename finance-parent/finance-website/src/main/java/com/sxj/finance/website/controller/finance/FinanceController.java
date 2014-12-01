package com.sxj.finance.website.controller.finance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.finance.entity.FinanceEntity;
import com.sxj.finance.model.finance.FinanceModel;
import com.sxj.finance.service.finance.IFinanceService;
import com.sxj.finance.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@RequestMapping("finance")
@Controller
public class FinanceController extends BaseController {

	@Autowired
	private IFinanceService financeService;

	@RequestMapping("financeList")
	public String financeList(ModelMap map, FinanceModel query)
			throws WebException {
		try {
			if (query == null) {
				query.setPagable(true);
			}
			List<FinanceEntity> fe = financeService.query(query);
			map.put("list", fe);
			map.put("query", query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "site/finance/pay-list";
	}
}
