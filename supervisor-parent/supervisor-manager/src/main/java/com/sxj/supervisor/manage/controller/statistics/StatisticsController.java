package com.sxj.supervisor.manage.controller.statistics;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.statistics.StatisticsModel;
import com.sxj.supervisor.service.rfid.statistics.IStatisticsService;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/rfid/statistics")
public class StatisticsController extends BaseController {

	@Autowired
	private IStatisticsService service;

	private StatisticsModel st;

	@RequestMapping("query")
	public String queryStatistics(String startDate, String endDate,
			ModelMap model) throws WebException {
		st = service.statistics(startDate, endDate);
		model.put("st", st);
		return "manage/rfid/statistics/count";

	}

	@RequestMapping("apply")
	public @ResponseBody Map<String, Object> queryApply(String type,
			String startDate, String endDate) throws WebException {
		if (st == null) {
			st = service.statistics(startDate, endDate);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if ("1".equals(type)) {
			map.put("item", st.getApplyList());
		} else if ("2".equals(type)) {
			map.put("item", st.getPurchaseList());
		} else if ("3".equals(type)) {
			map.put("item", st.getWindowSaleList());

		} else if ("4".equals(type)) {
			map.put("item", st.getBatchSaleList());

		} else if ("5".equals(type)) {
			map.put("item", st.getWindowSaleAmountList());

		} else if ("6".equals(type)) {
			map.put("item", st.getBatchSaleAmountList());

		} else if ("7".equals(type)) {
			map.put("item", st.getWindowPurchaseAmountList());

		} else if ("8".equals(type)) {
			map.put("item", st.getBatchPurchaseAmountList());

		} else if ("9".equals(type)) {
			map.put("item", st.getExpectProfitList());

		} else if ("10".equals(type)) {
			map.put("item", st.getRealityProfitList());

		}

		return map;

	}

}
