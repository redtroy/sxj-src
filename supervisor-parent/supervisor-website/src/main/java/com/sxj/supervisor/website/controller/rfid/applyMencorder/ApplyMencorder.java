package com.sxj.supervisor.website.controller.rfid.applyMencorder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/rfid/applyMencorder")
public class ApplyMencorder extends BaseController {

	@RequestMapping("applyMencorder_view")
	public String applyMencorder_view(ModelMap map) {
		return "site/rfid/new-mencorder/new-mencorder";
	}

}
