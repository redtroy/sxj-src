package com.sxj.supervisor.website.controller.rfid.window;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/rfid/window")
public class LossWinRfidController extends BaseController {

	@RequestMapping("to_loss")
	public String lossMen() {
		return "site/rfid/window/lossrfid";
	}
}
