package com.sxj.finance.website.controller.finance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.finance.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@RequestMapping("finance")
@Controller
public class FinanceController extends BaseController {

	@RequestMapping("financeList")
	public String financeList(ModelMap map) throws WebException {
		try {
			System.out.println("a");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "site/finance/pay-list";
	}
}
