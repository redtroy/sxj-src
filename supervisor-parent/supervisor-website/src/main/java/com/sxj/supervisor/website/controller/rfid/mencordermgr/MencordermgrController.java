package com.sxj.supervisor.website.controller.rfid.mencordermgr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/rfid/mencordermgr")
public class MencordermgrController extends BaseController {

	/**
	 * 认证标签申请管理
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("mencordermgr_list")
	public String mencordermgr_list(ModelMap map) {
		return "site/rfid/mencordermgr/mencordermgr";
	}
}
