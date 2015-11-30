package com.sxj.supervisor.manage.controller.purchase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.purchase.ApplyEntity;
import com.sxj.supervisor.service.purchase.IPurchaseService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@RequestMapping("purchase")
@Controller
public class PurchaseController {

	@Autowired
	private IPurchaseService purchaseService;

	@RequestMapping("queryApply")
	public String woodList(ModelMap map, ApplyEntity query) throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			List<ApplyEntity> list = purchaseService.queryApply(query);
			map.put("list", list);
			map.put("query", query);
		} catch (Exception e) {
			SxjLogger.error("查询信息失败", e.getClass());
			e.printStackTrace();
		}
		return "manage/purchase/applyList";
	}
}
