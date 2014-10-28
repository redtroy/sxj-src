package com.sxj.supervisor.website.controller.rfid.startmrfid;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/rfid")
public class StartmrfidController extends BaseController {

	@RequestMapping("startmrfid_list")
	public String startmrfid_list() {
		return "site/rfid/startmrfid/startmrfid";
	}

	@RequestMapping("query")
	public @ResponseBody Map<String, String> query(String refContractNo)
			throws WebException {

		return null;
	}
}
