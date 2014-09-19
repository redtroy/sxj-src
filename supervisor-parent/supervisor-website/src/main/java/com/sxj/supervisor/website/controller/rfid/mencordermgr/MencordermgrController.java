package com.sxj.supervisor.website.controller.rfid.mencordermgr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/rfid/mencordermgr")
public class MencordermgrController extends BaseController {

	@RequestMapping("mencordermgr_view")
	public String mencordermgr_view(ModelMap map) {
		return "site/rfid/mencordermgr/mencordermgr";
	}
}
