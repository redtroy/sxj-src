package com.sxj.supervisor.website.controller.rfid.startmrfid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/rfid")
public class StartmrfidController extends BaseController {

	@RequestMapping("startmrfid_list")
	public String startmrfid_list() {
		return "site/rfid/startmrfid/startmrfid";
	}
}
