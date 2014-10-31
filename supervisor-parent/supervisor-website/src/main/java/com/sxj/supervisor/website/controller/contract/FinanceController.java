package com.sxj.supervisor.website.controller.contract;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("contract")
public class FinanceController extends BaseController {

	@RequestMapping("finance")
	public String finance(ModelMap map) throws WebException {
		try {

		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
		return "site/contract/finance";
	}
}
