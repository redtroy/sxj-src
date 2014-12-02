package com.sxj.finance.manage.controller.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.finance.entity.FinanceEntity;
import com.sxj.finance.enu.finance.PayStageEnum;
import com.sxj.finance.manage.controller.BaseController;
import com.sxj.finance.model.finance.FinanceModel;
import com.sxj.finance.service.finance.IFinanceService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

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
			PayStageEnum[] states = PayStageEnum.values();
			List<FinanceEntity> fe = financeService.queryManage(query);
			map.put("list", fe);
			map.put("query", query);
			map.put("states", states);
		} catch (Exception e) {
			e.printStackTrace();
			SxjLogger.error("查询融资列表错误", e, this.getClass());
		}
		return "manage/finance/pay-list";
	}

	@RequestMapping("pay")
	public @ResponseBody Map<String, String> pay(FinanceEntity fe)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (financeService.pay(fe)) {
				map.put("isOk", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SxjLogger.error("融资支付错误", e, this.getClass());
		}
		return map;
	}

	@RequestMapping("shelve")
	public @ResponseBody Map<String, String> shelve(FinanceEntity fe)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (financeService.shelve(fe)) {
				map.put("isOk", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SxjLogger.error("融资搁置错误", e, this.getClass());
		}
		return map;
	}

	@RequestMapping("accept")
	public @ResponseBody Map<String, String> accept(FinanceEntity fe)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (financeService.accept(fe)) {
				map.put("isOk", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SxjLogger.error("融资受理错误", e, this.getClass());
		}
		return map;
	}
}
