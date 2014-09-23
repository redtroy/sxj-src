package com.sxj.supervisor.manager.controller.rfid.pricemgr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.manage.controller.BaseController;

@Controller
@RequestMapping("/rfid/pricemgr")
public class PricemgrController extends BaseController {

	/**
	 * RFID销售价格管理
	 * 
	 * @return
	 */
	@RequestMapping("pricemgr_view")
	public String pricemgr_view(ModelMap map) {
		System.out.println("ll");
		return "manage/rfid/pricemgr/pricemgr";
	}
}
