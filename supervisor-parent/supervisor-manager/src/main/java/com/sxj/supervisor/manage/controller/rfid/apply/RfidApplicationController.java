package com.sxj.supervisor.manage.controller.rfid.apply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;

@Controller
@RequestMapping("/rfid/apply")
public class RfidApplicationController extends BaseController {

	@RequestMapping("appList")
	public String appList(ModelMap map, RfidApplicationQuery query) {
		return "manage/rfid/order/order";
	}
}
