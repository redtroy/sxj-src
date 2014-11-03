package com.sxj.supervisor.website.controller.rfid.lossMenc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/rfid")
public class LossMencController extends BaseController {

	@RequestMapping("lossMen")
	public String lossMen() {
		return "site/rfid/loss-menc/loss-menc";
	}
}
